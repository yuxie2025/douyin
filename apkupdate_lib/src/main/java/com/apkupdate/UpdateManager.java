package com.apkupdate;

import android.util.Log;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by luo on 2018/3/6.
 */

public class UpdateManager {

    private static UpdateManager updateManager;
    private ThreadPoolExecutor threadPoolExecutor;
    private UpdateDownloadRequest request;

    private UpdateManager() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public static UpdateManager getInstance() {
        if (updateManager == null) {
            synchronized (UpdateManager.class) {
                if (updateManager == null)
                    updateManager = new UpdateManager();
            }
        }
        return updateManager;
    }

    public void startDownloads(String downloadUrl, String localPath, UpdateDownloadListener listener) {
        checkLocalFilePath(localPath);
        request = new UpdateDownloadRequest(downloadUrl, localPath, listener);
        Future<?> future = threadPoolExecutor.submit(request);

    }

    /**
     * 检查文件路径是否存在
     *
     * @param path
     */
    private void checkLocalFilePath(String path) {
        File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }
}

