package top.lothar.juc.Producer.wait;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>Object 下 wait / notify 生产者消费者模式</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/11 10:29
 */
public class BufferStorage {

    // 缓冲区大小
    private int maxSize = 0;
    // 容器
    private List<String> list;

    private Object lock = new Object();

    public BufferStorage(int maxSize){
        this.maxSize = maxSize;
        this.list = new LinkedList<>();
    }

    public void set(String str){
        synchronized (lock){
            // 容器满了就 wait 不再生产
            while(list.size()==maxSize){
                try{
                    lock.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println("SET-->"+str);
            list.add(str);
            //唤醒消费者
            lock.notifyAll();
        }
    }

    public void get(){
        synchronized (lock){
            while (list.size()==0){
                try{
                    lock.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println("GET-->"+((LinkedList<String>)list).poll());
            lock.notifyAll();
        }
    }

}