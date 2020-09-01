package top.lothar.juc.lock.readwrite;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <h1>ReentrantReadWriteLock 非公平-读写锁 降级演示案例</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/9/1 10:38 上午
 */
public class DowngradingExample{
    //保证原子性
    static AtomicInteger atomicInteger = new AtomicInteger();
    //非公平读写锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    //读写锁
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();

    public void plus(){
        writeLock.lock();
        try {
            atomicInteger.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + " 累加 i ->"+atomicInteger.get());
            //写锁不释放要不一瞬间就会被其他线程抢 直接上读锁 后 在释放写更安全
            readLock.lock();
            System.out.println("降级读锁成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
            System.out.println("释放写锁");
        }

        try {
            System.out.println("i现在初始化为" + atomicInteger.get() + "了,但是降级的读锁我没释放 ！！！"); //这里还要继续用到i，此时其他线程也可以读了，但是不能写
        } finally {
            //readLock.unlock();
        }

    }

    public void noReleaseReadAndLockWrite(){
        System.out.println("你不释放读锁我不能去拿写锁");
        writeLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" get - 获取 i ->"+atomicInteger.get());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    public void releaseReadAndLockRead(){
        System.out.println("你不释放读锁,我照样可以共享读");
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+" get - 获取 i ->"+atomicInteger.get());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }



    public static void main(String[] args) {
        //base 0
        new Thread(() -> new DowngradingExample().plus(),"Thread-0").start();
        /**
         * 组合1：
         *      [演示写锁降级 在降级后的读锁不释放的情况下,其他线程不能拿到死锁]
         * 执行流程：
         *      0 加写锁 +1 -> 0 降级为读锁 -> 0 释放写锁 （读锁没释放） -> 1 不能拿到写锁 , 不能获取 atomicInteger 的值
         *
         */
        //new Thread(() -> new DowngradingExample().noReleaseReadAndLockWrite(),"Thread-1").start();
        /**
         * 组合2：
         *      [演示写锁降级 其他线程读锁共享数据]
         * 执行流程：
         *      0 加写锁 +1 -> 0 降级为读锁 -> 0 释放写锁 （读锁没释放）-> 1 成功拿读锁 atomicInteger = 1
         */
        new Thread(() -> new DowngradingExample().releaseReadAndLockRead(),"Thread-1").start();

    }



}

