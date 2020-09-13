package top.lothar.juc.lock.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import sun.java2d.pipe.RenderingEngine;

/**
 * 描述：     TODO
 */
public class AQSDemo {

    public static void main(String[] args) {
        /**
         * 内部都有一个sync类 继承的 AQS
         */
//        new Semaphore(5);
//        new CountDownLatch(1);
//        new ReentrantLock();
//        new HashMap<>()
        Map<Integer,String> modelMap  = new HashMap<>();
        modelMap.put(1,"Z");
        modelMap.put(1,"T");
        System.out.println(modelMap.get(1));
    }
}
