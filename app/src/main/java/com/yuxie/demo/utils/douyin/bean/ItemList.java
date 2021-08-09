
package com.yuxie.demo.utils.douyin.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemList {

    @SerializedName("video")
    @Expose
    private Video video;
    @SerializedName("category")
    @Expose
    private Long category;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @SerializedName("is_preview")
    @Expose
    private Long isPreview;
    @SerializedName("is_live_replay")
    @Expose
    private Boolean isLiveReplay;
    @SerializedName("create_time")
    @Expose
    private Long createTime;
    @SerializedName("aweme_type")
    @Expose
    private Long awemeType;
    @SerializedName("author_user_id")
    @Expose
    private Long authorUserId;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("statistics")
    @Expose
    private Statistics statistics;
    @SerializedName("share_info")
    @Expose
    private ShareInfo shareInfo;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("video_text")
    @Expose
    private Object videoText;
    @SerializedName("long_video")
    @Expose
    private Object longVideo;
    @SerializedName("aweme_id")
    @Expose
    private String awemeId;
    @SerializedName("music")
    @Expose
    private Music music;
    @SerializedName("text_extra")
    @Expose
    private List<TextExtra> textExtra = null;
    @SerializedName("duration")
    @Expose
    private Long duration;
    @SerializedName("uniqid_position")
    @Expose
    private Object uniqidPosition;
    @SerializedName("cha_list")
    @Expose
    private List<ChaList> chaList = null;
    @SerializedName("image_infos")
    @Expose
    private Object imageInfos;
    @SerializedName("promotions")
    @Expose
    private Object promotions;
    @SerializedName("forward_id")
    @Expose
    private String forwardId;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("video_labels")
    @Expose
    private Object videoLabels;
    @SerializedName("geofencing")
    @Expose
    private Object geofencing;
    @SerializedName("label_top_text")
    @Expose
    private Object labelTopText;
    @SerializedName("group_id")
    @Expose
    private Long groupId;
    @SerializedName("risk_infos")
    @Expose
    private RiskInfos riskInfos;
    @SerializedName("comment_list")
    @Expose
    private Object commentList;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Long getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Long isPreview) {
        this.isPreview = isPreview;
    }

    public Boolean getIsLiveReplay() {
        return isLiveReplay;
    }

    public void setIsLiveReplay(Boolean isLiveReplay) {
        this.isLiveReplay = isLiveReplay;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getAwemeType() {
        return awemeType;
    }

    public void setAwemeType(Long awemeType) {
        this.awemeType = awemeType;
    }

    public Long getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(Long authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public ShareInfo getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfo shareInfo) {
        this.shareInfo = shareInfo;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getVideoText() {
        return videoText;
    }

    public void setVideoText(Object videoText) {
        this.videoText = videoText;
    }

    public Object getLongVideo() {
        return longVideo;
    }

    public void setLongVideo(Object longVideo) {
        this.longVideo = longVideo;
    }

    public String getAwemeId() {
        return awemeId;
    }

    public void setAwemeId(String awemeId) {
        this.awemeId = awemeId;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public List<TextExtra> getTextExtra() {
        return textExtra;
    }

    public void setTextExtra(List<TextExtra> textExtra) {
        this.textExtra = textExtra;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Object getUniqidPosition() {
        return uniqidPosition;
    }

    public void setUniqidPosition(Object uniqidPosition) {
        this.uniqidPosition = uniqidPosition;
    }

    public List<ChaList> getChaList() {
        return chaList;
    }

    public void setChaList(List<ChaList> chaList) {
        this.chaList = chaList;
    }

    public Object getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(Object imageInfos) {
        this.imageInfos = imageInfos;
    }

    public Object getPromotions() {
        return promotions;
    }

    public void setPromotions(Object promotions) {
        this.promotions = promotions;
    }

    public String getForwardId() {
        return forwardId;
    }

    public void setForwardId(String forwardId) {
        this.forwardId = forwardId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getVideoLabels() {
        return videoLabels;
    }

    public void setVideoLabels(Object videoLabels) {
        this.videoLabels = videoLabels;
    }

    public Object getGeofencing() {
        return geofencing;
    }

    public void setGeofencing(Object geofencing) {
        this.geofencing = geofencing;
    }

    public Object getLabelTopText() {
        return labelTopText;
    }

    public void setLabelTopText(Object labelTopText) {
        this.labelTopText = labelTopText;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public RiskInfos getRiskInfos() {
        return riskInfos;
    }

    public void setRiskInfos(RiskInfos riskInfos) {
        this.riskInfos = riskInfos;
    }

    public Object getCommentList() {
        return commentList;
    }

    public void setCommentList(Object commentList) {
        this.commentList = commentList;
    }

}
