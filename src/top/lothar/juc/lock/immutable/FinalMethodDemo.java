package top.lothar.juc.lock.immutable;

/**
 * 描述：     final修饰方法  构造方法不允许final修饰
 *            修饰的方法不能被重写 有同样名字的方法也不能@Override
 */
public class FinalMethodDemo {

    public void drink() {

    }

    public final void eat() {

    }

    public static void sleep() {

    }
}

class SubClass extends FinalMethodDemo {


    @Override
    public void drink() {
        super.drink();
        //可以调用
        eat();
    }
    //不能重写父类中被final修饰的方法
//    public final void eat() {
//
//    }
    //静态的方法不能被重写
    public static void sleep() {

    }
}