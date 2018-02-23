package com.b3ad.yuxie.myapplication.http.download.interfaces;

import com.b3ad.yuxie.myapplication.http.interfaces.IHttpListener;
import com.b3ad.yuxie.myapplication.http.interfaces.IHttpService;

/**
 * Created by Administrator on 2017/09/14.
 */

public interface IDownLitener extends IHttpListener {
    void setHttpService(IHttpService httpService);

    void setCancleCallable();

    void  setPuaseCallable();
}
