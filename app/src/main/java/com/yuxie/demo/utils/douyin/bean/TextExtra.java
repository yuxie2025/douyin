
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextExtra {

    @SerializedName("start")
    @Expose
    private Long start;
    @SerializedName("end")
    @Expose
    private Long end;
    @SerializedName("type")
    @Expose
    private Long type;
    @SerializedName("hashtag_name")
    @Expose
    private String hashtagName;
    @SerializedName("hashtag_id")
    @Expose
    private Long hashtagId;

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getHashtagName() {
        return hashtagName;
    }

    public void setHashtagName(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    public Long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }

}
