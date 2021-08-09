package com.yuxie.demo.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
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
import com.baselib.ui.widget.ClearableEditTextWithIcon;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.jaeger.library.StatusBarUtil;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.utils.douyin.Douyin;
import com.yuxie.demo.widget.ClearEditText;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a_video";

    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;

    @BindView(R.id.tvExplain)
    TextView tvExplain;

    @BindView(R.id.et_url)
    ClearEditText etUrl;

    boolean isEnd = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("抖音无水印");
        rlLeft.setVisibility(View.INVISIBLE);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        XXPermissions.with(this)
                // 不适配 Android 11 可以这样写
                //.permission(Permission.Group.STORAGE)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .permission(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    registerClipEvents();
                                }
                            }, 500);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        ToastUtils.showShort("获取权限失败！");
                    }
                });
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
        CharSequence content = ClipboardUtils.getText();
        if (!TextUtils.isEmpty(content)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String fileDir = path;
                        String msgFromDouYin = content.toString();

                        if (Douyin.isExists(msgFromDouYin, fileDir)) {
                            System.out.println("已经下载过！");
                            //下载过了
                            return;
                        }

                        boolean re = Douyin.downloadVideo(msgFromDouYin, fileDir);
                        System.out.println("下载结果re:" + re);
                        if (re) {
                            String dirName = new File(fileDir).getName();
                            ToastUtils.showShort("下载成功，文件在" + dirName + "目录下！");
                        } else {
                            ToastUtils.showShort("下载失败！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showShort("下载失败！");
                    }
                }
            }).start();
        }
    }

    /**
     * 注销监听，避免内存泄漏。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @OnClick({R.id.openDy, R.id.hotVideo, R.id.hVideo, R.id.dVideo, R.id.copyVideo, R.id.download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openDy:
                openDouYinApp();
                break;
            case R.id.hotVideo:
                startActivity(PlayVideosActivity.class);
                break;
            case R.id.hVideo:
                NativeVideosActivity.start(this);
                break;
            case R.id.copyVideo:
                getDyVideo();
                break;
            case R.id.dVideo:
                if (TextUtils.isEmpty(path) || !FileUtils.isDir(path)) {
                    showToast("你还没有,下载视频哦");
                    return;
                }
                NativeVideosActivity.start(this, path);
                break;
            case R.id.download:
                download();
                break;
        }
    }

    private void openDouYinApp() {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.ss.android.ugc.aweme", "com.ss.android.ugc.aweme.splash.SplashActivity");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void download() {
        String url = etUrl.getText().toString();
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort("请输入抖音分享链接！");
            return;
        }

        if (CommonUtils.isDoubleClick(2000)) {
            ToastUtils.showShort("请稍后在下载！");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileDir = path;
                    String msgFromDouYin = url;

                    if (Douyin.isExists(msgFromDouYin, fileDir)) {
                        String dirName = new File(fileDir).getName();
                        ToastUtils.showShort("已经下载过，文件在" + dirName + "目录下！");
                        return;
                    }

                    boolean re = Douyin.downloadVideo(msgFromDouYin, fileDir);
                    System.out.println("下载结果re:" + re);
                    if (re) {
                        String dirName = new File(fileDir).getName();
                        ToastUtils.showShort("下载成功，文件在" + dirName + "目录下！");
                    } else {
                        ToastUtils.showShort("下载失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort("下载失败！");
                }
            }
        }).start();
    }

    void getDyVideo() {

        isEnd = false;
        showToast("开始获取...");
        new Thread(() -> {
            List<String> paths = getDyPath();
            if (paths.size() == 0) {
                showToast("先去抖音看看吧");
                return;
            }
            String dyPath = "";
            for (int i = 0; i < paths.size(); i++) {
                dyPath = paths.get(i);
                copyVideo(dyPath, path);
            }
            showToast("获取抖音视频完成,文件夹路径:a_video");
        }).start();
    }

    List<String> getDyPath() {
        String dyPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cache";
        String dyPath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cachev2";
        List<String> re = new ArrayList<>();
        re.add(dyPath1);
        re.add(dyPath2);
        return re;
    }

    void copyVideo(String dyPath, String path) {
        List<File> dyFiles = FileUtils.listFilesInDir(dyPath);
        if (dyFiles == null) return;
        for (File file : dyFiles) {
            if (isEnd) {
                return;
            }
            long fileSize = file.length();
            if (fileSize <= 1 * 1024) {
                continue;
            }

            String filePath = path + File.separator + file.getName() + ".mp4";
            FileUtils.copy(file.getAbsolutePath(), filePath);
        }
    }
}
