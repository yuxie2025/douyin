package com.b3ad.yuxie.myapplication.db.test;

import com.b3ad.yuxie.myapplication.db.BaseDao;

/**
 * Created by Administrator on 2017/09/11.
 */

public class UserDao extends BaseDao {

    @Override
    protected String createTable() {
        return "create table if not exists tb_user(time varchar(20),path varchar(20))";
    }
}
