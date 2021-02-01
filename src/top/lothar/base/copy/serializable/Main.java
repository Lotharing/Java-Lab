package top.lothar.base.copy.serializable;

import top.lothar.base.copy.serializable.Email;
import top.lothar.base.copy.Person;
import top.lothar.base.copy.utils.CloneUtils;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/2/1 15:02
 */
public class Main {

    public static void main(String[] args) {
        //写封邮件
        Email email = new Email("请参加会议","请与今天12:30到二会议室参加会议...");

        User user1 = new User("LT","handsome",email);

        User user2 =  CloneUtils.clone(user1);
        user2.setName("GT");
        user2.getEmail().setContent("请与今天12:00到二会议室参加会议...");

        System.out.println(user1.getName() + "的邮件内容是：" + user1.getEmail().getContent());
        System.out.println(user2.getName() + "的邮件内容是：" + user2.getEmail().getContent());
    }


}


