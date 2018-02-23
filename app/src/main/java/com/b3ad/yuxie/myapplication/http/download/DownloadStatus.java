package com.b3ad.yuxie.myapplication.http.download;

/**
 * Created by Administrator on 2017/09/14.
 */

public enum  DownloadStatus {
    waitting(0),starting(1),downloading(2),pause(3),finish(4),failed(5);

    private int value;

    DownloadStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
