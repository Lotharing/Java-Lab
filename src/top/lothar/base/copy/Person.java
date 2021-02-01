package top.lothar.base.copy;

/**
 * <h1></h1>
 *    其实出现问题的关键就在于clone()方法上，我们知道该clone()方法是使用Object类的clone()方法，
 *    但是该方法存在一个缺陷，它并不会将对象的所有属性全部拷贝过来，而是有选择性的拷贝，基本规则如下：
 *       1、 基本类型
 *          如果变量是基本很类型，则拷贝其值，比如int、float等。
 *       2、 对象
 *          如果变量是一个实例对象，则拷贝其地址引用，也就是说此时新对象与原来对象是公用该实例变量。
 *       3、 String字符串
 *          若变量为String字符串，则拷贝其地址引用。但是在修改时，它会从字符串池中重新生成一个新的字符串，原有对象保持不变。
 * @author LuTong.Zhao
 * @Date 2021/2/1 14:14
 */
public class Person implements Cloneable{

    /** 姓名 **/
    private String name;

    /** 电子邮件 **/
    private Email email;

    public Person(String name, Email email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * 克隆一个Person
     * @return
     */
    @Override
    protected Person clone() {
        Person person = null;
        try {
            person = (Person) super.clone();
            // 避免是实例的引用地址 ， 直接在new一个新返回，每一个对象都是clone的新对象
            person.setEmail(new Email(person.getEmail().getTitle(),person.getEmail().getContent()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }


    public static void main(String[] args) {
        //写封邮件
        Email email = new Email("请参加会议","请与今天12:30到二会议室参加会议...");

        Person person1 =  new Person("张三",email);

        Person person2 =  person1.clone();
        person2.setName("李四");

        Person person3 =  person1.clone();
        person3.setName("王五");


        person1.getEmail().setContent("请与今天12:00到二会议室参加会议...");

        System.out.println(person1.getName() + "的邮件内容是：" + person1.getEmail().getContent());
        System.out.println(person2.getName() + "的邮件内容是：" + person2.getEmail().getContent());
        System.out.println(person3.getName() + "的邮件内容是：" + person3.getEmail().getContent());
    }
}
