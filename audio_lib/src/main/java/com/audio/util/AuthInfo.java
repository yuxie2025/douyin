package com.audio.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fujiayi on 2017/6/24.
 */
@Deprecated
public class AuthInfo {


    private static HashMap<String, Object> authInfo;

    private static final String TAG = "AuthInfo";

    public static Map<String, Object> getAuthParams(final Context context) {
        if (authInfo == null) {
            try {
                authInfo = new HashMap<String, Object>(3);
                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                int appId = appInfo.metaData.getInt("com.baidu.speech.APP_ID");
                String configKey = appInfo.metaData.getString("com.baidu.speech.API_KEY");
                String configSecret = appInfo.metaData.getString("com.baidu.speech.SECRET_KEY");
                authInfo.put("appid", appId); // 认证相关, key, 从开放平台(http://yuyin.baidu.com)中获取的key
                authInfo.put("appkey", configKey); // 认证相关, key, 从开放平台(http://yuyin.baidu.com)中获取的key
                authInfo.put("secret", configSecret); // 认证相关, secret, 从开放平台(http://yuyin.baidu.com)secret

            } catch (Exception e) {
                e.printStackTrace();
                String message = "请在AndroidManifest.xml中配置APP_ID, API_KEY 和 SECRET_KEY";
                Logger.error(TAG, message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return null;
            }
        }
        return authInfo;
    }


}
