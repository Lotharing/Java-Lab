package top.lothar.juc.lock.collections.copyonwrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 描述：演示CopyOnWriteArrayList可以在迭代的过程中修改数组内容，但是ArrayList不行，对比
 *
 * CopyOnWriteArrayList： 读取完全不加锁 写入也不会阻塞读取 只有写入和写入之间需要同步等待
 *
 * 执行流程：
 * list is[1, 2, 3, 4, 5]
 * 1
 * list is[1, 2, 3, 4, 5]
 * 2
 * list is[1, 2, 3, 4]
 * 3
 * list is[1, 2, 3, 4, 3 found]
 * 4
 * list is[1, 2, 3, 4, 3 found]
 * 5 这里依旧遍历的 5 写的found不影响原先读的
 */
public class CopyOnWriteArrayListDemo1 {

    public static void main(String[] args) {
        /**
         * 会抛出异常 迭代过程中不能修改
         */
        //ArrayList<String> list = new ArrayList<>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println("list is" + list);
            String next = iterator.next();
            System.out.println(next);

            if (next.equals("2")) {
                list.remove("5");
            }
            if (next.equals("3")) {
                list.add("3 found");
            }
        }
    }
}
