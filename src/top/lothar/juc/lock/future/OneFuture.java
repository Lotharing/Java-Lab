package top.lothar.juc.lock.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：     演示一个Future的使用方法
 */
public class OneFuture {

    public static void main(String[] args) {
        //10定量线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        //提交task implements callable  <V> 与callable返回的类型一致泛型
        Future<Integer> future = service.submit(new CallableTask());
        try {
            //从Future中获取get出callable的返回值
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    /**
     * <V>  实现时候指定 泛型  用Future比较高级
     */
    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            //返回一个 Integer
            return new Random().nextInt();
        }
    }

}
