package top.lothar.juc.lock.lock;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：执行流程：
 *
 * 未中断：
 *
 * Thread-1尝试获取锁
 * Thread-0尝试获取锁
 *
 * Thread-1获取到了锁
 * Thread-1释放了锁
 *
 * Thread-0获取到了锁 （一直等待1去释放锁 然后自己拿到）
 * Thread-0释放了锁
 *
 * 中断：
 * Thread-0尝试获取锁
 * Thread-1尝试获取锁
 * Thread-0获取到了锁
 *
 * Thread-1获得锁期间被中断了 （线程sleep 3s 没有等到锁 手动 thread1.interrupt(); 中断处理，不再争抢）
 * Thread-0释放了锁
 *
 */
public class LockInterruptibly implements Runnable {

    private Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread thread0 = new Thread(lockInterruptibly);
        Thread thread1 = new Thread(lockInterruptibly);
        thread0.start();
        thread1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断获取
        thread1.interrupt();
}
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            //等锁期间会打断它用interrupt方法
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠期间被中断了");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "获得锁期间被中断了");
        }
    }
}
