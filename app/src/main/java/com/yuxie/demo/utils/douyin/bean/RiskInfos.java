
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiskInfos {

    @SerializedName("type")
    @Expose
    private Long type;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("warn")
    @Expose
    private Boolean warn;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getWarn() {
        return warn;
    }

    public void setWarn(Boolean warn) {
        this.warn = warn;
    }

}
