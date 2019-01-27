package com.yuxie.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lankun on 2019/01/21
 */
@Entity
public class UrlLibraryBean {

    @Id
    private Long id;

    /**
     * 1是新闻,2是视频
     */
    private String type;

    private String url;

    private boolean isUse;

    @Generated(hash = 267917226)
    public UrlLibraryBean(Long id, String type, String url, boolean isUse) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.isUse = isUse;
    }

    @Generated(hash = 2060225115)
    public UrlLibraryBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getIsUse() {
        return this.isUse;
    }

    public void setIsUse(boolean isUse) {
        this.isUse = isUse;
    }

   
}
