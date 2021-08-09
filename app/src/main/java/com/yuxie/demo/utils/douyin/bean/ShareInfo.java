
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareInfo {

    @SerializedName("share_weibo_desc")
    @Expose
    private String shareWeiboDesc;
    @SerializedName("share_desc")
    @Expose
    private String shareDesc;
    @SerializedName("share_title")
    @Expose
    private String shareTitle;

    public String getShareWeiboDesc() {
        return shareWeiboDesc;
    }

    public void setShareWeiboDesc(String shareWeiboDesc) {
        this.shareWeiboDesc = shareWeiboDesc;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

}
