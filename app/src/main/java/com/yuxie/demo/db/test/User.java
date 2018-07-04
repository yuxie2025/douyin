package com.yuxie.demo.db.test;

import com.yuxie.demo.db.annotion.DbFiled;
import com.yuxie.demo.db.annotion.DbTable;

/**
 * Created by Administrator on 2017/09/10.
 */
@DbTable("tb_user")
public class User {
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User( ) {
    }

    @DbFiled("name")
    public String name;

    @DbFiled("password")
    public String password;
}
