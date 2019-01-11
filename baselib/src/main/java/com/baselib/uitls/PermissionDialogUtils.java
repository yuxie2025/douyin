package com.baselib.uitls;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by Lankun on 2018/12/27
 */
@SuppressWarnings("unused")
public class PermissionDialogUtils {

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    private static void showAlertDialog(Activity activity, @Nullable String title, @Nullable String message,
                                        @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                        @NonNull String positiveText,
                                        @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                        @NonNull String negativeText) {
        showAlertDialog(activity, title, message, onPositiveButtonClickListener, positiveText, onNegativeButtonClickListener, negativeText, false);
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     * @param isCancal                      - 点击范围外和back键是否取消
     */
    private static void showAlertDialog(Activity activity, @Nullable String title, @Nullable String message,
                                        @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                        @NonNull String positiveText,
                                        @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                        @NonNull String negativeText, boolean isCancal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancal);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    public static void mustSetting(Activity activity, String message) {
        if (TextUtils.isEmpty(message)) {
            message = "应用需要定位权限和存储权限,是否去设置";
        }
        PermissionDialogUtils.showAlertDialog(activity, null, message, (DialogInterface dialog, int which) -> CommonUtils.toSelfSetting(), "确定", (DialogInterface dialog, int which) -> {
            activity.finish();
            System.exit(0);
            Process.killProcess(Process.myPid());
        }, "取消", false);
    }

    /**
     * 建议权限设置
     *
     * @param message 提示内容
     */
    public static void suggestSetting(Activity activity, String message) {
        if (TextUtils.isEmpty(message)) {
            message = "应用需要定位权限和存储权限,是否去设置";
        }
        PermissionDialogUtils.showAlertDialog(activity, null, message, (dialog, which) -> CommonUtils.toSelfSetting(), "确定", (dialog, which) -> {
        }, "取消", false);
    }

}
