package com.yuxie.baselib.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.yuxie.baselib.utils.X5InitUtils;

import java.util.HashMap;

public class BaseApp extends Application {

    private static BaseApp instance = null;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);

        //初始化X5浏览器
        X5InitUtils.init(this);
    }
}
