package com.baselib.base;

import android.content.Context;

import com.baselib.baserx.RxManager;

/**
 * Created by llk on 2017/9/5.
 */
@SuppressWarnings("unused")
public abstract class BasePresenter<T, E> {
    private Context mContext;
    private E mModel;
    private T mView;
    private RxManager mRxManage = new RxManager();


    public void inject(Context context, T view, E model) {
        mContext = context;
        mView = view;
        mModel = model;
        onStart();
    }

    public void inject(T view, E model) {
        mView = view;
        mModel = model;
        onStart();
    }

    public Context getContext() {
        return mContext;
    }

    public E getModel() {
        return mModel;
    }

    public T getView() {
        return mView;
    }

    public RxManager getRxManage() {
        return mRxManage;
    }

    public void onStart() {
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
