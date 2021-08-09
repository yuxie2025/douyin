
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("aweme_id")
    @Expose
    private String awemeId;
    @SerializedName("comment_count")
    @Expose
    private Long commentCount;
    @SerializedName("digg_count")
    @Expose
    private Long diggCount;

    public String getAwemeId() {
        return awemeId;
    }

    public void setAwemeId(String awemeId) {
        this.awemeId = awemeId;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(Long diggCount) {
        this.diggCount = diggCount;
    }

}
