package com.baselib.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.view.View;
import android.webkit.WebView;

import com.baselib.R;

import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by llk on 2017/9/8.
 */
@SuppressWarnings("unused")
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    public static AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        appManager = AppManager.getAppManager();

        new Thread(() -> {
            initUtils();
        }).start();

    }

    //初始化工具
    private void initUtils() {
        //初始化Utils
        Utils.init(this);

        //必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
        //第一个参数：应用程序上下文
        //第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
        List<Class<? extends View>> views = new ArrayList<>();
        views.add(BridgeWebView.class);
        BGASwipeBackHelper.init(this, views);

        if (CommonUtils.isApkDebugable()) {
            initLeakCanary();//检测内存泄漏工具
        }

        //初始化X5浏览器
        initX5WebView();

        //腾讯Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id), false);
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }


    /**
     * 检测内存泄漏工具
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if (CommonUtils.isApkDebugable()) {
            LeakCanary.install(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     *
     * @param base 上下文
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 开启x5内核
     */
    private void initX5WebView() {

        // 在调用TBS初始化、创建WebView之前进行如下配置，以开启优化方案
        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //X5内核初始化完成，true使用x5内核，false使用系统内核
                LogUtils.d("x5内核使用：" + b);
            }
        };
        //x5内核初始化
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

}

