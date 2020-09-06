package top.lothar.juc.lock.collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：     组合操作并不保证线程安全
 */
public class OptionsNotSafe implements Runnable {

    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<String, Integer>();

    public static void main(String[] args) throws InterruptedException {
        scores.put("小明", 0);
        Thread t1 = new Thread(new OptionsNotSafe());
        Thread t2 = new Thread(new OptionsNotSafe());
        t1.start();
        t2.start();
        //等待线程结束 在让main函数执行
        t1.join();
        t2.join();
        System.out.println(scores);
    }

    /**
     * 线程不安全的 因为map虽然线程安全
     * 只能保证同时get put不出问题 不能保证get完对数值重新操作的情况
     */
//    @Override
//    public void run() {
//        for (int i = 0; i < 1000; i++) {
//            Integer score = scores.get("小明");
//            Integer newScore = score + 1;
//            scores.put("小明",newScore);
//        }
//    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            while (true) {
            Integer score = scores.get("小明");
            Integer newScore = score + 1;
            scores.put("小明",newScore);
                //类似cas操作 有可能不成功因为多线程操作
                boolean b = scores.replace("小明", score, newScore);
                //写成功跳出 for循环1000次两个线程 2000分
                if (b) {
                    break;
                }
            }
        }
    }

}
