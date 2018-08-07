package com.yuxie.demo.api;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author llk
 * @time 2018/7/12 13:42
 * @class describe
 */
public class UrlUtils {

    private static final String TAG = "UrlUtils";

    public static String[] string2Url(String url) {
        String[] urls = new String[2];

        URL urlHost = null;
        String host = "http://www.baidu.com";
        String path = url;
        try {
            urlHost = new URL(url);
            if (url.contains("https")) {
                host = "https://" + urlHost.getHost();
            } else {
                host = "http://" + urlHost.getHost();
            }

            if (path.contains("https://")) {
                path = path.replaceFirst("https://", "");
            }
            if (path.contains("http://")) {
                path = path.replaceFirst("http://", "");
            }

            if (path.contains("/")) {
                path = path.substring(path.indexOf("/"), path.length());
            } else {
                path = "";
            }
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }
        urls[0] = host;
        urls[1] = path;
        return urls;
    }

}
