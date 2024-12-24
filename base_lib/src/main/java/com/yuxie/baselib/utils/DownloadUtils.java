package com.yuxie.baselib.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    public static final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0";

    public static LinkedList<String> linkedList = new LinkedList<>();

    public static void shouldInterceptRequest(Context mContext, WebView webView, WebResourceRequest webResourceRequest, String url) {
        String requestUrl = webResourceRequest.getUrl().toString();

        if (requestUrl.contains("?previous_page=") ||
                requestUrl.contains("&testst=") ||
                requestUrl.startsWith("https://sp0.baidu.com") ||
                requestUrl.startsWith("https://p-pc-weboff.byteimg.com") ||
                requestUrl.startsWith("https://helpdesk.bytedance.com")) {
            return;
        }

        //视频下载
        if (requestUrl.contains("video/") || requestUrl.contains(".mp4")) {
//            Log.i(TAG, "webResourceRequest:" + requestUrl);
            HashMap<String, String> reList = DownloadUtils.getContentType(requestUrl, webResourceRequest);
            String type = reList.get("type");
            String eTag = reList.get("eTag");
            String contentLength = reList.get("contentLength");
            String contentRange = reList.get("contentRange");
            String range = reList.get("range");
            if (type != null && type.equals("video/mp4")) {
                if (contentRange != null && contentLength != null
                        && !TextUtils.isEmpty(contentLength) && !TextUtils.isEmpty(contentRange)
                        && !contentRange.endsWith(contentLength)) {
                    return;
                }
                if (range != null && !"bytes=0-".equals(range)) {
                    return;
                }
                if (!TextUtils.isEmpty(eTag) && isExists(eTag)) {
                    ToastUtils.showLong("下载过了,文件在Download目录下!");
                    return;
                }
                Log.i(TAG, "webResourceRequest_video:" + requestUrl);
                Log.i(TAG, "webResourceRequest_video_getContentType_re: " + reList.toString());
                String shortUrl = "";
                if (url.contains("douyin.com") || url.contains("ixigua.com")) {
                    shortUrl = url;
                }
                if (!TextUtils.isEmpty(eTag)) {
                    shortUrl = eTag;
                }
                String finalShortUrl = shortUrl;
                new Handler(Looper.getMainLooper()).post(() -> DownloadUtils.downloadDialog(mContext, requestUrl, finalShortUrl, webResourceRequest));
            }
        }
    }


    public static void downloadDialog(Context mContext, String url, String shortUrl, WebResourceRequest webResourceRequest) {

        if (TextUtils.isEmpty(shortUrl) && !TextUtils.isEmpty(url)) {
            shortUrl = url.split("\\?")[0];
            shortUrl = EncryptUtils.encryptMD5ToString(shortUrl);
        }

        if (isExists(shortUrl)) {
            //下载过了
            ToastUtils.showLong("下载过了,文件在Download目录下!");
            return;
        }

        if (linkedList.contains(shortUrl)) {
            return;
        }
        linkedList.add(shortUrl);

        String finalShortUrl = shortUrl;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("是否下载？");
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            //处理下载事件
            download(url, finalShortUrl, webResourceRequest);
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setOnDismissListener(dialogInterface -> {
            linkedList.remove(finalShortUrl);
        });
        builder.show();
    }

    public static void download(String url, String shortUrl, WebResourceRequest webResourceRequest) {
//        //链接示例
//        https://v26-web.douyinvod.com/d9ec5407864eff09d9daef3faca2ef5e/6422a9c1/video/tos/cn/tos-cn-ve-15c001-alinc2/osLh7xvAIIEEBdBZnCQeJoeDUenQu0A9XAb55J/?
//        a=6383&ch=26&cr=3&dr=0&lr=all&cd=0%7C0%7C0%7C3&cv=1&br=3748&bt=3748&cs=0&ds=4&ft=bvTKJbQQqUYqfJEZPo0OW_EklpPiX9A_ZMVJEH28f2vPD-I&
//        mime_type=video_mp4&qs=0&rc=M2Q7ZDg1ZmQ1MzU3ZWQzNUBpamV1a2Y6ZjhyajMzNGkzM0BjMV81YmJhX2ExL15hNTMwYSNrbjRvcjRvc2RgLS1kLS9zcw%3D%3D
//        &l=20230328154752918F62A33B161C0D313A&btag=8000&testst=1679989686676

        new Thread(() -> {
            boolean re = DownloadUtils.downloadVideo(url, PathUtils.getExternalDownloadsPath(), shortUrl, webResourceRequest);
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
    public static boolean downloadVideo(String shareInfo, String saveToFolder, String shortUrl, WebResourceRequest webResourceRequest) {

        //创建目录
        FileUtils.createOrExistsDir(saveToFolder);

        if (isExists(shortUrl)) {
            //下载过了
            return true;
        }

        Map<String, String> headers = new HashMap<>();
        if (webResourceRequest != null) {
            headers = webResourceRequest.getRequestHeaders();
        } else {
            try {
                URL url = new URL(shareInfo);
                //host需要随着变化不然会下载失败
                headers.put("Host", url.getHost());
            } catch (MalformedURLException ignored) {
            }
            headers.put("Connection", "keep-alive");
            headers.put("User-Agent", UA);
        }
        HttpURLConnection conn = get(shareInfo, headers);
        if (conn == null) {
            return false;
        }

        String fileName = EncryptUtils.encryptMD5ToString(shortUrl);
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> eTag = headerFields.get("ETag");
        String fileMd5Tag = "";
        if (eTag != null && eTag.size() == 1) {
            String eTagStr = eTag.get(0).replace("\"", "").toUpperCase();
            fileName = eTagStr;
            fileMd5Tag = eTagStr;
        }

        File file = new File(saveToFolder + "/" + fileName + ".mp4");
        File fileTemp = new File(saveToFolder + "/" + fileName + ".temp");

        InputStream in = null;
        try {
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in == null) {
            return false;
        }
        //删除再创建，缓存文件
        FileUtils.delete(fileTemp);
        FileUtils.createOrExistsFile(fileTemp);
        Log.i(TAG, "downloadVideo_file_path:" + fileTemp.getAbsolutePath());

        boolean writeRe = FileIOUtils.writeFileFromIS(fileTemp, in);
        if (!writeRe) {
            FileUtils.delete(fileTemp);
            return false;
        }
        if (!TextUtils.isEmpty(fileMd5Tag)) {
            String tempMd5 = EncryptUtils.encryptMD5File2String(fileTemp);
            Log.i(TAG, "downloadVideo_file_path:" + fileTemp.getAbsolutePath()
                    + ",tempMd5:" + tempMd5 + ",fileMd5Tag:" + fileMd5Tag);
            if (!fileMd5Tag.equals(tempMd5)) {
                FileUtils.delete(fileTemp);
                return false;
            }
        }
        boolean re = FileUtils.copy(fileTemp, file);
        if (re) {
            FileUtils.delete(fileTemp);
            return true;
        }
        FileUtils.delete(file);
        return false;
    }

    public static HttpURLConnection get(String url, Map<String, String> headers) {
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
//                Log.i(TAG, "Request_header_key:" + entry.getKey() + ",value:" + entry.getValue());
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            Log.i(TAG, "get:_ContentType:" + type + ",code:" + code + ",url:" + url);
            if (code == 302) {
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String locationUrl = conn.getHeaderField("Location");
                Log.i(TAG, "locationUrl:" + locationUrl);
                return get(locationUrl, new HashMap<>());
            }

            if (code == 200 || code == 206) {
                return conn;
            } else {
                ToastUtils.showLong("请稍后再试，错误码：" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, String> getContentType(String url, WebResourceRequest webResourceRequest) {
        HashMap<String, String> re = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        if (webResourceRequest != null) {
            headers = webResourceRequest.getRequestHeaders();
        } else {
            try {
                URL mUrl = new URL(url);
                //host需要随着变化不然会下载失败
                headers.put("Host", mUrl.getHost());
            } catch (MalformedURLException ignored) {
            }
            headers.put("User-Agent", UA);
        }

        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
//                Log.i(TAG, "getContentType:Key():" + entry.getKey() + ",Value():" + entry.getValue());
                if (entry.getKey().equals("Range")) {
                    re.put("range", entry.getValue());
                }
                if (entry.getKey().equals("Access-Control-Request-Method")) {
                    re.put("requestMethod", entry.getValue());
                }
                if (entry.getKey().equals("Access-Control-Request-Headers")) {
                    re.put("requestHeaders", entry.getValue());
                }
            }
            int code = conn.getResponseCode();
            String type = conn.getContentType();
            re.put("type", type);
            re.put("code", code + "");

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> eTag = headerFields.get("ETag");
            if (eTag != null && eTag.size() == 1) {
                String eTagStr = eTag.get(0).replace("\"", "").toUpperCase();
                re.put("eTag", eTagStr);
            }
            List<String> contentLengthHeader = headerFields.get("Content-Length");
            if (contentLengthHeader != null && contentLengthHeader.size() == 1) {
                String contentLengthStr = contentLengthHeader.get(0);
                re.put("contentLength", contentLengthStr);
            }
            List<String> contentRangeHeader = headerFields.get("Content-Range");
            if (contentRangeHeader != null && contentRangeHeader.size() == 1) {
                String contentRangeStr = contentRangeHeader.get(0);
                re.put("contentRange", contentRangeStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getContentType:_ContentType:" + re.get("type") + ",eTag:" + re.get("eTag")
                + ",code:" + re.get("code")
                + ",range:" + re.get("range")
                + ",contentLength:" + re.get("contentLength")
                + ",contentRange:" + re.get("contentRange") + ",url:" + url);
        return re;
    }

    public static boolean isExists(String fileMd5) {
        String saveToFolder = PathUtils.getExternalDownloadsPath();
        if (StringUtils.isEmpty(fileMd5)) {
            //下载链接为空
            return false;
        }
        File file = new File(saveToFolder + "/" + fileMd5 + ".mp4");
        return file.exists();
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
