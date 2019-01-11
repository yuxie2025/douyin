package com.baselib.commonutils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.baselib.baseapp.BaseApplication;

/**
 * desc:获取版本工具
 * Created by Lankun on 2018/10/29/029
 */
@SuppressWarnings("unused")
public class PackageUtils {
    /**
     * 获取版本名称
     *
     * @return 版本名称
     */
    public static String getVersionName() {
        //获取包管理器
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            //返回版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {
        //获取包管理器
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            //返回版本号
            return (int) packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取App的名称
     *
     * @return 名称
     */
    public static String getAppName() {
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(BaseApplication.getAppContext().getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return BaseApplication.getAppContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}