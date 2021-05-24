package top.lothar.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>测试内存溢出</h1>
 * 1.命令行输入 jvisualvm 打开JVM监控工具
 * 2.-Xmx128M -Xms128M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zhaolutong/Desktop/gc.hprof -XX:+PrintGCDetails
 * 3.用MAT工具分析
 * @author LuTong.Zhao
 * @Date 2021/5/24 21:43
 */
public class MemoryOverflow {

    // 1k
    private static final Integer K = 1024;

    public static void main(String[] args) {
        // 8bit位 = 1 字节 ， 1024字节 * 1024个 = 1M
        int size = K * K * 8;
        List<byte[]> list = new ArrayList<>();
        for (int i = 0 ; i < K; i++) {
            System.out.println("JVM写入数据" + (i+1) +"M");
            try {
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
            list.add(new byte[size]);
        }
    }
}
