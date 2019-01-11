package com.baselib.basebean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luo on 2017/12/20.
 */

@SuppressWarnings("ALL")
public class WxBean {


    /**
     * package : Sign=WXPay
     * appid : wx4829dd54efe9c7d3
     * sign : 2EBAE2C987BAE22A0AFD39A226DFEC1E
     * prepayid : wx201711212042306e51956c240542859316
     * partnerid : 1482712842
     * noncestr : hMTdNO0lcmxX0tbR
     * timestamp : 1511268152
     */

    //package是关键字
    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String prepayid;
    private String partnerid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WxBean{" +
                "packageX='" + packageX + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
