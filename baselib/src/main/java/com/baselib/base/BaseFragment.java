package com.baselib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselib.baserx.RxManager;
import com.baselib.commonutils.TUtil;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: llk on 2017/9/19.
 */

public abstract class BaseFragment
        <T extends BasePresenter, E extends BaseModel>
        extends Fragment {
    public View rootView;
    protected T mPresenter;
    public E mModel;
    protected RxManager mRxManager;

    protected Context mContext;

    //分页,第几页
    protected int page = 1;
    protected int pageSize = 10;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mContext = getActivity();
        mRxManager = new RxManager();
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        initPresenter();
        initView(savedInstanceState);
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    protected void initPresenter() {
        if (mPresenter != null) {
            mPresenter.inject(getActivity(), this, mModel);
        }
    }

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 吐司
     *
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mRxManager.clear();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
