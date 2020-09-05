package top.lothar.juc.lock.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 描述：     演示原子数组的使用方法
 * 以AtomicIntegerArray为例子  还有 AtomicLongArray  AtomicReferenceArray
 *
 * 每一个线程都遍历原子数组对每个位置进行操作
 * 100 个线程不断的进行加减1 最终结果还是0
 */
public class AtomicArrayDemo {

    public static void main(String[] args) {
        //原子数组
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);
        //线程任务
        Incrementer incrementer = new Incrementer(atomicIntegerArray);
        Decrementer decrementer = new Decrementer(atomicIntegerArray);
        //多线程例子
        Thread[] threadsIncrementer = new Thread[100];
        Thread[] threadsDecrementer = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threadsDecrementer[i] = new Thread(decrementer);
            threadsIncrementer[i] = new Thread(incrementer);
            threadsDecrementer[i].start();
            threadsIncrementer[i].start();
        }

        for (int i = 0; i < 100; i++) {
            try {
                threadsDecrementer[i].join();
                threadsIncrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 检验多线程累加累减结束够的值对不对
         */
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i)!=0) {
                System.out.println("发现了错误"+i);
            }
            System.out.println(atomicIntegerArray.get(i));
        }
        System.out.println("运行结束");
    }
}

/**
 * 遍历每一个做自减操作
 */
class Decrementer implements Runnable {

    private AtomicIntegerArray array;

    public Decrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            //数组下标 i
            array.getAndDecrement(i);
        }
    }
}

/**
 * 遍历每一个做自增操作
 */
class Incrementer implements Runnable {

    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}
