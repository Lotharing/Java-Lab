package top.lothar.juc.lock.collections.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 描述：     有界限 阻塞队列 ArrayBlockingQueue
 *
 * 执行过程：
 * 10个候选人都来啦
 * 安排好了Candidate0
 * 安排好了Candidate1
 * 安排好了Candidate2  入队三个
 *
 * Candidate0到了
 * Candidate1到了
 * Candidate2到了
 * 安排好了Candidate3
 * 安排好了Candidate4
 * 安排好了Candidate5
 *
 * Candidate3到了
 * 安排好了Candidate6
 *
 * Candidate4到了
 * Candidate5到了
 * 安排好了Candidate7
 * 安排好了Candidate8
 *
 * Candidate6到了
 * 安排好了Candidate9
 *
 * Candidate7到了
 * Candidate8到了
 * Candidate9到了
 *
 * 所有候选人都结束了
 */
public class ArrayBlockingQueueDemo {


    public static void main(String[] args) {
        //三个容量的有界队列
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        Interviewer r1 = new Interviewer(queue);
        Consumer r2 = new Consumer(queue);
        new Thread(r1).start();
        new Thread(r2).start();
    }
}

/**
 * 生产者
 */
class Interviewer implements Runnable {

    BlockingQueue<String> queue;

    public Interviewer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("10个候选人都来啦");
        for (int i = 0; i < 10; i++) {
            String candidate = "Candidate" + i;
            try {
                queue.put(candidate);
                System.out.println("安排好了" + candidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //约定结束信号
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {

    BlockingQueue<String> queue;

    public Consumer(BlockingQueue queue) {

        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg;
        try {
            while(!(msg = queue.take()).equals("stop")){
                System.out.println(msg + "到了");
            }
            System.out.println("所有候选人都结束了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}