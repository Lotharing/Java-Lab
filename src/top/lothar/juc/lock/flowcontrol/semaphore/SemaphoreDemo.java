package top.lothar.juc.lock.flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 描述：     演示Semaphore 信号量(许可证)用法  场景： 速度慢 任务繁重的情况
 */
public class SemaphoreDemo {
    //permits 信号量数量  fair 公平非公平  static可以在全局范围内控制
    static Semaphore semaphore = new Semaphore(5, true);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            service.submit(new Task());
        }
        service.shutdown();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                //一次拿3个许可证（工厂排污太大 - 根据负载分配权重）
                semaphore.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "拿到了许可证");
            try {
                //模拟执行任务
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "释放了许可证");
            semaphore.release(2);
        }
    }
}
