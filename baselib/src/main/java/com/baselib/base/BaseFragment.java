package com.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.baserx.RxManager;
import com.baselib.commonutils.TUtil;
import com.baselib.uitls.PermissionDialogUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者: llk on 2017/9/19.
 */
@SuppressWarnings("unused")
public abstract class BaseFragment
        <T extends BasePresenter, E extends BaseModel>
        extends Fragment {
    public View rootView;
    protected T mPresenter;
    public E mModel;
    protected RxManager mRxManager;

    protected Context mContext;

    /**
     * 分页,第几页
     */
    protected int page = 1;
    /**
     * 分页,每页页数
     */
    protected int pageSize = 10;

    Unbinder unbinder;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }
        mContext = getActivity();
        mRxManager = new RxManager();
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
        if (mPresenter != null) {
            mPresenter.inject(getActivity(), this, mModel);
        }
        initView(savedInstanceState);
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置title
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        try {
            ((TextView) rootView.findViewById(R.id.title)).setText(title);
            rootView.findViewById(R.id.rl_left).setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {
        }
    }

    /**
     * 子类要改变返回按钮的行为，需要从写该方法
     */
    protected void back() {
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().finish();
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(@NonNull final Class<? extends Class> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(@NonNull final Class<? extends Class> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(@NonNull final Class<? extends Class> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(@NonNull final Class<? extends Class> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 必须权限设置
     *
     * @param message 提示内容
     */
    protected void mustSetting(String message) {
        PermissionDialogUtils.mustSetting(getActivity(), message);
    }

    /**
     * 建议权限设置
     *
     * @param message 提示内容
     */
    protected void suggestSetting(String message) {
        PermissionDialogUtils.suggestSetting(getActivity(), message);
    }

    /**
     * 吐司
     *
     * @param msg 吐司内容
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
        if (mRxManager != null) {
            mRxManager.clear();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
