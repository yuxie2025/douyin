package com.yuxie.myapp.utils.db;


import com.yuxie.myapp.greendao.SmsApiDao;
import com.yuxie.myapp.greendao.UserDao;

/**
 * Created by luo on 2018/3/1.
 */

public class EntityManager {

    private static EntityManager entityManager;

    /**
     * 创建User表实例
     *
     * @return
     */
    public UserDao getUserDao() {
        return DaoManager.getInstance().getSession().getUserDao();
    }

    public SmsApiDao getSmsApiDao() {
        return DaoManager.getInstance().getSession().getSmsApiDao();
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}
