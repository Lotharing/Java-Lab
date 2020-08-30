package top.lothar.juc.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：     TODO
 */
public class PessimismOptimismLock {

    int a;

    public static void main(String[] args) {
        //乐观锁原理 - 运用CAS原理保证线程安全 保证安全
        AtomicInteger atomicInteger = new AtomicInteger();
        //累加方法
        atomicInteger.incrementAndGet();
    }

    /**
     * 悲观锁实现 不同线程不能同时进入
     */
    public synchronized void testMethod() {
        a++;
    }


}
