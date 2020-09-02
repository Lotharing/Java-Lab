package top.lothar.juc.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 描述：实现简单自旋锁  自旋状态下等待拿锁的线程释放锁，避免线程之间的切换，不放弃cpu的执行时间 减少cpu对线程的调度 ， 为了就是在场景中进一步提高效率
 * 应用场景： 1.多核 并发度不是很高  2.临界区小（线程释放锁比较快）
 * [为什么要自己旋： 1.线程频繁的阻塞唤醒切换需要切换cpu的状态耗费处理器时间 2.如果同步代码块执行时间很短就可以不阻塞唤醒线程 让其一直自旋 提高效率]
 * 
 * 执行过程：
 * 
 * Thread-0开始尝试获取自旋锁
 * Thread-1开始尝试获取自旋锁
 * Thread-0获取到了自旋锁
 * Thread-1自旋获取失败，再次尝试
 * .
 * .自旋的时间跟拿锁的线程释放时间有关 （缺点就是如果A一直不释放 , B一直自旋拿不到浪费cpu资源）
 * .
 * Thread-1自旋获取失败，再次尝试
 * Thread-1自旋获取失败，再次尝试
 * Thread-0释放了自旋锁
 * Thread-1获取到了自旋锁
 * Thread-1释放了自旋锁
 *  
 *
 */
public class SpinLock {
    /**
     * 原子引用
     */
    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        // null 希望没有人使用锁  current 原子让当前线程使用
        while (!sign.compareAndSet(null, current)) {
            //打印出自旋的过程  这里就会暴露缺点如果0锁不释放 1就会一直自旋
            System.out.println(Thread.currentThread().getName()+"自旋获取失败，再次尝试");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        //当前线程本身  null 没有任何人持有锁
        sign.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
                //多个线程开始尝试获取锁 没有获取到的会开始while中自旋
                spinLock.lock();
                System.out.println(Thread.currentThread().getName() + "获取到了自旋锁");
                try {
                    //休眠短一点让 thread 0 早点释放锁 让1自旋结束得到锁
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    spinLock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放了自旋锁");
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
