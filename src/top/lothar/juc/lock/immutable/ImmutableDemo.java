package top.lothar.juc.lock.immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述：     一个属性是对象，但是整体不可变，其他类无法修改set里面的数据
 */
public class ImmutableDemo {
    //private 外部不能访问 内部方法都是读取 添加只在构造方法中做一次 赋值后就不能被更改了
    private final Set<String> students = new HashSet<>();

    public ImmutableDemo() {
        students.add("李小美");
        students.add("王壮");
        students.add("徐福记");
    }

    public boolean isStudent(String name) {
        return students.contains(name);
    }
}
