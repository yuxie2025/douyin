
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("avatar_medium")
    @Expose
    private AvatarMedium avatarMedium;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("followers_detail")
    @Expose
    private Object followersDetail;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("short_id")
    @Expose
    private String shortId;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("avatar_larger")
    @Expose
    private AvatarLarger avatarLarger;
    @SerializedName("avatar_thumb")
    @Expose
    private AvatarThumb avatarThumb;
    @SerializedName("platform_sync_info")
    @Expose
    private Object platformSyncInfo;
    @SerializedName("geofencing")
    @Expose
    private Object geofencing;
    @SerializedName("policy_version")
    @Expose
    private Object policyVersion;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("type_label")
    @Expose
    private Object typeLabel;

    public AvatarMedium getAvatarMedium() {
        return avatarMedium;
    }

    public void setAvatarMedium(AvatarMedium avatarMedium) {
        this.avatarMedium = avatarMedium;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Object getFollowersDetail() {
        return followersDetail;
    }

    public void setFollowersDetail(Object followersDetail) {
        this.followersDetail = followersDetail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public AvatarLarger getAvatarLarger() {
        return avatarLarger;
    }

    public void setAvatarLarger(AvatarLarger avatarLarger) {
        this.avatarLarger = avatarLarger;
    }

    public AvatarThumb getAvatarThumb() {
        return avatarThumb;
    }

    public void setAvatarThumb(AvatarThumb avatarThumb) {
        this.avatarThumb = avatarThumb;
    }

    public Object getPlatformSyncInfo() {
        return platformSyncInfo;
    }

    public void setPlatformSyncInfo(Object platformSyncInfo) {
        this.platformSyncInfo = platformSyncInfo;
    }

    public Object getGeofencing() {
        return geofencing;
    }

    public void setGeofencing(Object geofencing) {
        this.geofencing = geofencing;
    }

    public Object getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(Object policyVersion) {
        this.policyVersion = policyVersion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Object getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(Object typeLabel) {
        this.typeLabel = typeLabel;
    }

}
