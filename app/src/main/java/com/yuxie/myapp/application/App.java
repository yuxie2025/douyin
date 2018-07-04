package com.yuxie.myapp.application;

import android.content.Context;
import android.os.Environment;

import com.baselib.baseapp.BaseApplication;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.yuxie.myapp.R;
//import com.okhttplib.OkHttpUtil;
//import com.okhttplib.annotation.CacheType;
//import com.okhttplib.annotation.Encoding;
//import com.okhttplib.cookie.PersistentCookieJar;
//import com.okhttplib.cookie.cache.SetCookieCache;
//import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;

import java.io.File;

/**
 * Created by Administrator on 2017/7/13.
 */

public class App extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //初始化工具类
//        Utils utils=new Utils(getApplicationContext());

        //配置okhttp3
        initOkhttp3();

        // 应用程序入口处调用,避免手机内存过小,杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用“,”分隔。
        // 设置你申请的应用appid

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        StringBuffer param = new StringBuffer();
        param.append("appid=" + getString(R.string.app_id));
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(this, param.toString());


    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化okhttp3,工具类配置
     */
    public void initOkhttp3() {
        String downloadFileDir = Environment.getExternalStorageDirectory().getPath() + "/okHttp_download/";
        String cacheDir = Environment.getExternalStorageDirectory().getPath();
        if (getExternalCacheDir() != null) {
            //缓存目录，APP卸载后会自动删除缓存数据
            cacheDir = getExternalCacheDir().getPath();
        }
//        OkHttpUtil.init(this)
//                .setConnectTimeout(15)//连接超时时间
//                .setWriteTimeout(15)//写超时时间
//                .setReadTimeout(15)//读超时时间
//                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
//                .setCacheType(CacheType.FORCE_NETWORK)//缓存类型
//                .setHttpLogTAG("HttpLog")//设置请求日志标识
//                .setIsGzip(false)//Gzip压缩，需要服务端支持
//                .setShowHttpLog(true)//显示请求日志
//                .setShowLifecycleLog(false)//显示Activity销毁日志
//                .setRetryOnConnectionFailure(false)//失败后不自动重连
//                .setCachedDir(new File(cacheDir, "okHttp_cache"))//缓存目录
//                .setDownloadFileDir(downloadFileDir)//文件下载保存目录
//                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
//                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
////                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
////                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
//                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
//                .build();
    }

}
