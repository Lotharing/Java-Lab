package top.lothar.juc.lock.collections.predecessor;

import java.util.Vector;

/**
 * 描述：     演示Vector，主要是看Vector源码
 */
public class VectorDemo {

    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        //public synchronized E get(int index) { 源码线程安全的  很多方法都是synchronized修饰的
        System.out.println(vector.get(0));
    }

}
