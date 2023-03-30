package com.yuxie.baselib.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.StringUtils;

/**
 * 通用工具栏
 */
public class CommonUtils {

    public static Bitmap string2Bitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 上次点击时间
     */
    private static long lastClickTime = 0;

    /**
     * 检测是连续点击,连续点击事件间隔太小不处理
     *
     * @return 结果
     */
    public static boolean isDoubleClick() {
        int MIN_CLICK_DELAY_TIME = 1000;
        return isDoubleClick(MIN_CLICK_DELAY_TIME);
    }


    /**
     * @param delayTime 间隔时间(自定义间隔时间)
     * @return 结果
     */
    public static boolean isDoubleClick(long delayTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > delayTime || currentTime - lastClickTime < 0) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 抽取URL
     *
     * @param rawInfo rawInfo
     * @return url
     */
    public static String extractUrl(String rawInfo) {
        if (StringUtils.isEmpty(rawInfo)) {
            return "";
        }
        for (String string : rawInfo.split(" ")) {
            if (string.startsWith("http")) {
                return string;
            }
        }
        return "";
    }
}
