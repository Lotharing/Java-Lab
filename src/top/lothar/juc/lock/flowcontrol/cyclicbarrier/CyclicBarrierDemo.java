package top.lothar.juc.lock.flowcontrol.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 描述：    演示CyclicBarrier 可以轮回重用 而 countdownlatch不呢个
 *
 * 执行过程：
 * 线程0现在前往集合地点
 * 线程2现在前往集合地点
 * 线程1现在前往集合地点
 * 线程3现在前往集合地点
 * 线程4现在前往集合地点
 *
 * 线程1到了集合地点，开始等待其他人到达
 * 线程0到了集合地点，开始等待其他人到达
 * 线程4到了集合地点，开始等待其他人到达  满足栅栏的3个
 *
 * 所有人都到场了， 大家统一出发！
 * 线程4出发了
 * 线程1出发了
 * 线程0出发了
 *
 * 线程3到了集合地点，开始等待其他人到达
 * 线程2到了集合地点，开始等待其他人到达 不够了就不放开了
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //像是个栅栏 等所有任务都到了 在执行
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有人都到场了， 大家统一出发！");
            }
        });
        for (int i = 0; i <=5; i++) {
            new Thread(new Task(i, cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable{
        private int id;
        private CyclicBarrier cyclicBarrier;

        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + id + "现在前往集合地点");
            try {
                Thread.sleep((long) (Math.random()*10000));
                System.out.println("线程"+id+"到了集合地点，开始等待其他人到达");
                //await 线程会到这里等待 知道满足栅栏拦住的数量 不够就一直不放
                cyclicBarrier.await();
                System.out.println("线程"+id+"出发了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
