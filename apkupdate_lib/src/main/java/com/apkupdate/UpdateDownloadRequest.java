package com.apkupdate;

/**
 * Created by luo on 2018/3/6.
 */


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.apkupdate.utils.DownloadUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateDownloadRequest implements Runnable {

    private String downloadUrl;
    private String localFilePath;
    private UpdateDownloadListener listener;
    private boolean isDownloading = false;

    private DownloadResponseHandler downloadResponseHandler;

    public UpdateDownloadRequest(String downloadUrl, String localFilePath, UpdateDownloadListener listener) {

        this.downloadUrl = downloadUrl;
        this.localFilePath = localFilePath;
        this.listener = listener;
        this.isDownloading = true;
        downloadResponseHandler = new DownloadResponseHandler();

    }

    private String getTwoPointFloatStr(float value) {
        DecimalFormat df = new DecimalFormat("0.00000000000");
        return df.format(value);

    }

    private void makeRequest() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            download(downloadUrl);
        }
    }

    private void download(String mUrl) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downloadResponseHandler.sendFailureMessage(FailureCode.IO);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int responseCode = response.code();
                if (responseCode != 200) {
                    downloadResponseHandler.sendFailureMessage(FailureCode.SocketTimeout);
                    return;
                }
                long currentLength = response.body().contentLength();
                InputStream inStream = response.body().byteStream();
                if (!Thread.currentThread().isInterrupted()) {
                    downloadResponseHandler.sendResponseMessage(inStream, currentLength);
                }
            }
        };
        DownloadUtils.download(mUrl, callback);
    }

    @Override
    public void run() {
        try {
            makeRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载过程中的异常
     */
    public enum FailureCode {
        UnknownHost, Socket, SocketTimeout, connectionTimeout, IO, HttpResponse,
        Json, Interrupted

    }

    public class DownloadResponseHandler {

        protected static final int SUCCESS_MESSAGE = 0;
        protected static final int FAILURE_MESSAGE = 1;
        protected static final int START_MESSAGE = 2;
        protected static final int FINISH_MESSAGE = 3;
        protected static final int NETWORK_OFF = 4;
        private static final int PROGRESS_CHANGED = 5;

        private float completeSize = 0;
        private int progress = 0;

        private Handler handler;

        public DownloadResponseHandler() {

            handler = new Handler(Looper.getMainLooper()) {

                @Override
                public void handleMessage(Message msg) {
                    handleSelfMessage(msg);
                }
            };

        }


        protected void sendFinishMessage() {
            sendMessage(obtainMessage(FINISH_MESSAGE, null));
        }

        private void sendProgressChangedMessage(int progress) {
            sendMessage(obtainMessage(PROGRESS_CHANGED, new Object[]{progress}));

        }

        protected void sendFailureMessage(FailureCode failureCode) {
            sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{failureCode}));

        }

        protected void sendMessage(Message msg) {
            if (handler != null) {
                handler.sendMessage(msg);
            } else {
                handleSelfMessage(msg);
            }

        }

        protected Message obtainMessage(int responseMessge, Object response) {
            Message msg = null;
            if (handler != null) {
                msg = handler.obtainMessage(responseMessge, response);
            } else {
                msg = Message.obtain();
                msg.what = responseMessge;
                msg.obj = response;
            }
            return msg;

        }

        protected void handleSelfMessage(Message msg) {

            Object[] response;
            switch (msg.what) {
                case FAILURE_MESSAGE:
                    response = (Object[]) msg.obj;
                    sendFailureMessage((FailureCode) response[0]);
                    break;
                case PROGRESS_CHANGED:
                    response = (Object[]) msg.obj;
                    handleProgressChangedMessage(((Integer) response[0]).intValue());
                    break;
                case FINISH_MESSAGE:
                    onFinish();
                    break;
            }
        }

        protected void handleProgressChangedMessage(int progress) {
            listener.onProgressChanged(progress, downloadUrl);
        }

        protected void onFinish() {
            listener.onFinished(completeSize, "");

        }

        private void handleFailureMessage(FailureCode failureCode) {
            onFailure(failureCode);

        }

        protected void onFailure(FailureCode failureCode) {

            listener.onFailure();
        }

        void sendResponseMessage(InputStream is, long currentLength) {

            File file = new File(localFilePath);
            //如果文件存在且长度相等,则已经下载过了直接结束
            if (file.exists() && file.length() == currentLength) {
                sendFinishMessage();
                return;
            } else {
                file.delete();
            }

            RandomAccessFile randomAccessFile = null;
            completeSize = 0;
            try {
                byte[] buffer = new byte[1024];
                int length = -1;//读写长度
                int limit = 0;
                randomAccessFile = new RandomAccessFile(localFilePath, "rwd");
                randomAccessFile.setLength(currentLength);

                while ((length = is.read(buffer)) != -1) {
                    if (isDownloading) {
                        randomAccessFile.write(buffer, 0, length);
                        completeSize += length;
                        if (completeSize < currentLength) {
                            progress = (int) (Float.parseFloat(getTwoPointFloatStr(completeSize / currentLength)) * 100);
                            if (limit % 30 == 0 && progress <= 100) {//隔30次更新一次notification
                                sendProgressChangedMessage(progress);
                            }
                            limit++;
                        }
                    }
                }
                sendFinishMessage();
            } catch (IOException e) {
                sendFailureMessage(FailureCode.IO);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    sendFailureMessage(FailureCode.IO);
                }
            }
        }
    }
}
