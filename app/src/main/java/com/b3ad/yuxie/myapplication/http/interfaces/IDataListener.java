package com.b3ad.yuxie.myapplication.http.interfaces;

/**
 * Created by Administrator on 2017/09/11.
 */

public interface IDataListener<M> {

    void onSuccess(M m);

    void onFail();

}
