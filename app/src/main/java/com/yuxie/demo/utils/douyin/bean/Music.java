
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Music {

    @SerializedName("cover_large")
    @Expose
    private CoverLarge coverLarge;
    @SerializedName("duration")
    @Expose
    private Long duration;
    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cover_hd")
    @Expose
    private CoverHd coverHd;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("cover_medium")
    @Expose
    private CoverMedium coverMedium;
    @SerializedName("cover_thumb")
    @Expose
    private CoverThumb coverThumb;
    @SerializedName("play_url")
    @Expose
    private PlayUrl playUrl;

    public CoverLarge getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(CoverLarge coverLarge) {
        this.coverLarge = coverLarge;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CoverHd getCoverHd() {
        return coverHd;
    }

    public void setCoverHd(CoverHd coverHd) {
        this.coverHd = coverHd;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public CoverMedium getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(CoverMedium coverMedium) {
        this.coverMedium = coverMedium;
    }

    public CoverThumb getCoverThumb() {
        return coverThumb;
    }

    public void setCoverThumb(CoverThumb coverThumb) {
        this.coverThumb = coverThumb;
    }

    public PlayUrl getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(PlayUrl playUrl) {
        this.playUrl = playUrl;
    }

}
