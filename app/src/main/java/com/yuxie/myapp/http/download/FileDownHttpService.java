package com.yuxie.myapp.http.download;

import android.util.Log;

import com.yuxie.myapp.http.interfaces.IHttpListener;
import com.yuxie.myapp.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/09/14.
 */

public class FileDownHttpService implements IHttpService {
    /**
     * 添加到请求头的信息
     */
    private Map<String,String> headerMap= Collections.synchronizedMap(new HashMap<String, String>());
    /**
     * 含有请求处理的 接口
     */
    private IHttpListener mHttpListener;
    private HttpClient mHttpClient=new DefaultHttpClient();
    private HttpGet mHttpGet;
    private String mUrl;

    private byte[] mRequestData;

    /**
     * httpClient获取网络的回调
     */
    private HttpResponseHandler mHttpResponseHandler=new HttpResponseHandler();

    @Override
    public void setUrl(String url) {
        mUrl=url;
    }

    @Override
    public void excute() {
        mHttpGet=new HttpGet();
        constrcutHeader();

        try {
            mHttpClient.execute(mHttpGet,mHttpResponseHandler);
        } catch (IOException e) {
            e.printStackTrace();
         }
    }


    private void constrcutHeader(){
        Iterator iterator=headerMap.keySet().iterator();
        while (iterator.hasNext()){
            String key= (String) iterator.next();
            String value=headerMap.get(key);
            Log.i("TAG"," 请求头信息  "+key+"  value "+value);
            mHttpGet.addHeader(key,value);
        }
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        mHttpListener=httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        mRequestData=requestData;
    }

    private class HttpResponseHandler extends BasicResponseHandler{
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int code=response.getStatusLine().getStatusCode();
            if (code==200){
                mHttpListener.onSuccess(response.getEntity());
            }else{
                mHttpListener.onFail();
            }
            return null;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public Map<String, String> getHttpHeadMap() {
        return null;
    }

    @Override
    public boolean cancle() {
        return false;
    }

    @Override
    public boolean isCancle() {
        return false;
    }

    @Override
    public boolean isPause() {
        return false;
    }
}
