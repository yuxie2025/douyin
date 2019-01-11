package com.baselib.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baselib.basebean.BaseMsgEvent;
import com.baselib.baserx.RxBus;
import com.baselib.enums.BaseMsgEnum;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

/**
 * Created by Lankun on 2018/12/12
 */
public class DownLoadCompleteReceiver extends BroadcastReceiver {

    private final String mUrl;
    private final File mFile;
    private String mFileName;

    public DownLoadCompleteReceiver(String url, File file, String fileName) {
        mUrl = url;
        mFile = file;
        mFileName = fileName;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //下载完成修改文件名
            FileUtils.rename(mFile, mFileName);
            RxBus.getInstance().post(new BaseMsgEvent<>(mUrl, BaseMsgEnum.DOWNLOAD_COMPLETE));
            ToastUtils.showShort("下载完成,路径:" + "PEMS/" + mFileName);
        }
    }
}
