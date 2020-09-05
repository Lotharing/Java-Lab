package top.lothar.juc.lock.immutable;

/**
 * 描述：    final 修饰的在常量池中
 */
public class FinalStringDemo1 {

    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        //true b在常量池有 已经知道是悟空了指向和a同样的地址  同一个常量
        System.out.println((a == c));
        //false 不会提前知道d的值  运行时才会确定 堆上的
        System.out.println((a == e));
    }
}
