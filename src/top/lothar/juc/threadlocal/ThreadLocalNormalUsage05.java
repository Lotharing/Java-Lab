package top.lothar.juc.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal定义了四个方法:
 *
 * get():返回此线程局部变量当前副本中的值
 * set(T value):将线程局部变量当前副本中的值设置为指定值
 * initialValue():返回此线程局部变量当前副本中的初始值
 * remove():移除此线程局部变量当前副本中的值
 * ThreadLocal还有一个特别重要的静态内部类ThreadLocalMap，该类才是实现线程隔离机制的关键。get()、set()、remove()都是基于该内部类进行操作，ThreadLocalMap用键值对方式存储每个线程变量的副本，key为当前的ThreadLocal对象，value为对应线程的变量副本。
 * 试想，每个线程都有自己的ThreadLocal对象，也就是都有自己的ThreadLocalMap，对自己的ThreadLocalMap操作，当然是互不影响的了，
 * 这就不存在线程安全问题了，所以ThreadLocal是以空间来交换安全性的解决思路。加锁是时间交换安全性
 *
 * 每个ThreadLocal内部都有一个ThreadLocalMap,他保存的key是ThreadLocal的实例，他的值是当前线程的局部变量的副本的值。
 *
 * 目的：     效率提高（池）+节省资源（共享对象+池） + 线程安全 （ThreadLocal复印实例副本，10个线程就有10个属于自己的dateFormat对象）
 * 描述：     利用ThreadLocal，给每个线程分配自己的dateFormat对象，保证了线程安全，高效利用内存
 */
public class ThreadLocalNormalUsage05 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        try{
            for (int i = 0; i < 1000; i++) {
                int finalI = i;
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        String date = new ThreadLocalNormalUsage05().date(finalI);
                        System.out.println(date);
                    }
                });
            }
            threadPool.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // ThreadLocal被垃圾回收后，在ThreadLocalMap里对应的Entry的键值会变成null，
            // 但是Entry是强引用，那么Entry里面存储的Object，并没有办法进行回收，
            // 所以ThreadLocalMap 做了一些额外的回收工作 remove , 如果不回收可能会导致OOM内存溢出
            // 源码：ThreadLocalMap.remove(K:this[ThreadLocal])
            ThreadSafeFormatter.dateFormatThreadLocal2.remove();
        }

    }

    public String date(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //ThreadLocal所生成的对象
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal2.get();
        return dateFormat.format(date);
    }
}

/**
 * 生产出安全的工具类 / 线程独享
 */
class ThreadSafeFormatter {
    /**
     * 写法1
     */
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        /**
         * 初始化作用  可以利用ThreadLocal来生成data对象
         * @return
         */
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    /**
     * 写法2 Lambda表达式
     */
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
}