package com.yuxie.baselib.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

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
    }

}
