package top.lothar.juc.lock.flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：     演示Condition的基本用法
 *
 * 执行描述：
 *
 * 条件不满足，开始await
 * 准备工作完成，唤醒其他的线程
 * 条件满足了，开始执行后续的任务
 */
public class ConditionDemo1 {
    private ReentrantLock lock = new ReentrantLock();
    //lock中condition
    private Condition condition = lock.newCondition();

    void method1() throws InterruptedException {
        lock.lock();
        try{
            System.out.println("条件不满足，开始await");
            //开始等待
            condition.await();
            System.out.println("条件满足了，开始执行后续的任务");
        }finally {
            lock.unlock();
        }
    }

    void method2() {
        lock.lock();
        try{
            System.out.println("准备工作完成，唤醒其他的线程");
            //唤醒 使用同一个condition的线程
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo1 conditionDemo1 = new ConditionDemo1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //等待时间的时候await了
                    Thread.sleep(1000);
                    //新开一个线程进行执行 去唤醒
                    // 要不直接调用 m1 主线程阻塞 m2 也运行不了 只能使用子线程
                    conditionDemo1.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        conditionDemo1.method1();
    }
}
