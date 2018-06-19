package com.yuxie.demo.http.interfaces;

import org.apache.http.HttpEntity;

/**
 * Created by Administrator on 2017/09/11.
 */

public interface IHttpListener {

    void onSuccess(HttpEntity httpEntity);

    void onFail();

}
