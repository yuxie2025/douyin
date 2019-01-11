package com.baselib.uitls;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Lankun on 2018/12/11
 */
@SuppressWarnings("unused")
public class DownLoadUtils {

    public static boolean downFile(String uri, String filePath) {

        boolean result = false;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(uri);
            // 2)根据url,获得HttpURLConnection 连接工具
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "okhttp/3.8.1");
//            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("Content-Type", "application/pdf");
            conn.setDoOutput(false);
            // 3)很据连接工具，获得响应码
            int responseCode = conn.getResponseCode();
            // 4)响应码正确，即可获取、流、文件长度
            LogUtils.d("responseCode:" + responseCode);
            if (responseCode == 200) {
                InputStream inStream = conn.getInputStream();
                File saveFile = new File(filePath);
                if (saveFile.exists()) {
                    long fileLength = conn.getContentLength();
                    if (fileLength == saveFile.length()) {
                        return true;
                    } else {
                        saveFile.delete();
                    }
                }
                result = FileIOUtils.writeFileFromIS(saveFile, inStream);
                inStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return result;
    }

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
