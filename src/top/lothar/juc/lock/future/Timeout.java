package top.lothar.juc.lock.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 描述：     演示get的超时方法
 *           需要注意超时后处理，调用future.cancel()
 *           演示cancel传入参数 true和false的区别，代表是否中断正在执行的任务。
 *
 * 执行描述：
 *          Future - 演示Future.Done()判断是否执行完毕 /
 *          get(timeout,unit)超时处理
 *          如果future.get(true)则会给任务抛中断信号 超时TimeOutException中兜底返回广告 不要task接受到取消任务返回的AD
 *          如果是future.get(false)task会继续执行超时就返回超时处理/不超时就返回task执行信息
 */
public class Timeout {

    private static final Ad DEFAULT_AD = new Ad("无网络时候的默认广告");
    private static final ExecutorService exec = Executors.newFixedThreadPool(10);

    /**
     * 广告信息name
     */
    static class Ad {

        String name;

        public Ad(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * Callable线程任务
     */
    static class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                //future.cancel(true);收到中断信号
                System.out.println("sleep期间被中断了");
                return new Ad("被中断时候的默认广告");
            }
            return new Ad("旅游订票哪家强？找某程");
        }
    }


    public void printAd() {
        Future<Ad> f = exec.submit(new FetchAdTask());
        Ad ad;
        try {
            //1. 任务休眠3s  那么可以直接返回AD [旅游订票哪家强？找某程]
            //2. 任务休眠3s  限制2s超时处理 重写Future.get方法 超时处理 2s没有获取到就抛出TimeoutException
            ad = f.get(2000, TimeUnit.MILLISECONDS);
            //是否执行完毕
            System.out.println(f.isDone());
        } catch (InterruptedException e) {
            ad = new Ad("被中断时候的默认广告");
        } catch (ExecutionException e) {
            ad = new Ad("异常时候的默认广告");
        } catch (TimeoutException e) {
            ad = new Ad("超时时候的默认广告");//兜底广告
            System.out.println("超时，未获取到广告");
            //取消  true 中断执行当前任务 task收到InterruptedException信号   就算你中断业务广告执行我也不用 后边某程也不会被执行已经被中断的
            // false 不中断当前任务 正常返回[旅游订票哪家强？找某程]的逻辑  但是超时了就兜底代替了
            boolean cancel = f.cancel(true);
            System.out.println("cancel的结果：" + cancel);
        }
        //线程关闭 任务执行完毕
        exec.shutdown();
        System.out.println(ad);
    }

    public static void main(String[] args) {
        Timeout timeout = new Timeout();
        timeout.printAd();
    }
}

