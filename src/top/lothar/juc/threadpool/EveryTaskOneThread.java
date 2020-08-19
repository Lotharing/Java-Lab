package top.lothar.juc.threadpool;

/**
 * 描述：创建线程
 */
public class EveryTaskOneThread {

    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.start();
    }

    /**
     * 集成Runnabl的线程
     */
    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }

}
