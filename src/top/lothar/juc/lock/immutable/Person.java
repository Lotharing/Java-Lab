package top.lothar.juc.lock.immutable;

/**
 * 描述：     不可变的对象（并发不可变的一种策略），演示其他类无法修改这个对象，public也不行
 */
public class Person {
    //不可变
    final int age = 18;
    //可变
    String alice = new String("Alice");
    //不可变
    final String name = alice;
    //不可变
    final TestFinal testFinal = new TestFinal();

    public static void main(String[] args) {
        Person person = new Person();
        person.alice = "LT"; //就算你修改他 final照样不可变
        System.out.println(person.name);
    }
}

