package top.lothar.proxy;

/**
 * <h1>JDK 静态代理分析</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/8 10:33
 */
public class StaticProxyDemonstration implements Subject {

    private RealSubject realSubject;

    public StaticProxyDemonstration(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public String sayHello(String name) {
        System.out.println("sayHello - before");
        return realSubject.sayHello("Lutong");
    }

    @Override
    public String sayGoodBye() {
        System.out.println("sayGoodBye - before");
        return realSubject.sayGoodBye();
    }
}
