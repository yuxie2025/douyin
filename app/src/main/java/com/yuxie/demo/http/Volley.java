package com.yuxie.demo.http;

import com.yuxie.demo.http.interfaces.IDataListener;
import com.yuxie.demo.http.interfaces.IHttpListener;
import com.yuxie.demo.http.interfaces.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/09/11.
 */

public class Volley {
    /**
     *
     * @param <T> 请求参数类型
     * @param <M> 想要参数类型
     */
    public static <T,M> void sendRequest(T requestInfo , String url, Class<M> response, IDataListener dataListener){

        RequestHolder<T> requestHolder=new RequestHolder<>();
        requestHolder.setmUrl(url);
        IHttpService httpService=new JsonDealService();
        IHttpListener httpListener=new JsonDealLitener<>(response,dataListener);
        requestHolder.setmHttpListener(httpListener);
        requestHolder.setmHttpService(httpService);

        HttpTask<T> httpTask=new HttpTask<>(requestHolder);

        try {
            ThreadPoolManager.getInstance().execte(new FutureTask<Object>(httpTask,null){});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
