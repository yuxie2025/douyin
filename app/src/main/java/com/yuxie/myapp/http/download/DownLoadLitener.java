package com.yuxie.myapp.http.download;

import android.os.Handler;
import android.os.Looper;

import com.yuxie.myapp.http.download.interfaces.IDownLitener;
import com.yuxie.myapp.http.download.interfaces.IDownloadServiceCallable;
import com.yuxie.myapp.http.interfaces.IHttpService;

import org.apache.http.HttpEntity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 2017/09/14.
 */

public class DownLoadLitener implements IDownLitener {

    private DownloadItemInfo mDownloadItemInfo;
    private File mFile;
    private String url;
    private long breakPoint;
    private IDownloadServiceCallable mDownloadServiceCallable;

    private IHttpService mHttpService;
    private Handler mHandler=new Handler(Looper.getMainLooper());

    public DownLoadLitener(DownloadItemInfo mDownloadItemInfo, IDownloadServiceCallable mDownloadServiceCallable, IHttpService mHttpService) {
        this.mDownloadItemInfo = mDownloadItemInfo;
        this.mDownloadServiceCallable = mDownloadServiceCallable;
        this.mHttpService = mHttpService;
        this.mFile=new File(mDownloadItemInfo.getFilePath());
        this.breakPoint=mFile.length();
    }

    public DownLoadLitener(DownloadItemInfo downloadItemInfo) {
        this.mDownloadItemInfo = downloadItemInfo;
    }

    public void addHttpHeader(Map<String,String> headerMap) {
        long length=getFile().length();
        if(length>0L)
        {
            headerMap.put("RANGE","bytes="+length+"-");
        }
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream=null;

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        try {
            inputStream=httpEntity.getContent();
            long startTime=System.currentTimeMillis();
            //用于计算每秒多少k
            long speed = 0L;
            //花费时间
            long useTime = 0L;
            //下载的长度
            long getLen = 0L;
            //接受的长度
            long receiveLen = 0L;
            boolean bufferLen = false;
            //得到下载的长度
            long dataLength = httpEntity.getContentLength();
            //单位时间下载的字节数
            long calcSpeedLen = 0L;
            //总数
            long totalLength = this.breakPoint + dataLength;
            //更新数量
            this.receviceTotalLength(totalLength);
            //更新状态
            this.downloadStatusChange(DownloadStatus.downloading);

            byte[] buffer=new byte[1024];
            int count=0;
            long currentTime=System.currentTimeMillis();

            if (!makeDir(this.getFile().getParentFile())) {
                mDownloadServiceCallable.onDownloadError(mDownloadItemInfo,1,"创建文件夹失败");
                return;
            }

            fos = new FileOutputStream(this.getFile(), true);
            bos = new BufferedOutputStream(fos);
            int length = 1;
            while ((length = inputStream.read(buffer)) != -1) {
                if (this.getHttpService().isCancle()) {
                    mDownloadServiceCallable.onDownloadError(mDownloadItemInfo, 1, "用户取消了");
                    return;
                }

                if (this.getHttpService().isPause()) {
                    mDownloadServiceCallable.onDownloadError(mDownloadItemInfo, 2, "用户暂停了");
                    return;
                }

                bos.write(buffer, 0, length);
                getLen += (long) length;
                receiveLen += (long) length;
                calcSpeedLen += (long) length;
                ++count;
                if (receiveLen * 10L / totalLength >= 1L || count >= 5000) {
                    currentTime = System.currentTimeMillis();
                    useTime = currentTime - startTime;
                    startTime = currentTime;
                    speed = 1000L * calcSpeedLen / useTime;
                    count = 0;
                    calcSpeedLen = 0L;
                    receiveLen = 0L;
                    this.downloadLengthChange(this.breakPoint + getLen, totalLength, speed);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                if (inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(bos!=null){
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }


    }

    @Override
    public void onFail() {

    }

    @Override
    public void setHttpService(IHttpService httpService) {
        mHttpService=httpService;
    }

    @Override
    public void setCancleCallable() {

    }

    @Override
    public void setPuaseCallable() {

    }
    public IHttpService getHttpService() {
        return mHttpService;
    }

    public File getFile() {
        return mFile;
    }

    /**
     * 创建文件夹的操作
     * @param parentFile
     * @return
     */
    private boolean makeDir(File parentFile) {
        return parentFile.exists()&&!parentFile.isFile()
                ?parentFile.exists()&&parentFile.isDirectory():
                parentFile.mkdirs();
    }

    /**
     * 下载长度改变会掉
     * @param downlength
     * @param totalLength
     * @param speed
     */
    private void downloadLengthChange(final long downlength, final long totalLength, final long speed) {

        mDownloadItemInfo.setCurrentLength(downlength);
        if(mDownloadServiceCallable!=null)
        {
            DownloadItemInfo copyDownItenIfo=mDownloadItemInfo.copy();
            synchronized (this.mDownloadServiceCallable)
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadServiceCallable.onCurrentSizeChanged(mDownloadItemInfo,downlength/totalLength,speed);
                    }
                });
            }
        }
    }

    /**
     * 更改下载时的状态
     * @param downloading
     */
    private void downloadStatusChange(DownloadStatus downloading) {
        mDownloadItemInfo.setStatus(downloading);
        final DownloadItemInfo copyDownloadItemInfo=mDownloadItemInfo.copy();
        if(mDownloadServiceCallable!=null)
        {
            synchronized (this.mDownloadServiceCallable)
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadServiceCallable.onDownloadStatusChanged(copyDownloadItemInfo);
                    }
                });
            }
        }
    }


    /**
     * 回调  长度的变化
     * @param totalLength
     */
    private void receviceTotalLength(long totalLength){
        mDownloadItemInfo.setCurrentLength(totalLength);
        final DownloadItemInfo copyDownloadItemInfo=mDownloadItemInfo.copy();
        if(mDownloadServiceCallable!=null)
        {
            synchronized (mDownloadServiceCallable)
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadServiceCallable.onTotalLengthReceived(copyDownloadItemInfo);
                    }
                });
            }
        }
    }
}
