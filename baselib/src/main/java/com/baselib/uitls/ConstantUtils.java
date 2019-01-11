package com.baselib.uitls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressWarnings("unused")
public class ConstantUtils {

    /**
     * 日期正则(2011/10/11)
     */
    public static final String DATA_REGEX = "^\\d{4}/\\d{1,2}/\\d{1,2}";

    /**
     * 正则:密码(字母数字下划线)
     */
    public static final String REGEX_PASSWORD = "^\\w{6,16}$";
    /**
     * 正则:车牌号
     */
    public static final String REGEX_PLATE_NUMBER = "(^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$)|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF][A-HJ-NP-Z0-9][0-9]{4})))";

    /**
     * 年月日时分秒
     */
    public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    /**
     * 年月日时分
     */
    public static final DateFormat YMDHM_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    /**
     * 年月日
     */
    public static final DateFormat YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * 年月日时分秒(文件名)
     */
    public static final DateFormat DATE_FILE = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss", Locale.getDefault());
}
