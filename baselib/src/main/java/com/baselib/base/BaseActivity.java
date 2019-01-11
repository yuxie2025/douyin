package com.baselib.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baselib.baserx.RxManager;
import com.baselib.ui.widget.LoadingDialog;
import com.baselib.uitls.PermissionDialogUtils;
import com.blankj.utilcode.util.ToastUtils;

import android.view.View;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.baseapp.AppManager;
import com.baselib.commonutils.TUtil;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * 基类
 * Created by luo on 2017/9/5.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends SwipeBackActivity {
    protected T mPresenter;
    protected E mModel;
    protected Context mContext;
    protected RxManager mRxManager;
    protected Subscription subscription;

    private Unbinder unbinder;

    /**
     * 分页,第几页
     */
    protected int page = 1;
    /**
     * 分页,每页页数
     */
    protected int pageSize = 10;
    /**
     * 是否着色状态栏
     */
    protected boolean isSetStatusBarColor = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mRxManager = new RxManager();
        onBeforeSetContentView();

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        unbinder = ButterKnife.bind(this);

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
        if (mPresenter != null) {
            mPresenter.inject(this, this, mModel);
        }

        initView(savedInstanceState);

        //绑第二次是为了,兼容DataBindingUtil,否则点击事件将无效
        unbinder = ButterKnife.bind(this);

        //销毁当前页面
        goBack();

        if (isSetStatusBarColor) {
            // 默认着色状态栏
            setStatusBarColor();
        }
    }


    /**
     * 如果有返回按钮,设置监听
     */
    protected void goBack() {
        try {
            findViewById(R.id.rl_left).setOnClickListener(view -> back());
        } catch (Exception ignored) {
        }
    }

    /**
     * 子类要改变返回按钮的行为，需要从写该方法
     */
    protected void back() {
        finish();
    }

    /**
     * 设置title
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        try {
            ((TextView) findViewById(R.id.title)).setText(title);
        } catch (Exception ignored) {
        }
    }

    /**
     * 设置右上角保存
     *
     * @param save            文字
     * @param res             颜色资源
     * @param onClickListener 点击监听
     */
    protected void setSave(String save, @ColorInt int res, View.OnClickListener onClickListener) {
        try {
            TextView saveText = findViewById(R.id.save);
            saveText.setVisibility(View.VISIBLE);
            if (res == 0) {
                saveText.setTextColor(Color.parseColor("#1c1c1c"));
            } else {
                saveText.setTextColor(res);
            }
            saveText.setText(save);
            saveText.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 吐司
     *
     * @param msg 吐司内容
     */
    protected void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    //获取布局文件
    protected abstract int getLayoutId();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置layout前配置
     */
    protected void onBeforeSetContentView() {

        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置全屏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 设置状态栏的颜色和透明度（4.4以上系统有效）
     */
    protected void setStatusBarColor() {
        int mStatusBarColor = getResources().getColor(R.color.status_bar_color);
        StatusBarUtil.setColorForSwipeBack(this, mStatusBarColor, 0);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(@NonNull final Class<? extends Class> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(@NonNull final Class<? extends Class> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 必须权限设置
     *
     * @param message 提示内容
     */
    protected void mustSetting(String message) {
        PermissionDialogUtils.mustSetting(this, message);
    }

    /**
     * 建议权限设置
     *
     * @param message 提示内容
     */
    protected void suggestSetting(String message) {
        PermissionDialogUtils.suggestSetting(this, message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mRxManager != null) {
            mRxManager.clear();
        }
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        AppManager.getAppManager().finishActivity(this);

        LoadingDialog.cancelDialogForLoading();

        ToastUtils.cancel();
    }

}
