package com.yuxie.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by luo on 2018/3/1.
 */

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String pwd;
    private String city;
    @Generated(hash = 294759309)
    public User(Long id, String name, String pwd, String city) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.city = city;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}