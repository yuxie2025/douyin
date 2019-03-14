package com.yuxie.demo.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.baselib.baseapp.BaseApplication;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lankun on 2019/03/14
 */
public class SysDownloadUtil {

    private static final String KEY_DOWNLOAD_DIR = "a_video";

    private static final String downloadDir = Environment.getExternalStorageDirectory() + "/" + KEY_DOWNLOAD_DIR;

    List<String> downloadList = new ArrayList<>();

    DownLoadCompleteReceiver mDownloadBroadcast;

    /**
     * 下载文件
     *
     * @param url 下载地址
     */
    public void download(Activity mActivity, String url, String fileName) {

        if (TextUtils.isEmpty(url) || !url.contains("/")) {
            ToastUtils.showShort("下载链接错误!");
            return;
        }

        String downloadFile = downloadDir + "/" + fileName;
        if (FileUtils.isFileExists(downloadFile)) {
            ToastUtils.showShort("已经下载过了!");
            return;
        }

        DownloadManager downloadManager = (DownloadManager) BaseApplication.getAppContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 通知栏的下载通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);

        //任务是否存在
        boolean isExists = false;
        for (int i = 0; i < downloadList.size(); i++) {
            if (url.equals(downloadList.get(i))) {
                isExists = true;
                break;
            }
        }
        if (isExists) {
            ToastUtils.showShort("正在下载中...");
            return;
        }

        downloadList.add(url);

        File cacheFile = new File(downloadDir, EncryptUtils.encryptMD5ToString(fileName));
        ToastUtils.showShort("开始下载...");
        request.setDestinationInExternalPublicDir(KEY_DOWNLOAD_DIR, fileName);
        downloadManager.enqueue(request);
        //文件下载完成会发送完成广播，可注册广播进行监听
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        mDownloadBroadcast = new DownLoadCompleteReceiver(url, cacheFile, fileName);
        mActivity.registerReceiver(mDownloadBroadcast, intentFilter);
    }

    public void unregister(Activity mActivity) {
        if (mDownloadBroadcast != null) {
            mActivity.unregisterReceiver(mDownloadBroadcast);
        }
    }

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
                downloadList.remove(mUrl);
                ToastUtils.showShort("下载完成,路径:" + KEY_DOWNLOAD_DIR + "/" + mFileName);
            }
        }
    }


}
