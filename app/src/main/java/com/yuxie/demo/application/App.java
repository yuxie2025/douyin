package com.yuxie.demo.application;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.yuxie.baselib.base.BaseApp;
import com.yuxie.demo.R;

/**
 * Created by Administrator on 2017/7/13.
 */

public class App extends BaseApp {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initData();
    }

    private void initData() {
        //腾讯Bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id), false);
    }

    public static Context getContext() {
        return mContext;
    }

}
