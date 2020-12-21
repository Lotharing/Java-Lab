package top.lothar.juc.Producer.wait;

import java.util.concurrent.TimeUnit;

/**
 * <h1>Notify 和 NotifyAll 唤醒方式演示</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/14 14:03
 */
public class NotifyAndNotifyAll {
    public static void main(String[] args) {
        Object lock = new Object();

        for (int i = 0; i < 3; i++) {
            MyThread t = new MyThread("Thread" + i, lock);
            t.start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("-----Main Thread notify-----");
            synchronized (lock) {
                //lock.notify();
                /**
                 * Thread0 is waiting.
                 * Thread1 is waiting.
                 * Thread2 is waiting.
                 * -----Main Thread notify-----
                 * Thread1 has been notified.
                 * Main Thread is end.
                 */
                lock.notifyAll();
                /**
                 * Thread0 is waiting.
                 * Thread1 is waiting.
                 * Thread2 is waiting.
                 * -----Main Thread notify-----
                 * Thread1 has been notified.
                 * Thread0 has been notified.
                 * Thread2 has been notified.
                 * Main Thread is end.
                 */
            }
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Main Thread is end.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread {
        private String name;
        private Object object;
        public MyThread(String name, Object object) {
            this.name = name;
            this.object = object;
        }
        @Override
        public void run() {
            System.out.println(name + " is waiting.");
            try {
                synchronized (object) {
                    object.wait();
                }
                System.out.println(name + " has been notified.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
