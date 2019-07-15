package com.apkupdate.widget;


import java.io.Serializable;

/**
 * created by Simon on 2017/9/18 17:55
 * description:
 */

public class ApkVersionModel implements Serializable {

    /*
        "id": 1,
            "appVersion": "版本号",
            "releaseTime": "上线时间",
            "releaseStatus": "上线状态（1、上线、0、未上线）",
            "type": "设备类型 0 安卓，1 ios",
            "url": "安装包地址",
            "forceUpgrape":"强制升级 Y|N",
            "remarks","升级说明信息"
     */
    public String appVersion;
    public String releaseTime;
    public String releaseStatus;
    public int id;
    public String type;
    public String url;
    public String forceUpgrade;
    public String remarks;
    public String msg;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForceUpgrape() {
        return forceUpgrade;
    }

    public void setForceUpgrape(String forceUpgrape) {
        this.forceUpgrade = forceUpgrape;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
