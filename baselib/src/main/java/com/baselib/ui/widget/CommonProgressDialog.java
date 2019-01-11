package com.baselib.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.baselib.R;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;

/**
 * 作者: llk on 2017/11/9.
 * 自定义进度条
 */
@SuppressWarnings("unused")
public class CommonProgressDialog extends AlertDialog {
    private ProgressBar mProgress;
    private TextView mProgressNumber;
    private TextView mProgressPercent;
    private TextView mProgressMessage;
    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;
    private String TAG = "CommonProgressDialog";
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;

    public CommonProgressDialog(Context context) {
        super(context);
        initFormats();
    }

    public static class MyHandler extends Handler {

        WeakReference<CommonProgressDialog> reference;

        public MyHandler(CommonProgressDialog dialog) {
            reference = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CommonProgressDialog dialog = reference.get();

            if (dialog == null) return;

            int progress = dialog.mProgress.getProgress();
            int max = dialog.mProgress.getMax();
            double dProgress = (double) progress / (double) (1024 * 1024);
            double dMax = (double) max / (double) (1024 * 1024);
            if (dialog.mProgressNumberFormat != null) {
                String format = dialog.mProgressNumberFormat;
                dialog.mProgressNumber.setText(String.format(format, dProgress,
                        dMax));
            } else {
                dialog.mProgressNumber.setText("");
            }
            if (dialog.mProgressPercentFormat != null) {
                double percent = (double) progress / (double) max;
                SpannableString tmp = new SpannableString(
                        dialog.mProgressPercentFormat.format(percent));
                tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                        0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                dialog.mProgressPercent.setText(tmp);
            } else {
                dialog.mProgressPercent.setText("");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_progress_dialog);
        mProgress = findViewById(R.id.progress);
        mProgressNumber = findViewById(R.id.progress_number);
        mProgressPercent = findViewById(R.id.progress_percent);
        mProgressMessage = findViewById(R.id.progress_message);
        // LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewUpdateHandler = new MyHandler(this);

        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }

    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        }
        // else {
        // mIndeterminate = indeterminate;
        // }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        // super.setMessage(message);
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }


}
