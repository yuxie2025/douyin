package com.yuxie.demo.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.yuxie.demo.service.RedPacketAccessibilityService;

public class AccessibilityUtils {

    /**
     * 跳转微信主界面
     */
    public static void intentWechat(Activity activity) {
        Intent wxIntent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        wxIntent.setAction(Intent.ACTION_MAIN);
        wxIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        wxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        wxIntent.setComponent(cmp);
        activity.startActivity(wxIntent);
    }

    public static void openService(Activity activity) {
        try {
            //跳转系统自带界面 辅助功能界面
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 此方法用来判断当前应用的辅助功能服务是否开启
     *
     * @param context
     * @return
     */
    public static boolean isServiceOpening(Context context) {
        int accessibilityEnabled = 0;
        // TestService为对应的服务
        String service = context.getPackageName() + "/" + RedPacketAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
