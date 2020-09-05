package top.lothar.juc.lock.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：     演示AtomicInteger的基本用法，对比非原子类的线程安全问题，使用了原子类之后，不需要加锁，也可以保证线程安全。
 *
 * API：
 * get() 获取当前值
 * getAndIncrement() 获取当前值并自增
 * getAndDecrement() 获取当前值并自减
 * getAndSet(int newValue)  获取当前值 设置新值
 * getAndAdd(int delta)     取取当前值 加上预期值 可以负数
 *
 * boolean compareAndSet(int expect,int update) 如果输入数值等于预期值，则以原子方式将改值设置为输入值
 *
 */
public class AtomicIntegerDemo1 implements Runnable {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 原子自身CAS保证安全
     */
    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    private static volatile int basicCount = 0;
    /**
     * synchronized 才能保证安全
     */
    public synchronized void incrementBasic() {
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 r = new AtomicIntegerDemo1();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("普通变量的结果：" + basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }
}
