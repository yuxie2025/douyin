package com.baselib.ui.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.baselib.R;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.BarUtils;


/**
 * 完善信息PopWindow
 * Created by llk on 2017/9/7.
 */

public class CommonPopWindow {
    private Activity m_activity;
    private PopupWindow m_share_pop;

    View view;

    public CommonPopWindow(Activity context, @LayoutRes int rId) {
        m_activity = context;

        if (rId == 0) {
            throw new IllegalArgumentException("布局资源文件不能为空!");
        }

        view = m_activity.getLayoutInflater().inflate(rId, null);
    }

    /**
     * 显示popupWindow
     *
     * @param
     */
    public void showPopupWindow() {
        m_share_pop = new PopupWindow(m_activity);

        try {
            view.findViewById(R.id.rl_no).setOnClickListener(v -> {
                dismiss();
            });

            view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                dismiss();
            });
        } catch (Exception e) {
        }

        view.setFocusable(true); // 这个很重要
        view.setFocusableInTouchMode(true);
        m_share_pop.setFocusable(true);
        m_share_pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setBackgroundDrawable(new ColorDrawable());
        m_share_pop.setContentView(view);
        m_share_pop.setClippingEnabled(false);
        int y = 0;
        boolean isShow = CommonUtils.isNavigationBarShow(m_activity);
        if (isShow) {
            y = BarUtils.getNavBarHeight();
        }
        m_share_pop.showAtLocation(view, Gravity.BOTTOM, 0, y);
        // 重写onKeyListener
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (m_share_pop != null) {
                    dismiss();
                    return true;
                }
            }
            return false;
        });
    }

    public View getView() {
        return view;
    }

    public void dismiss() {
        if (m_share_pop != null) {
            m_share_pop.dismiss();
            m_share_pop = null;
        }
    }
}


