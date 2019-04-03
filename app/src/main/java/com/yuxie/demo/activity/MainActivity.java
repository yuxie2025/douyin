package com.yuxie.demo.activity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apkupdate.ApkUpdateParamSet;
import com.apkupdate.UpdateActivity;
import com.apkupdate.utils.DownloadUtils;
import com.apkupdate.utils.UpdateUtils;
import com.apkupdate.widget.ApkVersionModel;
import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.uitls.CommonUtils;
import com.baselib.uitls.SysDownloadUtil;
import com.baselib.uitls.UrlUtils;
import com.blankj.utilcode.constant.RegexConstants;
import com.blankj.utilcode.util.AppUtils;
import com.jaeger.library.StatusBarUtil;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {


    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;

    @BindView(R.id.tvExplain)
    TextView tvExplain;

    ClipboardManager mClipboardManager;
    ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;
    private SysDownloadUtil downloadUtil;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("抖音无水印");
        rlLeft.setVisibility(View.INVISIBLE);

        String explain = "该app用于下载抖音无水印视频.\n\n使用说明:\n打开抖音>分享>复制链接,即可自动下载";
        tvExplain.setText(explain);

        downloadUtil = new SysDownloadUtil();
        registerClipEvents();

        update();

    }

    @Override
    protected void setStatusBarColor() {
        int mStatusBarColor = ContextCompat.getColor(mContext, R.color.status_bar_color);
        StatusBarUtil.setColorForSwipeBack(this, mStatusBarColor, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 注册剪切板复制、剪切事件监听
     */
    private void registerClipEvents() {
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (mClipboardManager.hasPrimaryClip()
                        && mClipboardManager.getPrimaryClip().getItemCount() > 0) {
                    // 获取复制、剪切的文本内容
                    CharSequence content =
                            mClipboardManager.getPrimaryClip().getItemAt(0).getText();
                    String url = CommonUtils.getMatches(content.toString(), "(" + RegexConstants.REGEX_URL + ")");
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    if (!url.contains("douyin")) {
                        return;
                    }
                    getDownloadUrl(url);
                }
            }
        };
        mClipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    private void getDownloadUrl(String url) {

        String[] urls = UrlUtils.string2Url(url);
        mRxManager.add(ServerApi.getInstance(urls[0]).getUrl(urls[1]).compose(RxSchedulers.io_main()).subscribe(new RxSubscriber<String>(mContext, false) {

            @Override
            protected void _onNext(String stringResult) {
                String videoId = CommonUtils.getMatches(stringResult, "video_id=(.+)&line=0");
                if (TextUtils.isEmpty(videoId)) {
                    showToast("获取下载链接失败!");
                    return;
                }
                String fileName = videoId + ".mp4";
                String url = "https://aweme.snssdk.com/aweme/v1/play/?video_id=" + videoId;
                downloadUtil.download(MainActivity.this, url, fileName);
            }

            @Override
            protected void _onError(String message) {
                showToast(message);
            }
        }));
    }

    /**
     * 注销监听，避免内存泄漏。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClipboardManager != null && mOnPrimaryClipChangedListener != null) {
            mClipboardManager.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
        }
        downloadUtil.unregister(this);
    }

    private void update() {

        mRxManager.add(ServerApi.getInstance().updateApp()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<ApkVersionModel>>(mContext, false) {
                    @Override
                    protected void _onNext(BaseRespose<ApkVersionModel> baseRespose) {

                        double versionDouble = CommonUtils.string2Double(baseRespose.getData().getAppVersion());
                        String cVersionStr = AppUtils.getAppVersionName();
                        double cVersionDouble = CommonUtils.string2Double(cVersionStr);
                        if (cVersionDouble < versionDouble) {
                            UpdateActivity.start(mContext, baseRespose.getData());
                        }
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                }));
    }

}
