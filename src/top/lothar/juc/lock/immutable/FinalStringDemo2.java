package top.lothar.juc.lock.immutable;

/**
 * 描述：
 */
public class FinalStringDemo2 {

    public static void main(String[] args) {
        String a = "wukong2";
        //方法获得的 编译器无法确定b的值  也是运行中生成在堆上的
        final String b = getDashixiong();
        String c = b + 2;
        //false
        System.out.println(a == c);

    }

    private static String getDashixiong() {
        return "wukong";
    }
}
