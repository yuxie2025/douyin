package com.baselib.uitls;

import android.text.TextUtils;

import com.baselib.BuildConfig;
import com.blankj.utilcode.util.SPUtils;

/**
 * 选择服务器工具
 * Created by Lankun on 2018/12/04
 */
public class BaseUrlUtil {

    public static final String KEY_BASE_URL = "BASE_URL";

    private static final String URL_HEAD = BuildConfig.HOST;
    /**
     * 项目名称
     */
    private static String ITEM = BuildConfig.ITEM;

    private static String BaseUrl = URL_HEAD + ITEM;

    /**
     * 获取baseUrl
     *
     * @return baseUrl
     */
    public static String getBaseUrl() {
        String baseUrl = SPUtils.getInstance().getString(KEY_BASE_URL);
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = BaseUrl;
        } else {
            baseUrl = baseUrl + ITEM;
        }
        return baseUrl;
    }

    /**
     * 设置base url
     *
     * @param mUrl url
     */
    public static void setBaseUrl(String mUrl) {
        SPUtils.getInstance().put(KEY_BASE_URL, mUrl);
    }

    /**
     * 获取url 不完整拼完整返回
     *
     * @param mUrl url
     * @return url
     */
    public static String getUrl(String mUrl) {
        if (!TextUtils.isEmpty(mUrl) && !mUrl.contains("http")) {
            mUrl = getBaseUrl() + mUrl;
        }
        return mUrl;
    }


}
