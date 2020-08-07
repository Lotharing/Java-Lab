package top.lothar;

/**
 * @author : LuTong.Zhao
 * @date : 11:50 2020/8/6
 */
public class BaseMethod {

    private static final boolean DEBUG = true;
     /**
     * 描述：用于调试输出 ，替代syso的工具类
     * 注意：静态方法，使用类名进行调用
     * 修改开关DEBUG的值可进行是否输出的切换
     * @param args 可变参数列表
     */
    public static void info (Object...args){
        if (DEBUG) {
            for (Object arg:args){
                System.out.println(arg);
            }
            System.out.println();
        }
    }

}
