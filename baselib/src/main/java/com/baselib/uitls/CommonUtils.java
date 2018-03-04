package com.baselib.uitls;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.baselib.baseapp.BaseApplication;
import com.baselib.utilcode.util.ActivityUtils;
import com.baselib.utilcode.util.AppUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luo on 2017/11/26.
 */

public class CommonUtils {

    /**
     * 判断是否是正确的json格式数据
     *
     * @param json
     * @return
     */
    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 判断是否是double字符串
     *
     * @param doubleString
     * @return
     */
    public static boolean isDouble(String doubleString) {
        if (TextUtils.isEmpty(doubleString)) {
            return false;
        }
        try {
            Double.parseDouble(doubleString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是int字符串
     *
     * @param intString
     * @return
     */
    public static boolean isInteger(String intString) {
        if (TextUtils.isEmpty(intString)) {
            return false;
        }
        try {
            Integer.parseInt(intString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是long字符串
     *
     * @param longString
     * @return
     */
    public static boolean isLong(String longString) {
        if (TextUtils.isEmpty(longString)) {
            return false;
        }
        try {
            Long.parseLong(longString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符串转int
     *
     * @param intString
     * @return
     */
    public static int string2Int(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 字符串转double
     *
     * @param doubleString
     * @return
     */
    public static double string2Double(String doubleString) {
        try {
            return Double.parseDouble(doubleString);
        } catch (Exception e) {
            return 0d;
        }
    }

    /**
     * 字符串转long
     *
     * @param longString
     * @return
     */
    public static long string2Long(String longString) {
        try {
            return Long.parseLong(longString);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static BluetoothAdapter getDefaultAdapter() {
        BluetoothAdapter adapter = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            adapter = BluetoothAdapter.getDefaultAdapter();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            final BluetoothManager bluetoothManager = (BluetoothManager) BaseApplication.getAppContext().getSystemService(Context.BLUETOOTH_SERVICE);
            adapter = bluetoothManager.getAdapter();
        }
        return adapter;
    }

    /**
     * 蓝牙是否可用
     *
     * @return
     */
    public static boolean isBluetoothEnabled() {
        //获取蓝牙适配器
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null == adapter) {
            return false;
        } else {
            if (!adapter.isEnabled()) {
                return false;
            } else {
                return true;
            }
        }
    }


    /**
     * 上次点击时间
     */
    private static long lastClickTime = 0;

    /**
     * 检测是连续点击,连续点击事件间隔太小不处理
     *
     * @return
     */
    public static boolean isDoubleClick() {
        /**
         * 最小间隔时间
         */
        int MIN_CLICK_DELAY_TIME = 1000;
        return isDoubleClick(MIN_CLICK_DELAY_TIME);
    }

    /**
     * @param delayTime 间隔时间(自定义间隔时间)
     * @return
     */
    public static boolean isDoubleClick(long delayTime) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > delayTime || currentTime - lastClickTime < 0) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @return
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = BaseApplication.getAppContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     *
     * @param date       String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static String stringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(date);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf2.format(d);
    }

    public static void toSelfSetting() {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", BaseApplication.getAppContext().getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", BaseApplication.getAppContext().getPackageName());
        }
        BaseApplication.getAppContext().startActivity(mIntent);
    }

    public static void baiduGuide(String latitude, String longitude, String addrees) {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?" +
                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:" + latitude + "," + longitude + "|name:" + addrees +        //终点
                    "&mode=driving&" +          //导航路线方式
                    "region=北京" +           //
                    "&src=" + AppUtils.getAppName() + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            BaseApplication.getAppContext().startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
        }
    }

    public static void gaodeGuide(String latitudeStr, String longitudeStr, String addrees, String latitudeStart, String longitudeStart) {

        if (!CommonUtils.isDouble(latitudeStr) || !CommonUtils.isDouble(longitudeStr)) {
            return;
        }

        double latitude = Double.parseDouble(latitudeStr);
        double longitude = Double.parseDouble(longitudeStr);


        double[] lng = GPSUtil.bd09_To_Gcj02(latitude, longitude);


        try {
            Uri uri = Uri.parse("androidamap://navi?" +
                    "sourceApplication=" + URLEncoder.encode(AppUtils.getAppName(), "utf-8") +
                    "&poiname=" + URLEncoder.encode(addrees, "utf-8") +
                    "&slat=" + latitudeStart +
                    "&slon=" + longitudeStart +
                    "&lat=" + lng[0] +
                    "&lon=" + lng[1] + "&dev=0&stype=0");

            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(uri);
            intent.setPackage("com.autonavi.minimap");
            BaseApplication.getAppContext().startActivity(intent);

        } catch (Exception e) {
        }
    }

}
