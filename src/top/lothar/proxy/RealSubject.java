package top.lothar.proxy;

/**
 * <h1>需要代理的真实对象</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/8 10:08
 */
public class RealSubject implements Subject{

    @Override
    public String sayHello(String name) {
        return "Hello ," +name;
    }

    @Override
    public String sayGoodBye() {
        return "good bye ~";
    }

}
