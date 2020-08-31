package top.lothar.juc.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述：演示非公平和公平的ReentrantReadWriteLock的策略
 *
 * 　   1.在公平锁的条件下，所有的锁都不允许插队
 *
 * 　　 2.在非公平的条件下：
 *
 * 　　　　　　2.1写锁是可以插队的（写锁插队可以避免饥饿）
 *
 * 　　　　　　2.2读锁仅在等待队列头结点不是想获取写锁的线程的时候是可以插队的。 (此案例演示读锁插队 子线程必须多点要不演示不出效果 此案例1000)
 *
 * 执行过程：
 *      * 线程1获取写锁，其他线程尝试获取
 *      * 线程1释放后，队列头为线程2(读锁)，进行读；线程3也进行读，
 * 结果：线程1在释放写锁瞬间 - 部分子线程获取到读锁进行读 （插队到队列头为2读锁的线程之前进行读） - 2 3 读完释放部分插队子线程释放 -
 *      线程4成功获取写锁 - 4 释放写锁 - 5 得到读锁 剩余子线程也得到读锁 - 都读完最后释放读锁
 *
 */
public class NonfairBargeDemo {
    //读写锁 true 公平锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    //读锁
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    //写锁
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到写锁，正在写入");
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(()->write(),"Thread1").start();
        new Thread(()->read(),"Thread2").start();
        new Thread(()->read(),"Thread3").start();
        new Thread(()->write(),"Thread4").start();
        new Thread(()->read(),"Thread5").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread[] = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    thread[i] = new Thread(() -> read(), "子线程创建的Thread—" + i);
                }
                for (int i = 0; i < 1000; i++) {
                    thread[i].start();
                }
            }
        }).start();
    }
}
