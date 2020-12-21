package top.lothar.jvm.classloader;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/16 21:30
 */
public class ClassLoaderStudy {


    public static void main(String[] args) throws ClassNotFoundException {

        String str = "hello class loader";
        // null 因为是启动类加载器，虚拟机自身的，不允许外部使用
        System.out.println(str.getClass().getClassLoader());

        Class driver = Class.forName("java.sql.Driver");
        System.out.println(driver.getClassLoader());

        ClassLoaderStudy classLoaderStudy = new ClassLoaderStudy();
        // AppClassLoader 应用程序类加载器 classpath路径下所有类库
        System.out.println(classLoaderStudy.getClass().getClassLoader());
        // 父类加载器 ExtClassLoader 扩展类加载器
        System.out.println(classLoaderStudy.getClass().getClassLoader().getParent());
        // null 因为是启动类加载器，虚拟机自身的，不允许外部使用
        System.out.println(classLoaderStudy.getClass().getClassLoader().getParent().getParent());

        /**
         * 一，类的装载
         *
         * 双亲委派机制：
         * 加载一个类 往往上级一级一级加载
         * 相当于是一个搜寻类的过程，越是公共的东西越是父加载器做，就比如string最后就是启动类加载器BootStrap加载的，不会让你自己写一个string类
         * 个性化的东西才留给自己的类加载器完成,（能保证大家找都的都是同一个String）
         *
         * 对于已经加载的系统级别的类 好比string 不管你是哪个类加载器 都不能在加载类 / 保证系统类不会被恶意修改或覆盖
         */

        /**
         *
         * 自定义加载器具 继承CLassLoader  重写findClass  读二进制字节流 返回Class ，发现还是用AppClassLoader装载的，因为父级找到类他这路径下
         * 的类，除非你把那个AppClassLoader扫描路径下class删除让他找不到，最后就会委托到子类加载类
         */


        /**
         * 二 类的连接：
         *
         *   01.验证
         * 1.是否符合jvm规范 「类文件结构检查」
         * 2.是否比如final不能被继承，继承抽象类是否有实现 重写等 「元数据验证」
         * 3.「字节码验证」 确保程序语意是合法的，比如if else 主要是对方法体进行校验 ， 操作数栈的类型与压栈数据匹配 ， 类型转换可用有效
         * 4.「符号引用验证」 类自身以外的信息 常量池中各种符号引用（全限定名能不能找到） 匹配校验（调用的方法 ）能不能找到有没有权限
         *
         *   02.准备
         * 1.为类的静态变量分配内存阶段
         *
         *   03.解析
         * 1.常量池中符号引用0x1110111指向内存地址与虚拟机无关 转换直接引用的过程 （也就是转化为虚拟机内部能直接引用到的东西「直接指向目标的指针」）
         * 主要针对 类 接口 字段 方法 接口方法等 进行解析
         *
         *
         */

        /**
         * 三 初始化
         * 1. 为类的静态变量赋初始值，执行类构造器<clinit>的过程
         *    规则： 1.如果类还没有加载连接 就先 加载连接
         *          2.如果类存在父类，且父类还没有初始化，就先初始化父类
         *
         *   例子 child extends parent 各自的静态块中肯定是先执行父类的  多个static时候 规则「从父到子 从上到下」
         *
         *   注意：  1.初始化一个类时候不会初始化他实现的接口
         *          2.初始化一个接口时候不会初始化他的父接口
         *          3.只有程序首次调用接口中的方法/变量时候才会去初始化 虽然我们看不到这个过程
         *          4.调用classloader装载不会初始化，因为不是主动使用
         *
         *          主动使用「1.创建类实例也就是new 2.访问某个类/接口的静态变量 3.调用静态方法
         *          4.反射某个类 5.虚拟机启动的主类也会初始化 6.初始化某个类子类，父类还没初始化
         *          7.定义类default 「public default void a ();」方法的接口  当接口实现类初始化时候」
         *
         *
         * 四 类的使用
         *
         *
         * 五 类的卸载
         *    当一个类的class对象不再被引用的时候，生命周期就结束类，对应方法区中的数据也会被清理
         *    jvm自带的类加载器是不会卸载的，用户自定义的类加载器是可以卸载的
         */

        System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024.0);
        System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024.0);
        System.out.println(Runtime.getRuntime().maxMemory()/1024.0/1024.0);
    }


}
