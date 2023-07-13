package com.yuxie.baselib.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0";

    public static LinkedList<String> linkedList = new LinkedList<>();

    public static void downloadDialog(Context mContext, String url, String shortUrl) {

        if (TextUtils.isEmpty(shortUrl) && !TextUtils.isEmpty(url)) {
            shortUrl = url.split("\\?")[0];
        }

        if (isExists(shortUrl)) {
            //下载过了
            ToastUtils.showLong("下载过了,文件在Download目录下!");
            return;
        }

        if (linkedList.contains(url)) {
            return;
        }
        linkedList.add(url);

        String finalShortUrl = shortUrl;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("是否下载？");
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            //处理下载事件
            download(url, finalShortUrl);
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setOnDismissListener(dialogInterface -> {
            linkedList.remove(url);
        });
        builder.show();
    }

    public static void download(String url, String shortUrl) {

//        //链接示例
//        https://v26-web.douyinvod.com/d9ec5407864eff09d9daef3faca2ef5e/6422a9c1/video/tos/cn/tos-cn-ve-15c001-alinc2/osLh7xvAIIEEBdBZnCQeJoeDUenQu0A9XAb55J/?
//        a=6383&ch=26&cr=3&dr=0&lr=all&cd=0%7C0%7C0%7C3&cv=1&br=3748&bt=3748&cs=0&ds=4&ft=bvTKJbQQqUYqfJEZPo0OW_EklpPiX9A_ZMVJEH28f2vPD-I&
//        mime_type=video_mp4&qs=0&rc=M2Q7ZDg1ZmQ1MzU3ZWQzNUBpamV1a2Y6ZjhyajMzNGkzM0BjMV81YmJhX2ExL15hNTMwYSNrbjRvcjRvc2RgLS1kLS9zcw%3D%3D
//        &l=20230328154752918F62A33B161C0D313A&btag=8000&testst=1679989686676

        if (TextUtils.isEmpty(shortUrl) && !TextUtils.isEmpty(url)) {
            shortUrl = url.split("\\?")[0];
        }

        if (isExists(shortUrl)) {
            //下载过了
            ToastUtils.showLong("下载过了,文件在Download目录下!");
            return;
        }

        Log.i(TAG, "download_videoUrl:" + url);

        String finalShortUrl = shortUrl;
        new Thread(() -> {
            boolean re = DownloadUtils.downloadVideo(url, PathUtils.getExternalDownloadsPath(), finalShortUrl);
            if (re) {
                ToastUtils.cancel();
                ToastUtils.showLong("下载成功,文件在Download目录下!");
            }
        }).start();

    }

    /**
     * 下载抖音无水印视频到某个路径下.
     *
     * @param shareInfo    下载链接
     * @param saveToFolder 下载目录
     */
    public static boolean downloadVideo(String shareInfo, String saveToFolder, String shortUrl) {

        //创建目录
        FileUtils.createOrExistsDir(saveToFolder);

        if (isExists(shortUrl)) {
            //下载过了
            return true;
        }

        File file = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4");
        File fileTemp = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".temp");


        Map<String, String> headers = new HashMap<>();
        try {
            URL url = new URL(shareInfo);
            //host需要随着变化不然会下载失败
            headers.put("Host", url.getHost());
        } catch (MalformedURLException ignored) {
        }
        headers.put("Connection", "keep-alive");
        headers.put("User-Agent", UA);
        InputStream in = get(shareInfo, headers);
        if (in == null) {
            return false;
        }
        //删除再创建，缓存文件
        FileUtils.delete(fileTemp);
        FileUtils.createOrExistsFile(fileTemp);
        Log.i(TAG, "file_path:" + fileTemp.getAbsolutePath());

        boolean writeRe = FileIOUtils.writeFileFromIS(fileTemp, in);
        if (writeRe) {
            boolean re = FileUtils.copy(fileTemp, file);
            if (re) {
                FileUtils.delete(fileTemp);
                return true;
            }
            FileUtils.delete(file);
        }
        return false;
    }

    public static InputStream get(String url, Map<String, String> headers) {
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
                Log.i(TAG, "header_key:" + entry.getKey() + ",value:" + entry.getValue());
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            Log.i(TAG, "url:" + url);
            Log.i(TAG, "code:" + code);
            Log.i(TAG, "conn.getContentType():" + conn.getContentType());

            if (code == 302) {
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String locationUrl = conn.getHeaderField("Location");
                Log.i(TAG, "locationUrl:" + locationUrl);
                return get(locationUrl, new HashMap<>());
            }

            if (code == 200) {
                return conn.getInputStream();
            } else {
                ToastUtils.showLong("请稍后再试，错误码：" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContentType(String url) {
        Map<String, String> headers = new HashMap<>();
        try {
            URL mUrl = new URL(url);
            //host需要随着变化不然会下载失败
            headers.put("Host", mUrl.getHost());
        } catch (MalformedURLException ignored) {
        }
        headers.put("User-Agent", UA);
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            Log.i(TAG, "url:" + url);
            Log.i(TAG, "code:" + code);
            Log.i(TAG, "conn.getContentType():" + type);
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isExists(String shortUrl) {
        String saveToFolder = PathUtils.getExternalDownloadsPath();
//        Log.i(TAG, "isExists:" + shortUrl);
        if (StringUtils.isEmpty(shortUrl)) {
            //下载链接为空
            return false;
        }

        File file = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4");
        if (file.exists()) {
            //下载过了
            return true;
        }
        return false;
    }

    /**
     * 从路径中提取itemId
     *
     * @param url
     * @return
     */
    public static String parseItemIdFromUrl(String url) {
        // https://www.iesdouyin.com/share/video/6519691519585160455/?region=CN&mid=6519692104368098051&u_code=36fi3lehcdfb&titleType=title
        String ans = "";
        String[] firstSplit = url.split("\\?");
        if (firstSplit.length > 0) {
            String[] strings = firstSplit[0].split("/");
            // after video.
            for (String string : strings) {
                if (!TextUtils.isEmpty(string)) {
                    return string;
                }
            }
        }
        return ans;
    }
}
