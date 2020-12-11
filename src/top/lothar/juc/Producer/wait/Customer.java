package top.lothar.juc.Producer.wait;

import top.lothar.juc.Producer.wait.BufferStorage;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/11 10:51
 */
public class Customer extends Thread{

    private BufferStorage bufferStorage;

    public Customer(BufferStorage bufferStorage){
        super();
        this.bufferStorage = bufferStorage;
        super.setName("Customer");
    }

    @Override
    public void run() {
        for(int i = 0 ; i < 100; i++){
            bufferStorage.get();
        }
    }
}
