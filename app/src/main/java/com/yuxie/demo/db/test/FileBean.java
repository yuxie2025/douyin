package com.yuxie.demo.db.test;

import com.yuxie.demo.db.annotion.DbTable;

/**
 * Created by Administrator on 2017/09/11.
 */
@DbTable("tb_file")
public class FileBean {
    public String time;
    public String path;
    public String decripte;

    public FileBean() {
    }

    public FileBean(String time, String path, String decripte) {
        this.time = time;
        this.path = path;
        this.decripte = decripte;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDecripte() {
        return decripte;
    }

    public void setDecripte(String decripte) {
        this.decripte = decripte;
    }
}
