package com.yuxie.demo.db.test;

import com.yuxie.demo.db.BaseDao;

/**
 * Created by Administrator on 2017/09/11.
 */

public class UserDao extends BaseDao {

    @Override
    protected String createTable() {
        return "create table if not exists tb_user(time varchar(20),path varchar(20))";
    }
}
