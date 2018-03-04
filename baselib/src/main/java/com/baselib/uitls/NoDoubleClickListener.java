package com.baselib.uitls;

import java.util.Calendar;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 防双击监听
 */
public abstract class NoDoubleClickListener implements OnClickListener {

    /**
     * 最近间隔时间
     */
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    /**
     * 上次点击时间
     */
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME || currentTime - lastClickTime < 0) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
