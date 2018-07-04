package com.yuxie.demo.txt;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/08/27.
 */

public class TxtDir implements Comparable{

    private String title;
    private String titleDir;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleDir() {
        return titleDir;
    }

    public void setTitleDir(String titleDir) {
        this.titleDir = titleDir;
    }

    @Override
    public String toString() {
        return "TxtDir{" +
                "title='" + title + '\'' +
                ", titleDir='" + titleDir + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
