package com.baselib.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.baseapp.BaseApplication;
import com.baselib.uitls.CommonUtils;

import java.lang.ref.WeakReference;

/**
 * 弹窗浮动加载进度条
 * 作者: llk on 2017/9/8.
 */

public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     *
     * @param activity   上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static synchronized Dialog showDialogForLoading(Activity activity, String msg, boolean cancelable) {

        if (CommonUtils.isDoubleClick(2000)) {
            return null;
        }

        WeakReference<Activity> reference = new WeakReference<>(activity);

        Activity context = reference.get();

        if (context == null || context.isFinishing()) {
            return null;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();

        //超时关闭,防止一直显示问题
//        new Handler().postDelayed(LoadingDialog::cancelDialogForLoading, 10000);

        return mLoadingDialog;
    }

    public static synchronized Dialog showDialogForLoading(Activity activity) {
        return showDialogForLoading(activity, BaseApplication.getAppContext().getString(R.string.loading), true);
    }

    /**
     * 关闭加载对话框
     */
    public static synchronized void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
    }
}
