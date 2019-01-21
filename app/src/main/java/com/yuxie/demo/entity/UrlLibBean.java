package com.yuxie.demo.entity;

/**
 * Created by Lankun on 2019/01/21
 */
public class UrlLibBean {

//    {"url":"https://www.jianshu.com/p/0160ba4d1b70","content":"","IsSucceed":0,"type":1},{"url":"https://www.jianshu.com/p/01ebb622c642","content":"","IsSucceed":0,"type":1}

    private String url;
    private String IsSucceed;
    private String content;
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsSucceed() {
        return IsSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        IsSucceed = isSucceed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
