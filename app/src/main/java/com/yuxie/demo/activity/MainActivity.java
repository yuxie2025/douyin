package com.yuxie.demo.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import com.yuxie.baselib.utils.CommonUtils;
import com.yuxie.baselib.base.BaseActivity;
import com.yuxie.baselib.webView.WebViewActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.widget.ClearEditText;


public class MainActivity extends BaseActivity {

    TextView tvExplain;

    ClearEditText etUrl;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
    }

    protected void initView() {
        setTitle("抖音无水印");
        tvExplain = findViewById(R.id.tvExplain);
        etUrl = findViewById(R.id.et_url);

        findViewById(R.id.openDy).setOnClickListener(v -> {
            openDouYinApp();
        });

        findViewById(R.id.download).setOnClickListener(v -> {
            download();
        });

        //调试使用
        if (AppUtils.isAppDebug()) {
            etUrl.setText("https://v.douyin.com/ArBxFg5/");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerClipEvents();
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
        Log.i("TAG", "content:" + content);
        if (!TextUtils.isEmpty(content)) {
            String msgFromDouYin = content.toString();
            String url = CommonUtils.extractUrl(msgFromDouYin);
            etUrl.setText(url);
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
        if (!url.contains("douyin.com")) {
            ToastUtils.showShort("请输入正确的抖音分享链接！");
            return;
        }

        if (CommonUtils.isDoubleClick(2000)) {
            ToastUtils.showShort("请稍后在下载！");
            return;
        }

        url = CommonUtils.extractUrl(url);
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort("没有获取到链接!");
            return;
        }

        String finalUrl = url;
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        WebViewActivity.open(mContext, finalUrl);
                    }

                    @Override
                    public void onDenied() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("下载去水印视频需要存储权限,去设置!");
                        builder.setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();
                            //打开设置
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                            ActivityUtils.startActivity(intent);
                        });
                        builder.setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        builder.show();
                    }
                }).request();
    }
}
