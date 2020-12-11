package top.lothar.juc.Producer.wait;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/11 10:53
 */
public class Main {

    public static void main(String[] args) {
        BufferStorage bufferStorage = new BufferStorage(10);

        Product product = new Product(bufferStorage);
        Customer customer = new Customer(bufferStorage);

        product.start();
        customer.start();
    }



}
