package top.lothar.jvm.classloader;

import java.io.*;

/**
 * <h1>自定义类加载器</h1>
 *
 * @author LuTong.Zhao
 * @Date 2020/12/17 10:27
 */
public class TestClassLoader extends ClassLoader {

    // 自定义的class扫描路径
    private String classPath;

    public TestClassLoader(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 1.JVM在加载一个class时会先调用classloader的loadClassInternal方法
     * 2.loadClassInternal方法就是调用了loadClass
     *   2.1 先查看这个类是否已经被自己加载了 / 加载直接返回
     *   2.2 没有加载， 如果有父类加载器，先委派给父类加载器来加载 parent.loadClass(name, false);
     *   2.3 如果父类加载器为null，说明ExtClassLoader也没有找到目标类，则调用BootstrapClassLoader来查找  findBootstrapClassOrNull(name);
     *   2.4 如果都没有找到，调用findClass方法，尝试自己加载这个类(也就是我们这里重写的findClass)
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    // 覆写ClassLoader的findClass方法   findClass方法解析class字节流，并实例化class对象
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // getDate方法会根据自定义的路径扫描class，并返回class的字节
        byte[] classData = getDate(name);
        System.out.println(classData.toString());
        if (classData == null) {
            System.out.println("ClassNotFoundException");
            throw new ClassNotFoundException();
        } else {
            // 生成class实例
            return defineClass(name, classData, 0, classData.length);
        }
    }

    /**
     * 指定路径加载二进制字节流的过程 / 比如安全领域可以对字节码加密或OSGi、代码热部署
     * @param name
     * @return
     */
    private byte[] getDate(String name) {
        // 拼接目标class文件路径
        String path = classPath + File.separatorChar + name.replace('.', File.separatorChar) + ".class";
        System.out.println(path);
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0 ,num);
            }
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}