package com.yuxie.myapp.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by Administrator on 2017/09/10.
 */

public class BaseDaoFactory {

    private String mSqliteDatebasePath;//数据库路径
    private SQLiteDatabase mSqliteDatebase;//数据库

    private static BaseDaoFactory install;

    public static BaseDaoFactory getInstall(){
        if (install==null){
           synchronized (BaseDaoFactory.class){
               if (install==null){
                   install=new BaseDaoFactory();
               }
           }
        }
        return install;
    }

    public BaseDaoFactory(){
        mSqliteDatebasePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/user.db";
        openDatabase();
    }

    public  synchronized <T extends BaseDao<M>,M> T getDataHelper(Class<T> clazz, Class<M> entityClass){
        BaseDao baseDao=null;
        try {
            baseDao=clazz.newInstance();
            baseDao.init(entityClass,mSqliteDatebase);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    /**
     * 打开或创建数据库
     */
    private void openDatabase(){
        mSqliteDatebase=SQLiteDatabase.openOrCreateDatabase(mSqliteDatebasePath,null);
    }
}
