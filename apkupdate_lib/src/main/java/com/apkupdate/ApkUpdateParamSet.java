package com.apkupdate;

/**
 * Created by luo on 2018/3/6.
 */

import java.io.Serializable;

public class ApkUpdateParamSet implements Serializable {

    public static final String NAME = "name";

    private String apkUrl;                     //apk线上地址
    private String localPath;                   //本地保存地址
    private String notifyIngMessage;            //进行时通知信息
    private String notifyStartMessage;          //开始时通知信息
    private String notifyEndMessage;            //结束时通知信息
    private String notifyErrorMessage;          //下载失败提示
    private String notifyTitle;                 //通知标题
    private int logoRes;                        //通知图标

    private boolean enableBroadcast;            //接受广播标志
    private boolean enableNotifycation;         //接受通知


    public static String getNAME() {
        return NAME;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getNotifyIngMessage() {
        return notifyIngMessage;
    }

    public void setNotifyIngMessage(String notifyIngMessage) {
        this.notifyIngMessage = notifyIngMessage;
    }

    public String getNotifyStartMessage() {
        return notifyStartMessage;
    }

    public void setNotifyStartMessage(String notifyStartMessage) {
        this.notifyStartMessage = notifyStartMessage;
    }

    public String getNotifyEndMessage() {
        return notifyEndMessage;
    }

    public void setNotifyEndMessage(String notifyEndMessage) {
        this.notifyEndMessage = notifyEndMessage;
    }

    public String getNotifyErrorMessage() {
        return notifyErrorMessage;
    }

    public void setNotifyErrorMessage(String notifyErrorMessage) {
        this.notifyErrorMessage = notifyErrorMessage;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public int getLogoRes() {
        return logoRes;
    }

    public void setLogoRes(int logoRes) {
        this.logoRes = logoRes;
    }

    public boolean isEnableBroadcast() {
        return enableBroadcast;
    }

    public void setEnableBroadcast(boolean enableBroadcast) {
        this.enableBroadcast = enableBroadcast;
    }

    public boolean isEnableNotifycation() {
        return enableNotifycation;
    }

    public void setEnableNotifycation(boolean enableNotifycation) {
        this.enableNotifycation = enableNotifycation;
    }
}
