package com.yuxie.demo.application;

import android.content.Context;

import com.baselib.baseapp.BaseApplication;
import com.hjq.permissions.XXPermissions;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuxie.demo.R;

/**
 * Created by Administrator on 2017/7/13.
 */

public class App extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initData();

        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true);
    }

    private void initData() {
        //腾讯Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id), false);
    }

    public static Context getContext() {
        return mContext;
    }

}
