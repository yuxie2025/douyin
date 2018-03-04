package com.yuxie.myapp.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.yuxie.myapp.http.interfaces.IDataListener;
import com.yuxie.myapp.http.interfaces.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/09/11.
 */

public class JsonDealLitener<M> implements IHttpListener {

    private Class<M> mResponese;

    private IDataListener<M> mDataListener;

    private Handler mHandler=new Handler(Looper.getMainLooper());

    public JsonDealLitener(Class<M> mResponese, IDataListener<M> mDataListener) {
        this.mResponese = mResponese;
        this.mDataListener = mDataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream=null;
        try {
            inputStream=httpEntity.getContent();
            String content=getContent(inputStream);
            final M m= JSON.parseObject(content,mResponese);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mDataListener.onSuccess(m);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail() {
        mDataListener.onFail();
    }

    public String getContent(InputStream in){
        StringBuffer out = null;
        try {
            out = new StringBuffer();
            BufferedReader br=new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                out.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
