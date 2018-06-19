package com.yuxie.demo.http.download;

import android.os.Environment;
import android.util.Log;

import com.yuxie.demo.http.HttpTask;
import com.yuxie.demo.http.RequestHolder;
import com.yuxie.demo.http.ThreadPoolManager;
import com.yuxie.demo.http.download.interfaces.IDownloadServiceCallable;
import com.yuxie.demo.http.interfaces.IHttpListener;
import com.yuxie.demo.http.interfaces.IHttpService;

import java.io.File;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/09/14.
 */

public class DownFileManager implements IDownloadServiceCallable {

    private byte[] lock=new byte[0];

    public void down(String url){

        synchronized (lock)
        {
            String[] preFixs=url.split("/");
            String afterFix=preFixs[preFixs.length-1];

            File file=new File(Environment.getExternalStorageDirectory(),afterFix);
            //实例化DownloadItem
            DownloadItemInfo downloadItemInfo=new DownloadItemInfo(url,file.getAbsolutePath());

            RequestHolder requestHolder=new RequestHolder();
            //设置请求下载的策略
            IHttpService httpService=new FileDownHttpService();
            //得到请求头的参数 map
            Map<String,String> map=httpService.getHttpHeadMap();
            /**
             * 处理结果的策略
             */
            IHttpListener httpListener=new DownLoadLitener(downloadItemInfo,this,httpService);

            requestHolder.setmHttpListener(httpListener);
            requestHolder.setmHttpService(httpService);
            requestHolder.setmUrl(url);
            HttpTask httpTask=new HttpTask(requestHolder);
            try {
                ThreadPoolManager.getInstance().execte(new FutureTask<Object>(httpTask,null));
            } catch (InterruptedException e) {

            }

        }

    }

    @Override
    public void onDownloadStatusChanged(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onTotalLengthReceived(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onCurrentSizeChanged(DownloadItemInfo downloadItemInfo, double downLenth, long speed) {
        Log.i("TAG","下载速度："+ speed/1000 +"k/s");
        Log.i("TAG","-----路径  "+ downloadItemInfo.getFilePath()+"  下载长度  "+downLenth+"   速度  "+speed);
    }

    @Override
    public void onDownloadSuccess(DownloadItemInfo downloadItemInfo) {
        Log.i("TAG","下载成功    路劲  "+ downloadItemInfo.getFilePath()+"  url "+ downloadItemInfo.getUrl());
    }

    @Override
    public void onDownloadPause(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onDownloadError(DownloadItemInfo downloadItemInfo, int code, String error) {

    }
}
