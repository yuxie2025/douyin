package com.yuxie.myapp.http.interfaces;

import java.util.Map;

/**
 * Created by Administrator on 2017/09/11.
 *
 * 处理结果
 */

public interface IHttpService {

    /**
     * 设置url
     * @param url
     */
    void setUrl(String url);

    /**
     * 执行网络请求
     */
    void excute();

    /**
     * 设置处理接口
     * @param httpListener
     */
    void setHttpListener(IHttpListener httpListener);

    /**
     * 设置请求参数
     * @param requestData
     */
    void setRequestData(byte[] requestData);

    void pause();

    /**
     *
     * 以下的方法是 额外添加的
     * 获取请求头的map
     * @return
     */
    Map<String,String> getHttpHeadMap();

    boolean cancle();

    boolean isCancle();

    boolean isPause();
}
