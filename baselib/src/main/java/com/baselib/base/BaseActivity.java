package com.baselib.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.baseapp.AppManager;
import com.baselib.baserx.RxManager;
import com.baselib.commonutils.TUtil;
import com.baselib.daynightmodeutils.ChangeModeController;
import com.baselib.uitls.CommonUtils;
import com.baselib.uitls.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * 基类
 * Created by luo on 2017/9/5.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AutoLayoutActivity {
    protected T mPresenter;
    protected E mModel;
    protected Context mContext;
    protected RxManager mRxManager;
    protected Subscription subscription;
    private AlertDialog mAlertDialog;

    private Unbinder unbinder;

    //分页,第几页
    protected int page = 1;
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
        initPresenter();
        initView(savedInstanceState);

        //绑第二次是为了,兼容DataBindingUtil,否则点击事件将无效
//        unbinder = ButterKnife.bind(this);

        //销毁当前页面
        back();

        if (isSetStatusBarColor) {
            // 默认着色状态栏
            setStatusBarColor();
        }

    }

    /**
     * layoutResID to View
     */
    protected View parseLayoutResId(@LayoutRes final int layoutResID) {
        return getLayoutInflater().inflate(layoutResID, null);
    }

    /**
     * 如果有返回按钮,设置监听
     */
    protected void back() {
        try {
            findViewById(R.id.rl_left).setOnClickListener(view -> finish());
        } catch (Exception e) {
        }
    }

    /**
     * 设置title
     *
     * @param title
     */
    protected void setTitle(String title) {
        try {
            ((TextView) findViewById(R.id.title)).setText(title);
        } catch (Exception e) {
        }
    }

    /**
     * 设置右上角保存
     *
     * @param save
     * @param res
     * @param onClickListener
     */
    protected void setSave(String save, @ColorInt int res, View.OnClickListener onClickListener) {
        try {
            TextView saveText = ((TextView) findViewById(R.id.save));
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
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    protected void initPresenter() {
        if (mPresenter != null) {
            mPresenter.inject(this, this, mModel);
        }
    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    protected abstract int getLayoutId();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置layout前配置
     */
    protected void onBeforeSetContentView() {

        //设置昼夜主题
//        initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 无标题
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 设置状态栏的颜色和透明度（4.4以上系统有效）
     */
    protected void setStatusBarColor() {
        int mStatusBarColor = getResources().getColor(R.color.status_bar_color);
        StatusBarUtil.setColor(this, mStatusBarColor, 50);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
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
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 请求权限
     * <p>
     * 如果权限被拒绝过，则提示用户需要权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showAlertDialog("权限需求", rationale,
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{permission}, requestCode);
                        }
                    }, "确定", null, "取消");
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        showAlertDialog(title, message, onPositiveButtonClickListener, positiveText, onNegativeButtonClickListener, negativeText, false);
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     * @param isCancal                      - 点击范围外和back键是否取消
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText, boolean isCancal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancal);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }

    /**
     * 必须权限设置
     *
     * @param message
     */
    protected void mustSetting(String message) {
        if (TextUtils.isEmpty(message)) {
            message = "应用需要定位权限和存储权限,是否去设置";
        }
        showAlertDialog(null, message, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonUtils.toSelfSetting();
            }
        }, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
                Process.killProcess(Process.myPid());
            }
        }, "取消", false);
    }

    /**
     * 建议权限设置
     *
     * @param message
     */
    protected void suggestSetting(String message) {
        if (TextUtils.isEmpty(message)) {
            message = "应用需要定位权限和存储权限,是否去设置";
        }
        showAlertDialog(null, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonUtils.toSelfSetting();
            }
        }, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }, "取消", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        unsubscribe();
        AppManager.getAppManager().finishActivity(this);

        if (unbinder != null) {
            unbinder.unbind();
        }

    }

}
