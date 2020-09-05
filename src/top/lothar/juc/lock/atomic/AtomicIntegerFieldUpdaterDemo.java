package top.lothar.juc.lock.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 描述：     演示AtomicIntegerFieldUpdater的用法 把基本类型升级为原子类型
 *
 * 执行结果：
 * 没有升级前的基本变量 - 普通变量：18725
 * 升级后的原子变量 - 升级后的结果20000
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable{
    //两个候选人 - int 基本类型的score
    static Candidate tom;
    static Candidate peter;

    /**
     * 把Candidate中的score升级为原子
     */
    public static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater
            .newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            //基本类型直接累加
            peter.score++;
            //把tom通过AtomicIntegerFieldUpdater包装升级实现原子（fieldName就是保证原子的）  累加操作
            scoreUpdater.getAndIncrement(tom);
        }
    }

    /**
     * 内部类
     */
    public static class Candidate {
        //分数
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        tom=new Candidate();
        peter=new Candidate();
        AtomicIntegerFieldUpdaterDemo r = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("普通变量："+peter.score);
        System.out.println("升级后的结果"+ tom.score);
    }
}
