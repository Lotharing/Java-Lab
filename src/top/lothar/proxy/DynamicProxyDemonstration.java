package top.lothar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * <h1>JDK 动态代理</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/8 10:20
 */
public class DynamicProxyDemonstration {

    public static void main(String[] args) {

        //代理的真实对象
        Subject realSubject = new RealSubject();

        /**
         * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
         * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
         * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
         */
        InvocationHandler handler = new InvocationHandlerImpl(realSubject);

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();
        /**
         * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         * 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
         */
        Subject subject = (Subject) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型：" + subject.getClass().getName());

        String hello = subject.sayHello("Lutong");
        System.out.println(hello);

        String goodbye = subject.sayGoodBye();
        System.out.println(goodbye);

        /**静态代理**/

        System.out.println("JDK - 静态代理");

        /**
         * 通过新实现接口的类（代理类）中 注入（真实类）对象，在引用真实类方法之前/之后可以进行操作处理
         */
        RealSubject object = new RealSubject();
        StaticProxyDemonstration staticProxyDemonstration = new StaticProxyDemonstration(object);
        System.out.println(staticProxyDemonstration.sayHello("null"));
        System.out.println(staticProxyDemonstration.sayGoodBye());
    }
}
