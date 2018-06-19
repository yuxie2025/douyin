package com.yuxie.demo.http.download.interfaces;

import com.yuxie.demo.http.interfaces.IHttpListener;
import com.yuxie.demo.http.interfaces.IHttpService;

/**
 * Created by Administrator on 2017/09/14.
 */

public interface IDownLitener extends IHttpListener {
    void setHttpService(IHttpService httpService);

    void setCancleCallable();

    void  setPuaseCallable();
}
