package com.baselib.daynightmodeutils.grant;

import android.Manifest;

/**
 * Created by Administrator on 2016/12/22.
 * 6.0权限申请帮助类
 */
public class PermissionHelper {

    /**
     * sd卡读写
     */
    public static String[] file = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 使用录音
     */
    public static String[] voice = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 百度定位
     */
    public static String[] baidu = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * ShareSDK
     */
    public static String[] shareSDK = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    /**
     * 使用相册功能
     */
    public static String[] album = {Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 使用相机功能
     */
    public static String[] camera = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 录像
     */
    public static String[] voideo = { Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 使用微信支付、支付宝支付所需的权限
     */
    public static String[] pay = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 打电话
     */
    public static String[] call_phone = {Manifest.permission.CALL_PHONE};

    /**
     * 获取通讯录
     */
    public static String[] addressBook = {Manifest.permission.READ_CONTACTS};
}
