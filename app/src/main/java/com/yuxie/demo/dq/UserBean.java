package com.yuxie.demo.dq;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lankun on 2018/12/07
 */
@Entity
public class UserBean {
    @Id
    Long id;
    
    String token;

    @Generated(hash = 522838729)
    public UserBean(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    

}
