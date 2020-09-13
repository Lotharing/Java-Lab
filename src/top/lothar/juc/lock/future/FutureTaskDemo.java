package top.lothar.juc.lock.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 描述：     演示FutureTask的用法
 *
 *  创建线程：
 *  1 继承Thread 重写run
 *  2 实现runnable 实现run Thread(runnable对象引用)
 *  3.实现Callable 实现call Thread(callable对象引用) call有返回值可以用Future<V>接受 ｜
 *    通过FutureTask(callable引用)  new Thread(futureTask对象引用) 且可以从futureTask.get() 出返回结果
 *    [因为FutureTask类实现RunnableFuture接口  RunnableFuture接口继承Runnable 和 Future]
 *
 * 源码：
 *
 *    class FutureTask<V> implements RunnableFuture<V>
 *
 *    interface RunnableFuture<V> extends Runnable, Future<V>   run 方法
 *
 * （1）创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
 *
 * （2）创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
 *
 * （3）使用FutureTask对象作为Thread对象的target（其实还是实现了Runnable的）创建并启动新线程。
 *
 * （4）调用FutureTask对象的get()（还是future.get()）方法来获得子线程执行结束后的返回值
 *
 *
 */
public class FutureTaskDemo {

    public static void main(String[] args) {
        Task task = new Task();
        //传入实现callable的task
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);
        //可以直接执行线程任务
//        new Thread(integerFutureTask).start();
        //缓存线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //提交FutureTask 相当于是一个Runnable 源码：class FutureTask<V> implements RunnableFuture<V>
        service.submit(integerFutureTask);

        try {
            System.out.println("task运行结果："+integerFutureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 实现Callable的task任务
 */
class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在计算");
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}

