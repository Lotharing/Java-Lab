package top.lothar.jvm.classloader;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/17 22:23
 */
public class ABInit {

    // 初始化是按照顺序加载的 在new ABInit()阶段已经回调类构造器这时候a = 1 b =1

    private static ABInit abInit  = new ABInit();
    // 到这里a有个赋值操作 = 0
    private static int a = 0 ;
    private static int b;

    public ABInit(){
        a++;
        b++;
        System.out.println("init Class a=="+a+"  b=="+b);
    }

    public static ABInit getInstance(){
        return abInit;
    }

    public int getA(){
        return a;
    }

    public int getB(){
        return b;
    }

    public static void main(String[] args) {
        ABInit instance = ABInit.getInstance();
        System.out.println(instance.getA());
        System.out.println(instance.getB());
    }
}
