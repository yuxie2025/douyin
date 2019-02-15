package com.yuxie.demo.base;

import android.support.v4.content.ContextCompat;

import com.baselib.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by Lankun on 2019/01/24
 */
public abstract class MyBaseActivity extends BaseActivity {

    @Override
    protected void setStatusBarColor() {
        int mStatusBarColor = ContextCompat.getColor(mContext, com.baselib.R.color.status_bar_color);
        // 获得状态栏高度
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        StatusBarUtil.setColorForSwipeBack(this, mStatusBarColor, 0);
    }

}
