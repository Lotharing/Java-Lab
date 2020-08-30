package top.lothar.juc.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：     TODO
 */
public class RecursionDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println("已经对资源进行了处理");
            if (lock.getHoldCount()<5) {
                System.out.println(lock.getHoldCount());
                //递归再次拿同一把锁
                accessResource();
                //递归完会统一执行 -> 释放
                System.out.println("last - >"+lock.getHoldCount());
            }
        } finally {
            //统一释放
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        accessResource();
    }
}
