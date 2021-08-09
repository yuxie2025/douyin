
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChaList {

    @SerializedName("user_count")
    @Expose
    private Long userCount;
    @SerializedName("is_commerce")
    @Expose
    private Boolean isCommerce;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("cha_name")
    @Expose
    private String chaName;
    @SerializedName("connect_music")
    @Expose
    private Object connectMusic;
    @SerializedName("type")
    @Expose
    private Long type;
    @SerializedName("cover_item")
    @Expose
    private CoverItem coverItem;
    @SerializedName("view_count")
    @Expose
    private Long viewCount;
    @SerializedName("hash_tag_profile")
    @Expose
    private String hashTagProfile;
    @SerializedName("cid")
    @Expose
    private String cid;

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Boolean getIsCommerce() {
        return isCommerce;
    }

    public void setIsCommerce(Boolean isCommerce) {
        this.isCommerce = isCommerce;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChaName() {
        return chaName;
    }

    public void setChaName(String chaName) {
        this.chaName = chaName;
    }

    public Object getConnectMusic() {
        return connectMusic;
    }

    public void setConnectMusic(Object connectMusic) {
        this.connectMusic = connectMusic;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public CoverItem getCoverItem() {
        return coverItem;
    }

    public void setCoverItem(CoverItem coverItem) {
        this.coverItem = coverItem;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getHashTagProfile() {
        return hashTagProfile;
    }

    public void setHashTagProfile(String hashTagProfile) {
        this.hashTagProfile = hashTagProfile;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
