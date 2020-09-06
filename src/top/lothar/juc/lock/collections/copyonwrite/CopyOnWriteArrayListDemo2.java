package top.lothar.juc.lock.collections.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 描述：     对比两个迭代器
 * 执行结果：
 * [1, 2, 3]
 * [1, 2]
 *
 * 1
 * 2
 * 3
 *
 * 1
 * 2
 */
public class CopyOnWriteArrayListDemo2 {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});

        System.out.println(list);

        Iterator<Integer> itr1 = list.iterator();
        //这个移除不影响 itr1的数据
        list.remove(2);
        Thread.sleep(1000);
        System.out.println(list);
        /**
         * 迭代器的诞生时间 就是他拿这个时候的数据
         */
        Iterator<Integer> itr2 = list.iterator();

        itr1.forEachRemaining(System.out::println);
        itr2.forEachRemaining(System.out::println);

    }
}
