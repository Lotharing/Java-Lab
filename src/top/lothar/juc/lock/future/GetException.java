package top.lothar.juc.lock.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：     演示get方法过程中抛出异常
 *           for循环为了演示抛出Exception的时机：并不是说一产生异常就抛出，直到我们get执行时，才会抛出。
 */
public class GetException {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        //提交线程池一个异常
        Future<Integer> future = service.submit(new CallableTask());

        try {
            //休眠时候 线程池中执行已经出现异常 IllegalArgumentException  但是我们不知道 只有get时候才会出现
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                Thread.sleep(500);
            }
            //只返是否完成 关于好坏并不关心
            System.out.println("是否执行完毕:"+future.isDone());
            //获取 出现异常ExecutionException  就算call抛出的是IllegalArgumentException
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException异常");
        }
    }


    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            throw new IllegalArgumentException("Callable抛出异常");
        }
    }
}
