package com.yuxie.demo.utils.db;


import com.yuxie.demo.entity.UrlLibraryBean;
import com.yuxie.demo.greendao.LikeReBeanDao;
import com.yuxie.demo.greendao.SmsApiDao;
import com.yuxie.demo.greendao.UrlLibraryBeanDao;
import com.yuxie.demo.greendao.UserBeanDao;
import com.yuxie.demo.greendao.UserDao;

/**
 * Created by luo on 2018/3/1.
 */

public class EntityManager {

    private static EntityManager entityManager;

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

    public UserBeanDao getUserBeanDao() {
        return DaoManager.getInstance().getSession().getUserBeanDao();
    }

    public LikeReBeanDao getLikeReBeanDao() {
        return DaoManager.getInstance().getSession().getLikeReBeanDao();
    }

    public UrlLibraryBeanDao getUrlLibraryBeanDao() {
        return DaoManager.getInstance().getSession().getUrlLibraryBeanDao();
    }
}
