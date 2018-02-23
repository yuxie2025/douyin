package com.b3ad.yuxie.myapplication.http;

import com.b3ad.yuxie.myapplication.http.JsonDealLitener;
import com.b3ad.yuxie.myapplication.http.interfaces.IHttpListener;
import com.b3ad.yuxie.myapplication.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Map;


/**
 * Created by Administrator on 2017/09/11.
 */

public class JsonDealService implements IHttpService {

    private IHttpListener mHttpListener;
    private HttpClient mHttpClient=new DefaultHttpClient();
    private HttpPost mHttpPost;
    private byte[] mRequestDate;
    private String mUrl;

    private HttpRespnceHandler mHttpRespnceHandler;

    @Override
    public void setUrl(String url) {
        mUrl=url;
    }

    @Override
    public void excute() {
        try {
            mHttpPost=new HttpPost(mUrl);
            ByteArrayEntity byteArrayEntity=new ByteArrayEntity(mRequestDate);
            mHttpPost.setEntity(byteArrayEntity);
            mHttpRespnceHandler=new HttpRespnceHandler();
            mHttpClient.execute(mHttpPost,mHttpRespnceHandler);
        } catch (IOException e) {
            e.printStackTrace();
            mHttpListener.onFail();
        }
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.mHttpListener=httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        mRequestDate=requestData;
    }

    private class HttpRespnceHandler extends BasicResponseHandler{
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
