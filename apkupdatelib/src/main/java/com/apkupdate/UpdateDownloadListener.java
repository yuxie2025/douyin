package com.apkupdate;

/**
 * Created by luo on 2018/3/6.
 */

public interface UpdateDownloadListener {

    public void onStarted();
    public void onProgressChanged(int progress, String downloadUrl);
    public void onFinished(float completeSize, String downloadUrl);
    public void onFailure();
}
