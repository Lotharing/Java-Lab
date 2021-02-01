package top.lothar.base.copy.serializable;

import top.lothar.base.copy.serializable.Email;

import java.io.Serializable;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/2/1 14:59
 */
public class User implements Serializable {

    private String name;

    private String introduce;

    private Email email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public User(String name, String introduce, Email email) {
        this.name = name;
        this.introduce = introduce;
        this.email = email;
    }
}
