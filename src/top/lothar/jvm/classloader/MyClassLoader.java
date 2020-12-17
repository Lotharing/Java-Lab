package top.lothar.jvm.classloader;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/17 14:01
 */
public class MyClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        String classpath = "/Users/zhaolutong/develop";
        // 自定义class类路径
        TestClassLoader testClassLoader = new TestClassLoader(classpath);
        // 通过自定义类加载器加载
        Class<?> user = testClassLoader.loadClass("ClassLoaderTest");
        // 这里的打印应该是我们自定义的类加载器：TestClassLoader
        System.out.println("自定义加载器 --- >"+user.getClassLoader());
    }
}
