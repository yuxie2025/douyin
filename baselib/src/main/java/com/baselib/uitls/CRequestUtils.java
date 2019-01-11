package com.baselib.uitls;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qqs on 2018/2/2.
 */
@SuppressWarnings("unused")
public class CRequestUtils {
    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL) {
        String strPage = null;

        strURL = strURL.trim().toLowerCase();

        String[] arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }

        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit;

        strURL = strURL.trim().toLowerCase();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<>();

        String[] arrSplit;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (!"".equals(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static Map<String, String> URLRequestParameter(String URLParameter) {
        Map<String, String> mapRequest = new HashMap<>();

        String[] arrSplit;

        if (TextUtils.isEmpty(URLParameter)) {
            return mapRequest;
        }

        //只有一个参数时
//        if (URLParameter.contains("=") && !URLParameter.contains("&")) {
//            String[] arrSplitEqual = arrSplitEqual = URLParameter.split("[=]");
//            //解析出键值
//            if (arrSplitEqual.length > 1) {
//                //正确解析
//                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
//
//            } else {
//                if (arrSplitEqual[0] != "") {
//                    //只有参数没有值，不加入
//                    mapRequest.put(arrSplitEqual[0], "");
//                }
//            }
//            return mapRequest;
//        }


        //每个键值为一组 www.2cto.com
        arrSplit = URLParameter.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (!"".equals(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

}
