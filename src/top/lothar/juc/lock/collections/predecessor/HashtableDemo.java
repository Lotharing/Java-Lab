package top.lothar.juc.lock.collections.predecessor;

import java.util.Hashtable;

/**
 * 描述：     Hashtable 一个单词不是驼峰命名  线程安全的hashMap
 */
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("学完以后跳槽涨薪幅度", "80%");
        //public synchronized V get(Object key) { 很多方法都是synchronized修饰的线程安全的
        System.out.println(hashtable.get("学完以后跳槽涨薪幅度"));
    }

}
