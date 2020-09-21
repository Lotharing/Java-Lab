package top.lothar.juc.lock.cache;

import top.lothar.juc.lock.cache.computable.Computable;
import top.lothar.juc.lock.cache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：     用装饰者模式，给计算器自动添加缓存功能
 *
 * 问题： 第一个线程算出666 第三次是等第二次计算完才从缓存拿出 synchronized的锅
 */
public class ImoocCache3<A,V> implements Computable<A,V> {

    private final Map<A, V> cache = new HashMap();

    private  final Computable<A,V> c;

    public ImoocCache3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache3<String, Integer> expensiveComputer = new ImoocCache3<>(
                new ExpensiveFunction());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("666");
                    System.out.println("第一次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("666");
                    System.out.println("第三次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("667");
                    System.out.println("第二次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
