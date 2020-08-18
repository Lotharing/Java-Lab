package top.lothar.io;

import java.nio.ByteBuffer;

/**
 * @author : LuTong.Zhao
 * @date : 14:46 2020/8/7
 */
public class NewIoAPI {


    public static void main(String[] args) {

        filp();
    }

    /**
     * put() 放入缓冲区
     *
     * filp()  limit变成了position的位置了而position变成了0
     * 每当要从缓存区的时候读取数据时，就调用filp()“切换成读模式”。
     *
     * get() 从缓冲区取出
     *
     * 使用clear()函数，这个函数会“清空”缓冲区：数据没有真正被清空，只是被遗忘掉了
     */
    public static void filp(){
        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 看一下初始时4个核心变量的值
        System.out.println("初始时-->limit--->"+byteBuffer.limit());
        System.out.println("初始时-->position--->"+byteBuffer.position());
        System.out.println("初始时-->capacity--->"+byteBuffer.capacity());
        System.out.println("初始时-->mark--->" + byteBuffer.mark());
        System.out.println("--------------------------------------");
        // 添加一些数据到缓冲区中
        String s = "I LIKE JAVA";
        byteBuffer.put(s.getBytes());
        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->"+byteBuffer.limit());
        System.out.println("put完之后-->position--->"+byteBuffer.position());
        System.out.println("put完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());
        //切换读模式
        byteBuffer.flip();
        System.out.println("--------------------------------------");
        System.out.println("flip完之后-->limit--->"+byteBuffer.limit());
        System.out.println("flip完之后-->position--->"+byteBuffer.position());
        System.out.println("flip完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("flip完之后-->mark--->" + byteBuffer.mark());

        // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
        byte[] bytes = new byte[byteBuffer.limit()];
        // 将读取的数据装进我们的字节数组中
        byteBuffer.get(bytes);
        // 输出数据
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println("--------------------------------------");
        System.out.println("get完之后-->limit--->"+byteBuffer.limit());
        System.out.println("get完之后-->position--->"+byteBuffer.position());
        System.out.println("get完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("get完之后-->mark--->" + byteBuffer.mark());
        byteBuffer.clear();
        System.out.println("--------------------------------------");
        System.out.println("clear完之后-->limit--->"+byteBuffer.limit());
        System.out.println("clear完之后-->position--->"+byteBuffer.position());
        System.out.println("clear完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("clear完之后-->mark--->" + byteBuffer.mark());


    }



}
