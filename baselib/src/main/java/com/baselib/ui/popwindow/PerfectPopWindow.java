package com.baselib.ui.popwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.baselib.R;
import com.baselib.takephoto.utils.SelectPhotoActivity;


/**
 * 完善信息PopWindow
 * Created by llk on 2017/9/7.
 */

public class PerfectPopWindow implements View.OnClickListener {
    private Activity m_activity;
    private PopupWindow m_share_pop;

    /**
     * 选择了相机
     */
    public static final String CAMERA = "CAMERA";
    /**
     * 选择了相册
     */
    public static final String PHOTO = "PHOTO";

    private Class<?> mCls;

    private String mItem;

    public PerfectPopWindow(Activity context, Class<?> cls) {
        m_activity = context;
        mCls = cls;
    }

    /**
     * 显示popupWindow
     */
    public void showPopupWindow() {
        m_share_pop = new PopupWindow(m_activity);
        @SuppressLint("InflateParams")
        View view = m_activity.getLayoutInflater().inflate(R.layout.popwindow_perfect, null);
        RelativeLayout rl_no = view.findViewById(R.id.rl_no);
        RelativeLayout rl_no1 = view.findViewById(R.id.rl_no1);
        RelativeLayout rl_no2 = view.findViewById(R.id.rl_no2);
        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        rl_no.setOnClickListener(this);
        rl_no1.setOnClickListener(this);
        rl_no2.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

        view.setFocusable(true); // 这个很重要
        view.setFocusableInTouchMode(true);
        m_share_pop.setFocusable(true);
        m_share_pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        m_share_pop.setBackgroundDrawable(ContextCompat.getDrawable(m_activity, 0));
        m_share_pop.setContentView(view);
        m_share_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 重写onKeyListener
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (m_share_pop != null) {
                    m_share_pop.dismiss();
                    m_share_pop = null;
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.rl_no1) {
            SelectPhotoActivity.start(m_activity, mCls, SelectPhotoActivity.CAMERA);
        }
        if (view.getId() == R.id.rl_no2) {
            SelectPhotoActivity.start(m_activity, mCls, SelectPhotoActivity.PHOTO);
        }
        if (m_share_pop != null) {
            m_share_pop.dismiss();
            m_share_pop = null;
        }
    }
}


