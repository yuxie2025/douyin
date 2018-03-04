package com.baselib.commonutils;

import android.content.Context;

/**
 * 作者: liuhuaqian on 2017/9/23.
 * 测量工具类
 */

public class MeasureUtil {
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
