package com.yuxie.demo.api.server;

/**
 * 作者: llk on 2017/9/8.
 */

public class HostType {

    public final static int HOST_COUNT = 2;
    public final static int HOST_TYPE_COMMON_FLAG = 100;
    public final static int HOST_TYPE_WEIXIN_FLAG = 103;
    public final static int HOST_TYPE_BAIDU_FLAG = 104;
    public final static int HOST_TYPE_ALIPAY_FLAG = 105;
    public final static int HOST_TYPE_SY_FLAG = 105;
    public final static int HOST_TYPE_TEST_FLAG = 999999;


    public final static String HOST_TYPE_TEST = "http://49.4.70.94:8080";
    public final static String HOST_TYPE_BAIDU = "https://aip.baidubce.com";
    public final static String HOST_TYPE_ALIPAY = "http://ccdcapi.alipay.com";
    public final static String HOST_TYPE_SY = "http://10.10.10.224:8088";
    /**
     * 项目名称
     */
    public final static String ITEM = "";
    public final static String HOST_TYPE_WEIXIN = "https://api.weixin.qq.com/sns/";
    public final static String HOST_TYPE_COMMON = "https://yuxie2025.github.io/";//正式环境

    public static String getHost(int hosttype) {
        switch (hosttype) {
            case HOST_TYPE_COMMON_FLAG://网络框架地址
                return HOST_TYPE_COMMON;
            case HOST_TYPE_WEIXIN_FLAG://微信地址
                return HOST_TYPE_WEIXIN;
            case HOST_TYPE_BAIDU_FLAG://百度地图地址
                return HOST_TYPE_BAIDU;
            case HOST_TYPE_ALIPAY_FLAG://支付宝地址
                return HOST_TYPE_ALIPAY;
            case HOST_TYPE_TEST_FLAG://测试地址
                return HOST_TYPE_TEST;
        }
        return null;
    }

}
