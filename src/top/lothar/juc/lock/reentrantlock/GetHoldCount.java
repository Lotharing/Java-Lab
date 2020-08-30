package top.lothar.juc.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：     可重入锁 lock.getHoldCount() 打印锁被拿几次
 */
public class GetHoldCount {

    private  static ReentrantLock lock =  new ReentrantLock();

    public static void main(String[] args) {
        System.out.println(lock.getHoldCount());  //0
        lock.lock();
        System.out.println(lock.getHoldCount());  //1
        lock.lock(); // 拿到锁后 再次拿同一把锁照样可以拿到 可重入到
        System.out.println(lock.getHoldCount());  //2
        lock.lock();
        System.out.println(lock.getHoldCount());  //3
        lock.unlock();
        System.out.println(lock.getHoldCount());  //2
        lock.unlock();
        System.out.println(lock.getHoldCount());  //1
        lock.unlock();
        System.out.println(lock.getHoldCount());  //0
    }
}
