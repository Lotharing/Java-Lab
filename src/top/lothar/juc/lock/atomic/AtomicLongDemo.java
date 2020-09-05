package top.lothar.juc.lock.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述：     演示高并发场景下，LongAdder累加器比AtomicLong原子性能好
 */
public class AtomicLongDemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicLong counter = new AtomicLong(0);
        ExecutorService service = Executors.newFixedThreadPool(20);
        //记录执行开始时间
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
        //获取执行后的值
        System.out.println(counter.get());
        System.out.println("AtomicLong耗时：" + (end - start));
    }

    private static class Task implements Runnable {

        private AtomicLong counter;

        public Task(AtomicLong counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.incrementAndGet();
            }
        }
    }
}
