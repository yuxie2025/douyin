package com.yuxie.myapp.http;

import com.alibaba.fastjson.JSON;
import com.yuxie.myapp.http.interfaces.IHttpService;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/09/11.
 */

public class HttpTask<T> implements Runnable{

    private IHttpService mHttpService;

    public HttpTask(RequestHolder<T> requestHolder) {
        mHttpService=requestHolder.getmHttpService();
        mHttpService.setHttpListener(requestHolder.getmHttpListener());
        mHttpService.setUrl(requestHolder.getmUrl());
        T request=requestHolder.getmRequstInfo();
        String requestInfo= JSON.toJSONString(request);
        try {
            mHttpService.setRequestData(requestInfo.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        mHttpService.excute();
    }
}
