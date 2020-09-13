package top.lothar.juc.lock.future;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：     演示一个Future的使用方法,lambda形式 / 匿名内部类
 */
public class OneFutureLambda {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = () -> {
            Thread.sleep(2000);
            return new Random().nextInt();
        };
        //传入任务
        Future<Integer> future = service.submit(callable);
        Callable<Integer> callableInClass = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        };
        //传入任务

        Future<Integer> futureInClass = service.submit(callableInClass);
        try {
            System.out.println("Lambda - >"+future.get());
            System.out.println("匿名内部类 - >"+futureInClass.get());;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //结束执行 任务执行完毕
        service.shutdown();
    }

}
