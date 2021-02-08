package top.lothar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <h1>处理器实现类</h1>
 * 每次生成动态代理类对象时都需要指定一个实现了 InvocationHandler 接口的调用处理器对象
 *
 * @author LuTong.Zhao
 * @Date 2020/12/8 10:09
 */
public class InvocationHandlerImpl implements InvocationHandler {

    /**
     * 需要代理的真实对象
     */
    private Object subject;

    public InvocationHandlerImpl(Object subject) {
        this.subject = subject;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("在代理真实对象前做的操作...");

        System.out.println("Method:" + method);

        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object returnValue = method.invoke(subject, args);

        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("在调用之后，我要干点啥呢？");

        return returnValue;
    }
}
