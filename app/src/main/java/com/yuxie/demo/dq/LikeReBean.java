package com.yuxie.demo.dq;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LikeReBean {

    @Id
    private Long id;
    private String voideoId;
    private String token;
    @Generated(hash = 512915081)
    public LikeReBean(Long id, String voideoId, String token) {
        this.id = id;
        this.voideoId = voideoId;
        this.token = token;
    }
    @Generated(hash = 79761192)
    public LikeReBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVoideoId() {
        return this.voideoId;
    }
    public void setVoideoId(String voideoId) {
        this.voideoId = voideoId;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }



}
