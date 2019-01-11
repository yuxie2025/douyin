package com.baselib.ui.popwindow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import com.baselib.R;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;


/**
 * 完善信息PopWindow
 * Created by llk on 2017/9/7.
 */

public class CommonPopWindow1 extends Dialog {
    private Activity m_activity;
    private PopupWindow m_share_pop;

    // 动画时长
    private final static int mAnimationDuration = 400;

    private boolean mIsAnimating = false;

    View mContentView;

    public CommonPopWindow1(Activity context) {
        super(context, com.qmuiteam.qmui.R.style.QMUI_BottomSheet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        // 在底部，宽度撑满
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int screenWidth = QMUIDisplayHelper.getScreenWidth(getContext());
        int screenHeight = QMUIDisplayHelper.getScreenHeight(getContext());
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(mContentView);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        super.setContentView(view, params);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void setContentView(@NonNull View view) {
        mContentView = view;
        super.setContentView(view);
    }


//    public CommonPopWindow1(Activity context, @LayoutRes int rId) {
//        m_activity = context;
//
//        if (rId == 0) {
//            throw new IllegalArgumentException("布局资源文件不能为空!");
//        }
//
//        view = m_activity.getLayoutInflater().inflate(rId, null);
//    }

    /**
     * 显示popupWindow
     *
     * @param
     */
    public void showPopupWindow() {
        m_share_pop = new PopupWindow(m_activity);

        try {
            mContentView.findViewById(R.id.rl_no).setOnClickListener(v -> {
                dismiss();
            });

            mContentView.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                dismiss();
            });
        } catch (Exception e) {
        }

        mContentView.setFocusable(true); // 这个很重要
        mContentView.setFocusableInTouchMode(true);
        m_share_pop.setFocusable(true);
        m_share_pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setBackgroundDrawable(new ColorDrawable());
        m_share_pop.setContentView(mContentView);
        m_share_pop.setClippingEnabled(false);
        int y = 0;
        boolean isShow = CommonUtils.isNavigationBarShow(m_activity);
        if (isShow) {
            y = BarUtils.getNavBarHeight();
        }
        m_share_pop.showAtLocation(mContentView, Gravity.BOTTOM, 0, y);
        // 重写onKeyListener
        mContentView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (m_share_pop != null) {
                    dismiss();
                    return true;
                }
            }
            return false;
        });
        animateUp();
    }

    public View getView() {
        return mContentView;
    }

    @Override
    public void show() {
        super.show();
        animateUp();
    }

    @Override
    public void dismiss() {
        LogUtils.i("dismiss()----");
        if (mIsAnimating) {
            return;
        }
        animateDown();
        new Handler().postDelayed(() -> {
            super.dismiss();
        }, mAnimationDuration);
    }

    /**
     * BottomSheet升起动画
     */
    private void animateUp() {
        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        mContentView.startAnimation(set);
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {

        LogUtils.i("animateDown----");

        if (mContentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(mAnimationDuration);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mContentView.startAnimation(set);
    }


    /**
     * 生成列表类型的 {@link QMUIBottomSheet} 对话框。
     */
    public static class BottomListSheetBuilder {

        private Context mContext;

        private QMUIBottomSheet mDialog;

        public BottomListSheetBuilder(Context context) {
            this(context, false);
        }

        /**
         */
        public BottomListSheetBuilder(Context context, boolean needRightMark) {
            mContext = context;
        }

        public QMUIBottomSheet build() {
            mDialog = new QMUIBottomSheet(mContext);
            View contentView = buildViews();
            mDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return mDialog;
        }

        protected int getContentViewLayoutId() {
            return R.layout.qmui_bottom_sheet_list;
        }

        private View buildViews() {
            View wrapperView = View.inflate(mContext, getContentViewLayoutId(), null);
            return wrapperView;
        }
    }
}


