
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("now")
    @Expose
    private Long now;
    @SerializedName("logid")
    @Expose
    private String logid;

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

}
