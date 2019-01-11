package com.baselib.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * Created by Lankun on 2018/12/11
 */
@SuppressWarnings("unused")
public class SuperFileView extends FrameLayout implements TbsReaderView.ReaderCallback {

    private TbsReaderView mTbsReaderView;
    private Context context;

    public SuperFileView(Context context) {
        this(context, null, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperFileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }

    private OnGetFilePathListener mOnGetFilePathListener;

    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }

    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    public void displayFile(File mFile) {

        if (mFile == null || TextUtils.isEmpty(mFile.toString())) {
            LogUtils.e("文件路径无效！");
            return;
        }

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
        File bsReaderTempFile = new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                LogUtils.e("创建TbsReaderTemp失败！！！！！");
            }
        }
        //加载文件
        Bundle localBundle = new Bundle();
        localBundle.putString("filePath", mFile.toString());
        localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");
        if (this.mTbsReaderView == null) this.mTbsReaderView = getTbsReaderView(context);
        boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);
        if (bool) {
            this.mTbsReaderView.openFile(localBundle);
        }
    }

    /***
     *获取文件类型
     *
     *@param paramString 文件路径或文件名
     * @return 文件类型
     */
    private String getFileType(String paramString) {
        return CommonUtils.getFileExt(paramString);
    }

    public void show() {
        if (mOnGetFilePathListener != null) {
            mOnGetFilePathListener.onGetFilePath(this);
        }
    }

    /***
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(SuperFileView mSuperFileView2);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        LogUtils.e("****************************************************" + integer);
    }

    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
