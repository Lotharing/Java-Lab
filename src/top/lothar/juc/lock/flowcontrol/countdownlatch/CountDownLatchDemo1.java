package top.lothar.juc.lock.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：     工厂中，质检，5个工人检查，所有人都认为通过，才通过
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {
        //构造函数等待次数 5
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + no + "完成了检查。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //每次执行线程执行完对countdown - 1
                        latch.countDown();
                    }
                }
            };
            //每个任务放线程池
            service.submit(runnable);
        }
        System.out.println("等待5个人检查完.....");
        //等待门闩 5 -> 0 后才会执行
        latch.await();
        System.out.println("所有人都完成了工作，进入下一个环节。");
    }
}
