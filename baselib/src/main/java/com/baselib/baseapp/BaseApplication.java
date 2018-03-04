package com.baselib.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Debug;
import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDex;

import com.baselib.R;
import com.baselib.commonutils.LogUtils;

import com.baselib.uitls.CommonUtils;
import com.baselib.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by liuhuaqian on 2017/9/8.
 */

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    public static AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
        appManager = AppManager.getAppManager();

        //初始化Utils
        Utils.init(this);

        if (CommonUtils.isApkDebugable()) {
            LogUtils.logInit(true);//日志
        }
        //腾讯Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id), false);

    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

