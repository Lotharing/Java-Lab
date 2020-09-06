package top.lothar.juc.lock.collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：     线程安全的ConcurrentHashMap Map接口中的get set 等方法
 */
public class ConcurrentHashMapDemo implements Runnable{

    private int key;
    private int value;

    public ConcurrentHashMapDemo(int key,int value){
        this.key = key;
        this.value = value;
    }

    final static ConcurrentHashMap<Integer,Object> map = new ConcurrentHashMap<Integer, Object>();

    public static void main(String[] args) {
        new Thread(new ConcurrentHashMapDemo(1,1),"Thread1").start();
        new Thread(new ConcurrentHashMapDemo(2,2),"Thread2").start();
    }

    @Override
    public void run() {
        map.put(key,value);
        System.out.println(map.get(key));
    }


}
