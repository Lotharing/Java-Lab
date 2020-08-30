package top.lothar.juc.lock.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：  演示公平和不公平两种情况  Lock lock = new ReentrantLock(true); 1.true 公平 2.false/默认 非公平
 *
 *  公平锁 执行情况：
 *      0 开始打印 0正在打印
 *      1 2 3 4 开始打印 [进入 run 线程执行中...]
 *      1 正在打印 （0打印第二次会排到工作队列最后 此时工作队列 2 3 4 0）
 *      2 正在打印           当前工作队列： 3 4 0 1
 *      3 正在打印           当前工作队列： 4 0 1 2
 *      4 正在打印           当前工作队列： 0 1 2 3
 *      0 正在打印 打印完毕         当前工作队列： 1 2 3 4
 *      1 正在打印 打印完毕         当前工作队列： 2 3 4
 *      2 正在打印 打印完毕         当前工作队列： 3 4
 *      3 正在打印 打印完毕         当前工作队列： 4
 *      4 正在打印 打印完毕         当前工作队列： null
 *
 *  非公平锁 执行情况：
 *      0 开始打印 0正在打印
 *      1 开始打印
 *      2 开始打印
 *      3 开始打印
 *      4 开始打印
 *      0 正在打印 （唤醒其他1 2 3 4 任何一个都需要时间 , 0 本身就已经就绪直接再次拿锁）
 *      0 打印完毕
 *      1 正在打印
 *      1 正在打印
 *      1 打印完毕
 *      2 正在打印
 *      2 正在打印
 *      2 打印完毕
 *      3 正在打印
 *      3 正在打印
 *      3 打印完毕
 *      4 正在打印
 *      4 正在打印
 *      4 打印完毕
 */
public class FairLock {

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread thread[] = new Thread[5];
        for (int i = 0; i < 5; i++) {
            thread[i] = new Thread(new Job(printQueue));
        }
        //休息 启动线程有顺序性
        for (int i = 0; i < 5; i++) {
            thread[i].start();
            try {
                //稍稍休息
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Job implements Runnable {

    PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        //多个线程进入开始打印
        System.out.println(Thread.currentThread().getName() + "开始打印");
        //有锁
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + "打印完毕");
    }
}

class PrintQueue {
    /**
     * true公平锁
     * public ReentrantLock(boolean fair) {
     *         sync = fair ? new FairSync() : new NonfairSync();
     * }
     */
    private Lock queueLock = new ReentrantLock(false);

    public void printJob(Object document) {
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印，需要" + duration +"秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
        //打印两次
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在打印，需要" + duration+"秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}
