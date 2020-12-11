package top.lothar.juc.Producer.wait;

import top.lothar.juc.Producer.wait.BufferStorage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/11 10:50
 */
public class Product extends Thread{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private BufferStorage bufferStorage;

    public Product(BufferStorage bufferStorage){
        super();
        this.bufferStorage = bufferStorage;
        super.setName("Product");
    }

    @Override
    public void run() {
        for(int i = 0 ; i < 100; i++){
            bufferStorage.set("interview -- >"+atomicInteger.getAndIncrement());
        }
    }
}
