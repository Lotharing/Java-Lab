package top.lothar.juc.threadlocal;

/**
 * 描述：     演示ThreadLocal用法2：避免传递参数的麻烦
 * （其实参数层层传递也可以，就是耦合高嘛）
 */
public class ThreadLocalNormalUsage06 {

    public static void main(String[] args) {
        new Service1().process("LT");
        new Service1().process("LW");
    }
}

/**
 * 比做过滤器场景 / 从token中拿到用户信息
 */
class Service1 {
    //读取到用户信息 设置到ThreadLocal中国呢
    public void process(String name) {
        User user = new User(name);
        UserContextHolder.holder.set(user);
        System.out.println("拦截器从Token中获取的User保存在ThreadLocal中："+UserContextHolder.holder.get().name);
        new Service2().process();
    }
}

class Service2 {

    public void process() {
        //可以直接在ThreadLocal中get出User
        User user = UserContextHolder.holder.get();
        ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println("Service2拿到用户名：" + user.name);
        //UserContextHolder.holder.remove(); //清空会有空指针异常
        User FX = new User("FX");
        //UserContextHolder.holder.set(FX); //可以设置新值
        new Service3().process();
    }
}

class Service3 {

    public void process() {
        //可以直接在ThreadLocal中get出User
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
        UserContextHolder.holder.remove();
    }
}

/**
 * ThreadLocal对象 需要是static
 */
class UserContextHolder {
    /**
     * 没没有创建初始值 在合适的时候（拿到user对象信息时候在set进去）
     */
    public static ThreadLocal<User> holder = new ThreadLocal<>();

}

class User {

    String name;

    public User(String name) {
        this.name = name;
    }
}