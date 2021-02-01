package top.lothar.base.copy;

import java.io.Serializable;

/**
 * <h1></h1>
 *
 * @author LuTong.Zhao
 * @Date 2021/2/1 14:15
 */
public class Email {

    private String title;

    private String content;

    public Email(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
