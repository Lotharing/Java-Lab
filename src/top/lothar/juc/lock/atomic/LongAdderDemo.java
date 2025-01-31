package top.lothar.juc.lock.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 描述：     演示高并发场景下，LongAdder比AtomicLong性能好
 */
public class LongAdderDemo {

    public static void main(String[] args) throws InterruptedException {
        //使用LongAdder 去 对比AtomicLong 类
        LongAdder counter = new LongAdder();
        ExecutorService service = Executors.newFixedThreadPool(20);
        //开始执行时间
        long start = System.currentTimeMillis();
        //提交10000此任务交给20线程处理  每次任务累加10000此
        for (int i = 0; i < 10000; i++) {
            service.submit(new Task(counter));
        }
        //关闭线程池 - 【让任务执行完毕后】
        service.shutdown();
        while (!service.isTerminated()) {
            //是否真的执行完任务了
        }
        //记录执行结束时间
        long end = System.currentTimeMillis();
        System.out.println(counter.sum());
        System.out.println("LongAdder耗时：" + (end - start));
    }

    private static class Task implements Runnable {

        private LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        }
    }
}
