package top.lothar.proxy;

/**
 * <h1>需要动态代理的接口</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/8 10:06
 */
public interface Subject {

    /**
     * 你好
     * @param name
     * @return
     */
    String sayHello(String name);

    /**
     * 再见
     * @return
     */
    String sayGoodBye();

}
