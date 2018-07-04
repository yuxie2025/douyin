package com.apkupdate;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.apkupdate.utils.UpdateUtils;
import com.apkupdate.widget.ApkVersionModel;
import com.apkupdate.widget.UpdateDialog;
import com.apkupdate.widget.UpdateProgressDialog;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class UpdateActivity extends AppCompatActivity {

    UpdateProgressDialog updateProgressDialog;

    String mUrl;

    public static final String APK_VERSION_MODEL = "ApkVersionModel";
    public static final String IS_DOWNLOAD = "isDownload";

    public static void start(Context context, ApkVersionModel apkVersionModel, boolean isDownload) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(APK_VERSION_MODEL, apkVersionModel);
        intent.putExtra(IS_DOWNLOAD, isDownload);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, UpdateUtils.getIntentFilter());
        UpdateActivityPermissionsDispatcher.fileNeedsWithCheck(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除监听
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void update(final ApkVersionModel apkVersionModel) {
        UpdateDialog.getDialog(apkVersionModel, UpdateActivity.this, new UpdateDialog.OnClickUpdate() {
            @Override
            public void sure() {
                downloadApkCheck(apkVersionModel);
            }
        }).show(getSupportFragmentManager(), UpdateActivity.class.getName());
    }

    private void downloadApkCheck(final ApkVersionModel apkVersionModel) {

        String filePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + ".apk";
        final File file = new File(filePath);
        if (!file.exists()) {
            downloadApk(apkVersionModel);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkVersionModel.getUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.connect();
                    final long currentLength = connection.getContentLength();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (currentLength == file.length()) {
                                startActivity(UpdateUtils.getInstallAppIntent(UpdateActivity.this.getApplicationContext(), file, UpdateActivity.this.getApplicationContext().getPackageName() + ".fileprovider"));
                                finish();
                            } else {
                                downloadApk(apkVersionModel);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "下载异常,请稍后再试!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).start();
    }


    private void downloadApk(ApkVersionModel apkVersionModel) {

        if (updateProgressDialog == null)
            updateProgressDialog = UpdateProgressDialog.getDialog(UpdateActivity.this);
        updateProgressDialog.show(getSupportFragmentManager(), UpdateActivity.class.getName());
        ApkUpdateParamSet apkUpdateParamSet = new ApkUpdateParamSet();
        apkUpdateParamSet.setApkUrl(apkVersionModel.getUrl());
        apkUpdateParamSet.setLocalPath(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + ".apk");
        apkUpdateParamSet.setNotifyEndMessage("下载完成");
        apkUpdateParamSet.setNotifyIngMessage("下载中...");
        apkUpdateParamSet.setNotifyTitle(getString(R.string.app_name));
        apkUpdateParamSet.setEnableBroadcast(true);
        apkUpdateParamSet.setEnableNotifycation(false);
        Intent intent = new Intent(UpdateActivity.this, UpdateService.class);
        intent.putExtra(ApkUpdateParamSet.NAME, apkUpdateParamSet);
        startService(intent);
    }


    /************************************************************************************
     * 版本更新
     * */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (UpdateService.BROADCAST_UPDATE_PROCESS_ERROR.equals(action)) {
                Toast.makeText(context, "下载错误,请稍后再试!", Toast.LENGTH_SHORT).show();
            } else if (UpdateService.BROADCAST_UPDATE_PROCESS_SUCCESS.equals(action)) {
                final int process = intent.getIntExtra(UpdateService.BROADCAST_UPDATE_PROCESS_VALUE, 0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (updateProgressDialog != null) {
                            updateProgressDialog.setProgress(process);
                        }
                    }
                });
            } else if (UpdateService.BROADCAST_UPDATE_PROCESS_FINISH.equals(action)) {
                finish();
            }
        }
    };

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void fileNeeds() {
        boolean isDownload = getIntent().getBooleanExtra(IS_DOWNLOAD, false);
        ApkVersionModel apkVersionModel = (ApkVersionModel) getIntent().getSerializableExtra(APK_VERSION_MODEL);
        if (isDownload) {
            downloadApkCheck(apkVersionModel);
        } else {
            update(apkVersionModel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UpdateActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void fileAskAgain() {
        UpdateUtils.showAlertDialog(UpdateActivity.this, null, "应用需要存储权限,是否去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateUtils.toSelfSetting(UpdateActivity.this);
                finish();
            }
        }, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, "取消", false);
    }

}
