package com.baselib.baserx;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.baselib.R;
import com.baselib.baseapp.BaseApplication;
import com.baselib.basebean.BaseMsgEvent;
import com.baselib.commonutils.NetWorkUtils;
import com.baselib.enums.BaseMsgEnum;
import com.baselib.ui.widget.LoadingDialog;
import com.blankj.utilcode.util.LogUtils;

import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 作者: llk on 2017/9/8.
 * 订阅封装
 */
@SuppressWarnings("unused")
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = false;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
        LogUtils.d(t.toString());
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
//        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
        } else if (e instanceof ServerException) {//服务器
            _onError("服务器异常,请稍后再试!");
        } else if (e instanceof SocketTimeoutException) {
            _onError("请求超时,请稍后再试!");
        } else if (e instanceof ResultException) {
            ResultException err = (ResultException) e;

            if (TextUtils.isEmpty(err.getErrMsg())) {
                err.setErrMsg("");
            }
            if ("Index: 0, Size: 0".equals(err.getErrMsg())) {
                err.setErrMsg("");
            }

            if (err.isErrCode()) {
                if ("400003".equals(err.getErrMsg())) {
                    RxBus.getInstance().post(new BaseMsgEvent<>(BaseMsgEnum.TOKEN, "400003"));
                } else {
                    _onError(err.getErrMsg());
                }
            } else {
                _onError(err.getErrMsg());
            }
        } else { //其它
            _onError("网络访问错误,请稍后再试!");
            if (e != null) {
                LogUtils.d("其它错误:" + e.getMessage());
            }
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}