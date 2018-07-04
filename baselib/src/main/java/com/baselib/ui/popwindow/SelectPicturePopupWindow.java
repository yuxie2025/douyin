package com.baselib.ui.popwindow;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.baselib.R;

/**
 * 作者: llk on 2017/9/10.
 * 拍照PopupWindow
 */

public class SelectPicturePopupWindow extends PopupWindow implements View.OnClickListener {

    private Button takePhotoBtn, pickPictureBtn, cancelBtn;
    private View mMenuView;
    private PopupWindow popupWindow;
    private OnSelectedListener mOnSelectedListener;

    public SelectPicturePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_picture_selector, null);
        takePhotoBtn = (Button) mMenuView.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = (Button) mMenuView.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = (Button) mMenuView.findViewById(R.id.picture_selector_cancel_btn);
        // 设置按钮监听
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    /**
     * 把一个View控件添加到PopupWindow上并且显示
     *
     * @param activity
     */
    public void showPopupWindow(Activity activity) {
        popupWindow = new PopupWindow(mMenuView,    // 添加到popupWindow
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // ☆ 注意： 必须要设置背景，播放动画有一个前提 就是窗体必须有背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);   // 设置窗口显示的动画效果
        popupWindow.setFocusable(false);                                        // 点击其他地方隐藏键盘 popupWindow
        popupWindow.update();
    }

    /**
     * 移除PopupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.picture_selector_take_photo_btn) {
            if (null != mOnSelectedListener) {
                mOnSelectedListener.OnSelected(v, 0);
            }

        } else if (i == R.id.picture_selector_pick_picture_btn) {
            if (null != mOnSelectedListener) {
                mOnSelectedListener.OnSelected(v, 1);
            }

        } else if (i == R.id.picture_selector_cancel_btn) {
            if (null != mOnSelectedListener) {
                mOnSelectedListener.OnSelected(v, 2);
            }

        }
    }

    /**
     * 设置选择监听
     *
     * @param l
     */
    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * 选择监听接口
     */
    public interface OnSelectedListener {
        void OnSelected(View v, int position);
    }

}