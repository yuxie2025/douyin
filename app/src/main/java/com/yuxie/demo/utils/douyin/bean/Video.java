
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("is_long_video")
    @Expose
    private Long isLongVideo;
    @SerializedName("cover")
    @Expose
    private Cover cover;
    @SerializedName("width")
    @Expose
    private Long width;
    @SerializedName("dynamic_cover")
    @Expose
    private DynamicCover dynamicCover;
    @SerializedName("bit_rate")
    @Expose
    private Object bitRate;
    @SerializedName("duration")
    @Expose
    private Long duration;
    @SerializedName("play_addr")
    @Expose
    private PlayAddr playAddr;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("origin_cover")
    @Expose
    private OriginCover originCover;
    @SerializedName("ratio")
    @Expose
    private String ratio;
    @SerializedName("has_watermark")
    @Expose
    private Boolean hasWatermark;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Long getIsLongVideo() {
        return isLongVideo;
    }

    public void setIsLongVideo(Long isLongVideo) {
        this.isLongVideo = isLongVideo;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public DynamicCover getDynamicCover() {
        return dynamicCover;
    }

    public void setDynamicCover(DynamicCover dynamicCover) {
        this.dynamicCover = dynamicCover;
    }

    public Object getBitRate() {
        return bitRate;
    }

    public void setBitRate(Object bitRate) {
        this.bitRate = bitRate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public PlayAddr getPlayAddr() {
        return playAddr;
    }

    public void setPlayAddr(PlayAddr playAddr) {
        this.playAddr = playAddr;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public OriginCover getOriginCover() {
        return originCover;
    }

    public void setOriginCover(OriginCover originCover) {
        this.originCover = originCover;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Boolean getHasWatermark() {
        return hasWatermark;
    }

    public void setHasWatermark(Boolean hasWatermark) {
        this.hasWatermark = hasWatermark;
    }

}
