package top.lothar.juc.lock.immutable;

/**
 * 描述：     演示final修饰变量
 */
public class FinalVariableDemo {
    //赋值就不能更改哪怕是null  就是你如果Person person; 它有默认值 你在person = null也不行认为是修改了
    private static final Person person = null;
    /**
     * 一,类中的final属性
     *
     * 赋值时机：// final修饰 只能赋值一次
     *
     *
     *          1.等号右边直接赋值 private final int a = 1;
     *
     *
     *          2.构造器赋值    private final int a;
     *                        public FinalVariableDemo(int a){
     *                          this.a = a;
     *                        }
     *
     *          3.代码块赋值    private final int a;
     *
     *                       {
     *                          a = 1;
     *                       }
     *
     *二,类中的static final属性
     *
     *          1.等号右边直接赋值 private static final int a = 1;
     *
     *          2.静态代码块赋值 private final int a;
     *
     *                        static{ a = 1};
     */

    //第三种  方法中final变量的赋值
    void testFinal() {
        //必须赋值
       //final int b = 7;

        //使用之前复制
       final int b;

       b = 5;
       int c = b;
    }

}
