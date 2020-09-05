package top.lothar.juc.lock.immutable;

/**
 * 描述：     测试final能否被修改
 *
 * 修饰变量 如果变量是对象 -  引用指向不可更改 person = new person() ，值可以更改
 */
public class TestFinal {

    String test;

    public static void main(String[] args) {
        final Person person = new Person();
        //final修饰创建的对象 引用不可更改 person = new person() ，值可以更改
        person.testFinal.test = "13g";
        System.out.println(person.testFinal.test);
        //不可在更改
        person.testFinal.test = "15g";
        System.out.println(person.testFinal.test);

//        String abc = "abc";
//        System.out.println(abc.substring(1, 2));
//        System.out.println(abc);
//        person.bag = "book";
//        person = new Person();
//        int age = person.age;
//        String name = person.name;

    }
}
