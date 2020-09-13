package top.lothar.juc.lock.future;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：     演示批量提交任务时，用List来批量接收结果
 *
 * Future - 演示Future批量提交任务时，用List来批量接收结果
 * ArrayList<Future> for循环submit任务进线程池且把对应的Future存储在List中很多任务色返回结果
 * 注意：如果线程池2个 则每次就只能成功两个也就是只能返回两个Future 所以就只能每次打印两个Future
 */
public class MultiFutures {

    public static void main(String[] args) throws InterruptedException {
        //20个线程池
        ExecutorService service = Executors.newFixedThreadPool(20);
        //通过List存放多个Future
        ArrayList<Future> futures = new ArrayList<>();
        //submit20个随机数任务,切任务返回结果放入list<future>
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = service.submit(new CallableTask());
            futures.add(future);
        }
        //休眠5s  保证执行完毕 然后future有返回数据
        Thread.sleep(5000);
        for (int i = 0; i < 20; i++) {
            //获取每个任务的返回的Future的包装结果
            Future<Integer> future = futures.get(i);
            try {
                //打印每个任务的返回存储在Future中的结果  线程池是2的时候 这里是阻塞的直到task 3s过去才有结果
                //[也就是future获取结果没执行完是一致阻塞的]
                Integer integer = future.get();
                System.out.println(integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 随机数任务
     */
    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            //等待三秒
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
