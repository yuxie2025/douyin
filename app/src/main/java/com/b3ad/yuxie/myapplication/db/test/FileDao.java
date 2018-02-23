package com.b3ad.yuxie.myapplication.db.test;

import android.os.Parcel;
import android.os.Parcelable;

import com.b3ad.yuxie.myapplication.db.BaseDao;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/09/11.
 */

public class FileDao<T> extends BaseDao<T> implements Parcelable{

    protected FileDao(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FileDao> CREATOR = new Creator<FileDao>() {
        @Override
        public FileDao createFromParcel(Parcel in) {
            return new FileDao(in);
        }

        @Override
        public FileDao[] newArray(int size) {
            return new FileDao[size];
        }
    };

    @Override
    protected String createTable() {
        return "create table if not exists tb_file(time varchar(20),path varchar(10),decripte varchar(100))";
    }
}
