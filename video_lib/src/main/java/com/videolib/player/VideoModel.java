package com.videolib.player;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author llk
 * @time 2018/5/21 18:52
 * @class describe
 */
public class VideoModel implements Serializable {

    /**
     * 视频title
     */
    private String title;

    /**
     * 视频url
     */
    private String url;

    /**
     * 封面
     */
    private String thumbImage;

    /**
     * 标记
     */
    private String Tag;

    /**
     * 位置
     */
    private int position;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
