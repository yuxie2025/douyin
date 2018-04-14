package com.apkupdate.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.apkupdate.R;


/**
 * Created by luo on 2018/3/6.
 */

public class UpdateProgressDialog extends DialogFragment {

    private MProgressBar progressBar;
    private TextView tvProgress;

    public static UpdateProgressDialog getDialog(Activity activity) {
        final UpdateProgressDialog updateDialog = new UpdateProgressDialog();
        Bundle bundle = new Bundle();
        updateDialog.setArguments(bundle);
        return updateDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View titleView = getActivity().getLayoutInflater().inflate(R.layout.dialog_apk_update_progress_title_view, null);
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_apk_update_progress_view, null);
        tvProgress = (TextView) contentView.findViewById(R.id.tv_progress);
        progressBar = (MProgressBar) contentView.findViewById(R.id.progress_bar);
        progressBar.setTextVisibility(false);
        builder.setCustomTitle(titleView);
        builder.setView(contentView);
        builder.setCancelable(false);
        setCancelable(false);
        return builder.show();
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        tvProgress.setText(progress + "%");
        if (progress >= 99)
            dismissAllowingStateLoss();
    }
}
