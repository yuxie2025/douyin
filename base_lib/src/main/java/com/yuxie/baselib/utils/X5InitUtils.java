package com.yuxie.baselib.utils;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.yuxie.baselib.base.BaseApp;

import java.util.HashMap;

public class X5InitUtils {

    public static final String TAG = "X5";

    private X5InitUtils() {
    }

    public static void init(Application application) {

        QbSdk.setDownloadWithoutWifi(true);

        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap<String, Object> map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);

        Log.i(TAG, "initX5WebView：---");

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.i(TAG, "===onDownloadFinish:" + i);
            }

            @Override
            public void onInstallFinish(int i) {
                Log.i(TAG, "===onInstallFinish:" + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.i(TAG, "===onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "X5加载内核完成");
            }

            @Override
            public void onViewInitFinished(boolean isX5) {
                Log.i(TAG, "X5加载内核是否成功:" + isX5);
            }
        });
    }
}


