package com.baselib.uitls;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baselib.baseapp.BaseApplication;
import com.baselib.listener.MyTextWatcher;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by luo on 2017/11/26.
 */

public class CommonUtils {

    /**
     * 判断集合是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isNoEmpty(List list) {
        if (list == null || list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }


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
     * 判断是否是坏的json
     *
     * @param json
     * @return
     */
    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
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
        int MIN_CLICK_DELAY_TIME = 5000;
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
     * 是否是发布版
     *
     * @return
     */
    public static boolean isRelease() {
        return !isApkDebugable();
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

    /**
     * 跳转应用权限设置页面
     */
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

    /**
     * 跳转到百度导航
     *
     * @param latitude
     * @param longitude
     * @param addrees
     */
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

    /**
     * 跳转到高德导航
     *
     * @param latitudeStr
     * @param longitudeStr
     * @param addrees
     * @param latitudeStart
     * @param longitudeStart
     */
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

    /**
     * 获取view的内容
     *
     * @param view
     * @return
     */
    public static String getViewContent(View view) {
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        } else if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        }
        return "";
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files, List<String> paramNames) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());

        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData(paramNames.get(i), files.get(i).getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    /**
     * 替换手机号码中间4位
     * 括号表示组，被替换的部分$n表示第n组的内容
     *
     * @param number
     * @return
     */
    public static String phoneNumberReplace(String number) {
        String result = "";
        if (TextUtils.isEmpty(number)) {
            return result;
        }
        result = number.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return result;
    }

    /**
     * 隐藏身份证中间部分,仅首位可见
     *
     * @param cardId
     * @return
     */
    public static String hiddenCardId(String cardId) {
        String result = "";
        if (TextUtils.isEmpty(cardId)) {
            return result;
        }
        if (cardId.length() == 18) {
            result = cardId.replaceAll("(\\d{1})\\d{16}(\\d{1})", "$1****************$2");
        } else if (cardId.length() == 15) {
            result = cardId.replaceAll("(\\d{1})\\d{13}(\\d{1})", "$1*************$2");
        }
        return result;
    }

    //验证码倒计时
    public static void theCountdownCode(TextView tvGetPhoneCode) {

        final int count = 60;

        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count - aLong; //
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvGetPhoneCode.setEnabled(false);//在发送数据的时候设置为不能点击#c02826
                        tvGetPhoneCode.setTextColor(Color.parseColor("#555555"));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tvGetPhoneCode.setEnabled(true);
                        tvGetPhoneCode.setText("获取验证码");//数据发送完后设置为原来的文字
                        tvGetPhoneCode.setTextColor(Color.parseColor("#1c1c1c"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        tvGetPhoneCode.setText("获取验证码(" + aLong + ")");
                    }
                });
    }

    /**
     * 4位分组,隐藏银行卡,(仅显示后面几位),不能不4整除的位数
     *
     * @param bankNumber
     * @return
     */
    public static String hiddenBankNumber(String bankNumber) {

        String result = "";
        if (TextUtils.isEmpty(bankNumber)) {
            return result;
        }

        if (bankNumber.length() < 4) {
            return "****";
        }

        //根据已知字符串的长度,参数信号,字符串,4个字符一个空格
        StringBuffer emptyString = new StringBuffer();

        for (int i = 0; i < bankNumber.length(); i++) {
            emptyString.append("*");
            //第一个字符,新增一个空格
            if ((i + 1) % 4 == 0) {
                //最后一位不加空格
                if ((i + 1) != bankNumber.length()) {
                    emptyString.append(" ");
                }
            }
        }
        int showLength = 0;
        int lastLength = bankNumber.length() % 4;
        if (lastLength == 0) {
            showLength = 4;
        } else {
            showLength = lastLength;
        }
        //截取不需要隐藏的字符串
        String bankNumberString = bankNumber.substring(bankNumber.length() - showLength, bankNumber.length());

        //把不需要隐藏的字符串拼接到,信号字符串上
        result = emptyString.substring(0, emptyString.length() - showLength) + bankNumberString;
        return result;
    }

    /**
     * 获取测试数据
     *
     * @param number
     * @return
     */
    public static List<String> getList(int number) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            strings.add("测试" + (i + 1));
        }
        return strings;
    }

    /**
     * 限制输入框限制工具
     *
     * @param editText
     * @param tip
     * @param charMaxNum
     */
    public static void ediTextSize(EditText editText, TextView tip, int charMaxNum) {

        //EditText手动设置值处理
        String etString = editText.getText().toString();
        tip.setText((etString.length()) + "/" + charMaxNum);

        editText.addTextChangedListener(new TextWatcher() {

            CharSequence temp; // 监听前的文本
            int editStart; // 光标开始位置
            int editEnd; // 光标结束位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tip.setText((s.length()) + "/" + charMaxNum);
            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = editText.getSelectionStart();
                editEnd = editText.getSelectionEnd();
                if (temp.length() > charMaxNum) {
                    s.delete(editStart - 1, editEnd);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }
        });

    }

    /**
     * 字符串转对应时间格式
     *
     * @param timeString 时间撮字符串 单位秒
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String time2String(String timeString) {
        long timeLong = string2Long(timeString) * 1000;
        return TimeUtils.millis2String(timeLong, ConstantUtils.YMDHM_FORMAT);
    }

    public static String getMatches(String content,String regex) {
        Pattern pattern = Pattern.compile(regex);//匹配的模式
        //通配符中也要加入转移字符 (.+?)代表要查找的内容
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            return matcher.group(1); //每次返回第一个即可 可用groupcount()方法来查看捕获的组数 个数
        }
        return "";

    }

}
