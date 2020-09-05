package top.lothar.juc.lock.cas;

/**
 * 描述：     模拟CAS操作，等价代码
 */
public class SimulatedCAS {
    //当前值
    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        //如果设置前期待值和当前值相同 compare
        if (oldValue == expectedValue) {
            //设置新的值 swap
            value = newValue;
        }
        //返回之前的值
        return oldValue;
    }
}
