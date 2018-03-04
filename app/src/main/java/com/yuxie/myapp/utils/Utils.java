package com.yuxie.myapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;


/**
 * Created by Administrator on 2017/8/14.
 */

public class Utils {

    // 如需要小写则把ABCDEF改成小写
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static Context mContext;
    public static Utils instance;

    //没有第一次传入上下文的异常处理
    public Utils(){
        if (mContext==null){
            throw new UnsupportedOperationException("utils no inits");
        }
    }
    //单例模式双从判断
    public static Utils getInstance() {
        if (instance==null){
            synchronized (Utils.class){
                if (instance==null){
                    instance=new Utils();
                }
            }
        }
        return instance;
    }
    //初始化工具类,传入application的上下文仅需一次
    public Utils (Context mContext){
        this.mContext=mContext;
    }

    public static String getSignature(){
        String result="";
        String packageName=mContext.getPackageName();
        try {
            //PackageManager.GET_SIGNATURES获取签名信息
            PackageInfo packageInfo=mContext.getPackageManager().getPackageInfo(packageName,PackageManager.GET_SIGNATURES);
            Signature[] signatures=packageInfo.signatures;
            StringBuffer sb=new StringBuffer();
            for (Signature signature:signatures) {
                sb.append(signature.toCharsString());
                Log.i("TAG","signature.toCharsString():"+signature.toCharsString());
            }
            result=sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("TAG","result:"+result);
        return result;
    }

    public static Signature[] getSignature(String packageName){
        Signature[] signatures=null;
        try {
            //PackageManager.GET_SIGNATURES获取签名信息
            PackageInfo packageInfo=mContext.getPackageManager().getPackageInfo(packageName,PackageManager.GET_SIGNATURES);
            signatures=packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return signatures;
    }

    /**
     * 进行转换
     */
    public static String toHexString(byte[] bData) {
        StringBuilder sb = new StringBuilder(bData.length * 2);
        for (int i = 0; i < bData.length; i++) {
            sb.append(HEX_DIGITS[(bData[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bData[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 返回MD5
     */
    public static String signatureMD5(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA1
     */
    public static String signatureSHA1(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA256
     */
    public static String signatureSHA256(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * get请求
     * @param uri
     * @return
     */
    public static String getDataNet(String uri) {
        String result = null;
        HttpURLConnection conn=null;
        try {
            URL url = new URL(uri);
            // 2)根据url,获得HttpURLConnection 连接工具
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("contentType", "utf-8");
            // 3)很据连接工具，获得响应码
            int responseCode = conn.getResponseCode();
            InputStream inStream = conn.getInputStream();
            // 4)响应码正确，即可获取、流、文件长度...
            if (responseCode == 200) {
                result = streamToString(inStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (conn!=null) {
                conn.disconnect();
                conn=null;
            }
        }
        return result;
    }

    /**
     * 流转换为字符串
     * @param in
     * @return
     * @throws IOException
     */
    public static String streamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        BufferedReader br=new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            out.append(line);
        }
        br.close();
        return out.toString();
    }
    /**
     * 流转换为字符串
     * @param in
     * @return
     * @throws IOException
     */
    public static String inputStreamToStringGbk(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        BufferedReader br=new BufferedReader(new InputStreamReader(in,"GBK"));
        String line = null;
        while ((line = br.readLine()) != null) {
            out.append(line);
        }
        br.close();
        return out.toString();
    }
}
