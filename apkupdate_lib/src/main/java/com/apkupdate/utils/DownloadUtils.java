package com.apkupdate.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Lankun on 2019/04/02
 */
public class DownloadUtils {

    public static Call download(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient.Builder dlOkhttp = new OkHttpClient.Builder();
        // 发起请求
        Call call = dlOkhttp.build().newCall(request);
        call.enqueue(callback);
        return call;
    }
}
