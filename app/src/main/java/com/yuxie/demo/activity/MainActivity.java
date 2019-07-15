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

import com.apkupdate.UpdateActivity;
import com.apkupdate.widget.ApkVersionModel;
import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.uitls.CommonUtils;
import com.baselib.uitls.SysDownloadUtil;
import com.baselib.uitls.UrlUtils;
import com.blankj.utilcode.constant.RegexConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.videolib.PlayVideoActivity;
import com.videolib.player.VideoModel;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;

    @BindView(R.id.tvExplain)
    TextView tvExplain;

    ClipboardManager mClipboardManager;
    ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;
    private SysDownloadUtil downloadUtil;

    boolean isEnd = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("抖音无水印");
        rlLeft.setVisibility(View.INVISIBLE);

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
                    CharSequence content = mClipboardManager.getPrimaryClip().getItemAt(0).getText();
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    String url = CommonUtils.getMatches(content.toString(), "(" + RegexConstants.REGEX_URL + ")");
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    getDownloadUrl(url);
                }
            }
        };
        mClipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    private void getDownloadUrl(String downUrl) {
        String url = "http://lyfzn.top/api/douyinApi/";
        String[] urls = UrlUtils.string2Url(url);
        url = url + "?url=" + downUrl;
        mRxManager.add(ServerApi.getInstance(urls[0]).getUrl(url)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<String>(mContext, false) {
                    @Override
                    protected void _onNext(String stringResult) {
                        String url;
                        try {
                            JSONObject jsonObject = new JSONObject(stringResult);
                            JSONArray videoUrls = jsonObject.getJSONArray("urls");
                            url = videoUrls.get(0).toString();
                        } catch (Exception e) {
                            url = "";
                        }

                        if (TextUtils.isEmpty(url)) {
                            showToast("获取解析地址失败!");
                            return;
                        }

                        String fileName = EncryptUtils.encryptMD5ToString(url) + ".mp4";
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
        if (!isEnd) {
            isEnd = true;
        }
    }

    private void update() {

        mRxManager.add(ServerApi.getInstance().updateApp()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<ApkVersionModel>>(mContext, false) {
                    @Override
                    protected void _onNext(BaseRespose<ApkVersionModel> baseRespose) {

                        tvExplain.setText(baseRespose.getData().getMsg());

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

    @OnClick({R.id.openDy, R.id.hotVideo, R.id.hVideo, R.id.dVideo, R.id.copyVideo})
    public void onViewClicked(View view) {
        String path;
        switch (view.getId()) {
            case R.id.openDy:
                ActivityUtils.startActivity("com.ss.android.ugc.aweme", "com.ss.android.ugc.aweme.splash.SplashActivity");
                break;
            case R.id.hotVideo:
                startActivity(PlayVideosActivity.class);
                break;
            case R.id.hVideo:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cache";
                if (TextUtils.isEmpty(path) || !FileUtils.isDir(path)) {
                    showToast("先去抖音,刷刷视频再来吧");
                    return;
                }
                NativeVideosActivity.start(this, path);
                break;
            case R.id.copyVideo:
                String dyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cache";
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a_video";
                if (TextUtils.isEmpty(dyPath) || !FileUtils.isDir(dyPath)) {
                    showToast("先去抖音看看吧");
                    return;
                }
                isEnd = false;
                showToast("开始获取...");
                new Thread(() -> {
                    List<File> dyFiles = FileUtils.listFilesInDir(dyPath);
                    for (File file : dyFiles) {
                        if (isEnd) {
                            return;
                        }
                        String filePath = path + File.separator + file.getName() + ".mp4";
                        FileUtils.copyFile(file.getAbsolutePath(), filePath);
                    }
                    showToast("获取抖音视频完成,文件夹路径:a_video");
                }).start();
                break;
            case R.id.dVideo:
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a_video";
                if (TextUtils.isEmpty(path) || !FileUtils.isDir(path)) {
                    showToast("你还没有,下载视频哦");
                    return;
                }
                NativeVideosActivity.start(this, path);
                break;
        }
    }
}
