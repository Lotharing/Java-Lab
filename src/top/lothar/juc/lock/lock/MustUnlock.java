package top.lothar.juc.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：     Lock不会像synchronized一样，异常的时候自动释放锁，
 *           所以最佳实践是，finally中释放锁，以便保证发生异常的时候锁一定被释放
 *           悲观锁
 */
public class MustUnlock {
    //最典型的实现就是ReentrantLock
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        //加锁
        lock.lock();
        try{
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName()+"开始执行任务");
        }finally {
            //最终释放锁 很重要
            lock.unlock();
        }
    }
}
