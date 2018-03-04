package com.yuxie.myapp.http.download.interfaces;

import com.yuxie.myapp.http.interfaces.IHttpListener;
import com.yuxie.myapp.http.interfaces.IHttpService;

/**
 * Created by Administrator on 2017/09/14.
 */

public interface IDownLitener extends IHttpListener {
    void setHttpService(IHttpService httpService);

    void setCancleCallable();

    void  setPuaseCallable();
}
