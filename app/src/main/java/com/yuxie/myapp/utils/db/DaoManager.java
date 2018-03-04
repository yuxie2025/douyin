package com.yuxie.myapp.utils.db;

import com.yuxie.myapp.application.App;
import com.yuxie.myapp.greendao.DaoMaster;
import com.yuxie.myapp.greendao.DaoSession;

/**
 * Created by luo on 2018/3/1.
 */

public class DaoManager {
    private static DaoManager mInstance;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;

    private DaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getContext(), "my_db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        this.mDaoMaster = mDaoMaster;
    }

    public static DaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new DaoManager();
        }
        return mInstance;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }
}
