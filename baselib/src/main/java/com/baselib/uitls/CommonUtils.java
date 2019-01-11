package com.baselib.uitls;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;

import com.baselib.baseapp.BaseApplication;
import com.baselib.listener.MyTextWatcher;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by luo on 2017/11/26.
 * 常用工具集
 */
@SuppressWarnings("unused")
public class CommonUtils {

    /**
     * 判断集合是否不为空
     *
     * @param list 集合
     * @return 是否是集合
     */
    public static boolean isNoEmpty(List list) {
        boolean re;
        if (list == null || list.size() == 0) {
            re = false;
        } else {
            re = true;
        }
        return re;
    }

    /**
     * 判断集合是否为空
     *
     * @param list 集合
     * @return 是否为空
     */
    public static boolean isEmpty(List list) {
        return !isNoEmpty(list);
    }

    /**
     * 判断是否是正确的json格式数据
     *
     * @param json json数据
     * @return 结果
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
     * 判断是否是坏的json json数据
     *
     * @param json json数据
     * @return 结果
     */
    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    /**
     * 判断是否是double字符串
     *
     * @param doubleString double字符串
     * @return 结果
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
     * @param intString int字符串
     * @return 结果
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

    public static boolean isFloat(String floatString) {
        if (TextUtils.isEmpty(floatString)) {
            return false;
        }
        try {
            Float.parseFloat(floatString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是long字符串
     *
     * @param longString long字符串
     * @return 结果
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
     * @param intString int字符串
     * @return 结果
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
     * @param doubleString double字符串
     * @return 结果
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
     * @param longString 字符串
     * @return 结果
     */
    public static long string2Long(String longString) {
        try {
            return Long.parseLong(longString);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static float string2Float(String floatString) {
        try {
            return Float.parseFloat(floatString);
        } catch (Exception e) {
            return 0f;
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
     * @return 结果
     */
    public static boolean isBluetoothEnabled() {
        //获取蓝牙适配器
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            return false;
        } else {
            return adapter.isEnabled();
        }
    }

    /**
     * 上次点击时间
     */
    private static long lastClickTime = 0;

    /**
     * 检测是连续点击,连续点击事件间隔太小不处理
     *
     * @return 结果
     */
    public static boolean isDoubleClick() {
        int MIN_CLICK_DELAY_TIME = 500;
        return isDoubleClick(MIN_CLICK_DELAY_TIME);
    }

    /**
     * @param delayTime 间隔时间(自定义间隔时间)
     * @return 结果
     */
    public static boolean isDoubleClick(long delayTime) {
        long currentTime = System.currentTimeMillis();
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
     * @return 返回是否调试版本
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = BaseApplication.getAppContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 是否是发布版
     *
     * @return 是否发布
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
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern, Locale.getDefault());        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern, Locale.getDefault());        // 实例化模板对象
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
        mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        mIntent.setData(Uri.fromParts("package", BaseApplication.getAppContext().getPackageName(), null));
        BaseApplication.getAppContext().startActivity(mIntent);
    }

    /**
     * 跳转到百度导航
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param addrees   地址
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
        } catch (URISyntaxException ignored) {
        }
    }

    /**
     * 跳转到高德导航
     *
     * @param latitudeStr    终点纬度
     * @param longitudeStr   终点经度
     * @param addrees        终点地址
     * @param latitudeStart  开始纬度
     * @param longitudeStart 开始经度
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

        } catch (Exception ignored) {
        }
    }

    /**
     * 获取view的内容
     *
     * @param view 需要获取的内容的View
     * @return 控件内容
     */
    public static String getViewContent(View view) {
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        } else if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        }
        return "";
    }

    /**
     * 上传多文件参数构建
     *
     * @param files      文件列表
     * @param paramNames 文件名列表
     * @return 构建后文本参数
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files, List<String> paramNames) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());

        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData(paramNames.get(i), files.get(i).getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 参数构建
     *
     * @param param 文本参数构建
     * @return RequestBody
     */
    public static RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    /**
     * 替换手机号码中间4位
     * 括号表示组，被替换的部分$n表示第n组的内容
     *
     * @param number 手机号
     * @return 替换后的手机号
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
     * @param cardId 身份证号
     * @return 隐藏后的身份证号
     */
    @SuppressWarnings("Annotator")
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

    /**
     * //验证码倒计时
     *
     * @param tvGetPhoneCode 发送验证码控件
     */
    public static void theCountdownCode(TextView tvGetPhoneCode) {

        final int count = 60;

        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .map(aLong -> {
                    return count - aLong; //
                })
                .doOnSubscribe(() -> {
                    tvGetPhoneCode.setEnabled(false);//在发送数据的时候设置为不能点击#c02826
                    tvGetPhoneCode.setTextColor(Color.parseColor("#555555"));
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
                        String str = "获取验证码(" + aLong + ")";
                        tvGetPhoneCode.setText(str);
                    }
                });
    }

    /**
     * 4位分组,隐藏银行卡,(仅显示后面几位),不能不4整除的位数
     *
     * @param bankNumber 银行卡号
     * @return 隐藏后的银行卡号
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
        StringBuilder emptyString = new StringBuilder();

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
        int showLength;
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
     * @param number 测试数据数量
     * @return List<String>
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
     * @param editText   编辑
     * @param tip        显示1/200 控件
     * @param charMaxNum 最大允许的数量
     */
    public static void ediTextSize(EditText editText, TextView tip, int charMaxNum) {

        //EditText手动设置值处理
        String etString = editText.getText().toString();

        String tipString = etString.length() + "/" + charMaxNum;
        tip.setText(tipString);

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
                String tipString = s.length() + "/" + charMaxNum;
                tip.setText(tipString);
            }

            @Override
            public void afterTextChanged(Editable s) {
                /* 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
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
     * 文本输入内容,判断按钮是否可用
     *
     * @param editText 编辑框
     * @param view     按钮
     */
    public static void ediTextChanged(EditText editText, View view) {
        editText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    view.setEnabled(false);
                } else {
                    view.setEnabled(true);
                }
            }
        });
    }

    /**
     * 所有的输入框有输入,按钮可用
     *
     * @param view 按钮
     * @param args 输入框
     */
    public static void ediTextChanged(View view, EditText... args) {

        List<Boolean> list = new ArrayList<>();
        EditText editText;
        for (int i = 0; i < args.length; i++) {
            editText = args[i];

            String content = getViewContent(editText);
            if (TextUtils.isEmpty(content)) {
                list.add(false);
            } else {
                list.add(true);
            }
            editText.addTextChangedListener(new EditextTextWatcher(list, i, view));
        }

        boolean isNoEmpty = false;
        for (int j = 0; j < list.size(); j++) {
            if (!list.get(j)) {
                isNoEmpty = true;
            }
        }

        if (isNoEmpty) {
            view.setEnabled(false);
        } else {
            view.setEnabled(true);
        }
    }

    /**
     * Editext内容改变监听
     */
    public static class EditextTextWatcher extends MyTextWatcher {

        int i;
        List<Boolean> list;
        View view;

        private EditextTextWatcher(List<Boolean> list, int i, View view) {
            this.list = list;
            this.i = i;
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                list.set(i, false);
            } else {
                list.set(i, true);
            }

            boolean isNoEmpty = false;
            for (int j = 0; j < list.size(); j++) {
                if (!list.get(j)) {
                    isNoEmpty = true;
                }
            }

            if (isNoEmpty) {
                view.setEnabled(false);
            } else {
                view.setEnabled(true);
            }
        }
    }


    /**
     * 字符串转对应时间格式
     *
     * @param timeString 时间撮字符串 单位秒
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String time2String(String timeString) {
        long timeLong = string2Long(timeString);
        return TimeUtils.millis2String(timeLong, ConstantUtils.YMDHM_FORMAT);
    }

    /**
     * 判断虚拟按键是否存在
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @return
     */
    public static String getFileExt(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String result = filename.substring(index + 1);
        return result;
    }

    /**
     * 解码url
     *
     * @param content
     * @return
     */
    public static String urlDecode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
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
