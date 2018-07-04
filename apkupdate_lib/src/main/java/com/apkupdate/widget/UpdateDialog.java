package com.apkupdate.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkupdate.R;


/**
 * Created by luo on 2018/3/6.
 */

public class UpdateDialog extends DialogFragment {
    private static OnClickUpdate mOnClickUpdate;
    private static final String MODEL = "model";
    private static Activity nActivity;

    public static UpdateDialog getDialog(ApkVersionModel apkVersionModel, Activity activity, OnClickUpdate onClickUpdate) {
        nActivity = activity;
        mOnClickUpdate = onClickUpdate;
        final UpdateDialog updateDialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MODEL, apkVersionModel);
        updateDialog.setArguments(bundle);
        return updateDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ApkVersionModel apkVersionModel = (ApkVersionModel) getArguments().getSerializable(MODEL);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View titleView = getActivity().getLayoutInflater().inflate(R.layout.dialog_apk_update_title_view, null);
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_apk_update_view, null);
        RelativeLayout wait = (RelativeLayout) contentView.findViewById(R.id.wait);
        if ("Y".equals(apkVersionModel.getForceUpgrape())) {
            wait.setVisibility(View.INVISIBLE);
        } else {
            wait.setVisibility(View.VISIBLE);
        }
        builder.setCustomTitle(titleView);
        builder.setView(contentView);
        TextView tvUpdateContent = (TextView) contentView.findViewById(R.id.tv_update_content);
        TextView tvVersion = (TextView) titleView.findViewById(R.id.tv_version);

        String remearks = apkVersionModel.getRemarks();
        if (!TextUtils.isEmpty(remearks)) {
            remearks = remearks.replace("\\n", "\n");
        }
        tvUpdateContent.setText(remearks);
        tvVersion.setText("版本:" + apkVersionModel.getAppVersion());

        contentView.findViewById(R.id.btn_update_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickUpdate != null) {
                    mOnClickUpdate.sure();
                }
                dismiss();
            }
        });
        contentView.findViewById(R.id.btn_update_wait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nActivity != null) {
                    nActivity.finish();
                }
                dismissAllowingStateLoss();
            }
        });
        builder.setCancelable(false);
        setCancelable(false);
        return builder.show();
    }

    public interface OnClickUpdate {
        void sure();
    }
}
