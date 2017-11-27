package io.ride.model;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-26
 * Time: 下午6:23
 */
public class Info {
    private Integer id;
    private String username;
    private String content;
    private Date createTime;

    public Info() {
    }

    public Info(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
