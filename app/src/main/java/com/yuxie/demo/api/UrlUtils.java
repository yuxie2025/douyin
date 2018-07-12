package com.yuxie.demo.api;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author llk
 * @time 2018/7/12 13:42
 * @class describe
 */
public class UrlUtils {

    public static String[] string2Url(String url) {
        String[] urls = new String[2];

        URL urlHost = null;
        String host = "http://www.baidu.com";
        String path = "";
        try {
            urlHost = new URL(url);
            if (url.contains("https")) {
                host = "https://" + urlHost.getHost();
            } else {
                host = "http://" + urlHost.getHost();
            }
            path = urlHost.getPath();
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }
        urls[0] = host;
        urls[1] = path;
        return urls;
    }

}
