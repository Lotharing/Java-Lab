package top.lothar.juc.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 描述：     演示关闭线程池
 * shutdown 这种方式收到停止命令会 把正在执行中的线程 和任务队列中的都执行完毕才会关闭线程池 拒绝新任务抛出异常
 *
 * isTerminated  会根据 整个任务是否执行完毕  执行完毕的情况下才会返回true 表示停止 否则就是false表示线程还在执行中
 *
 * awaitTerminated  主要作用是检测不是关闭，会看是否在多少时间内线程停止 在时间内关闭了返回true  否则返回false
 * ｜  与shutdown关闭没关系，它只关心时间内是否执行完毕 ｜ 1.线程执行完毕了 2.等待过程被打断了 3.等待的时间到了
 *
 * shutdownNow 立刻关闭线程池： 关闭中正在执行的会被iterruupt终端，队列中的会直接返回列表
 */
public class ShutDown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShutDownTask());
        }
        //运行1500ms 后关闭线程
        Thread.sleep(1500);
          // 立刻关闭线程池： 关闭中正在执行的会被interruupt终端，队列中的会直接返回任务没有执行线程的列表
//        List<Runnable> runnableList = executorService.shutdownNow();

        executorService.shutdown();
        // 停止后提交新任务是抛出异常： j.u.c.RejectedExecutionException
        executorService.execute(new ShutDownTask());

          //会返回 false - true
//        System.out.println(executorService.isShutdown());
//        executorService.shutdown();
//        System.out.println(executorService.isShutdown());

          //线程如果执行完毕才返回关闭true状态
//        System.out.println(executorService.isTerminated());
//        Thread.sleep(10000);
//        System.out.println(executorService.isTerminated());

          //7秒中过后还是没有终止就会返回false
//        boolean b = executorService.awaitTermination(7L, TimeUnit.SECONDS);
//        System.out.println(b);

//        executorService.execute(new ShutDownTask());
    }
}

class ShutDownTask implements Runnable {


    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}
