package top.lothar.juc.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述：     执行流程：
 *  第一步：
 *      Thread1得到了读锁，正在读取
 *      Thread2得到了读锁，正在读取
 *  第二步：
 *      Thread1释放读锁
 *      Thread2释放读锁
 *      Thread3得到了写锁，正在写入（释放读锁的同时 写得到写锁）
 *  第三步：
 *      Thread3释放写锁
 *      Thread4得到了写锁，正在写入 （写锁释放，其他线程获得到写锁）
 *  第四步：
 *      Thread4释放写锁
 */
public class CinemaReadWrite {
    //读写锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //读锁
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    //写锁
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(()->read(),"Thread1").start();
        new Thread(()->read(),"Thread2").start();
        new Thread(()->write(),"Thread3").start();
        new Thread(()->write(),"Thread4").start();
    }
}
