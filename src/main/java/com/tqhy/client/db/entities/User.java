package com.tqhy.client.db.entities;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author Yiheng
 * @create 2018/7/23
 * @since 1.0.0
 */
@Entity
public class User {
    @Id
    private long id;
    /**
     * 预留,与后台数据库中id对应
     */
    private String key;
    private String name;
    private String pwd;

    public User() {
    }

    public User(long id, String key, String name, String pwd) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.pwd = pwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
