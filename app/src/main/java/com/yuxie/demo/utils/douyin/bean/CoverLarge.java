
package com.yuxie.demo.utils.douyin.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverLarge {

    @SerializedName("url_list")
    @Expose
    private List<String> urlList = null;
    @SerializedName("uri")
    @Expose
    private String uri;

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
