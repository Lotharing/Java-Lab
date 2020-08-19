package top.lothar.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：     演示newFixedThreadPool  规定好的就是4,任务再多也不会执行
 *
 *     public static ExecutorService newFixedThreadPool(int nThreads) {
 *         return new ThreadPoolExecutor(nThreads, nThreads,
 *                                       0L, TimeUnit.MILLISECONDS,
 *                                       new LinkedBlockingQueue<Runnable>());
 *     }
 *     这种线程池看源码会发现构建线程池的参数 用的无界队列
 */
public class FixedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}

class Task implements Runnable {


    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}