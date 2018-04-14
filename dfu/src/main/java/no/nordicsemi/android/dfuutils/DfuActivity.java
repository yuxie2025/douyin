package no.nordicsemi.android.dfuutils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;
import no.nordicsemi.android.dfu.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by luo on 2018/3/8.
 */

@RuntimePermissions
public class DfuActivity extends AppCompatActivity {

    Context mContext = this;

    ProgressDialog progressDialog;

    private String mFilePath;

    private Uri mFileStreamUri;

    public static final String FILE_PATH = "filePath";
    public static final String MAC = "mac";

    public static void start(Context context, String filePath, String mac) {
        Intent intent = new Intent(context, DfuActivity.class);
        intent.putExtra(FILE_PATH, filePath);
        intent.putExtra(MAC, mac);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DfuServiceListenerHelper.registerProgressListener(this, mDfuProgressListener);
        DfuActivityPermissionsDispatcher.dfuNeedsWithCheck(DfuActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DfuServiceListenerHelper.unregisterProgressListener(this, mDfuProgressListener);
    }

    public void onUploadClicked(String mFilePath, String mac) {

        if (!DfuUtils.isBLEEnabled(mContext)) {
            DfuUtils.showBLEDialog(this);
            return;
        }

        mFileStreamUri = FileProvider.getUriForFile(this.getApplicationContext(), this.getApplicationContext().getPackageName() + ".fileprovider", new File(mFilePath));

        final boolean keepBond = false;
        final boolean forceDfu = false;
        final boolean enablePRNs = false;
        final int numberOfPackets = 12;

        final DfuServiceInitiator starter = new DfuServiceInitiator(mac)
                .setDeviceName("android.bluetooth.BluetoothDevice")
                .setKeepBond(keepBond)
                .setForceDfu(forceDfu)
                .setDisableNotification(false)
                .setPacketsReceiptNotificationsEnabled(enablePRNs)
                .setPacketsReceiptNotificationsValue(numberOfPackets)
                .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
        starter.setZip(mFileStreamUri, mFilePath);
        starter.start(this, DfuService.class);
        showProgressDialog();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在升级");
        progressDialog.setMessage("请稍等。。。");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private final DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
            Log.e("dfu", "onDeviceConnecting");
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            Log.e("dfu", "onDeviceConnected");
        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {
            Log.e("dfu", "onDfuProcessStarting");
        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {
            Log.e("dfu", "onDfuProcessStarted");
        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {
            Log.e("dfu", "onEnablingDfuMode");
        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            Log.e("dfu", "onProgressChanged" + percent);
            progressDialog.setProgress(percent);
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {
            Log.e("dfu", "onFirmwareValidating");
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Log.e("dfu", "onDeviceDisconnecting");
        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {
            Log.e("dfu", "onDeviceDisconnected");

        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            Log.e("dfu", "onDfuCompleted");
            stopDfu();
            finish();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(DfuService.NOTIFICATION_ID);
                }
            }, 200);
            Toast.makeText(mContext, "升级成功", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            Log.e("dfu", "onDfuAborted");
            stopDfu();
            finish();
            Toast.makeText(mContext, "升级失败，请重新点击升级。", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Log.e("dfu", "onError");
            stopDfu();
            finish();
            Toast.makeText(mContext, "升级失败，请重新点击升级。", Toast.LENGTH_SHORT).show();
        }
    };

    private void stopDfu() {
        Intent intent = new Intent(this, DfuService.class);
        stopService(intent);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void dfuNeeds() {

        String filePath = getIntent().getStringExtra(FILE_PATH);
        String mac = getIntent().getStringExtra(MAC);

        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(DfuActivity.this, "固件包不存在!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (TextUtils.isEmpty(mac)) {
            Toast.makeText(DfuActivity.this, "鞋垫Mac地址不存在!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mac=mac.toUpperCase();

        onUploadClicked(filePath, mac);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DfuActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void dfuAgain() {
        DfuUtils.showAlertDialog(DfuActivity.this, null, "应用需要存储权限,是否去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DfuUtils.toSelfSetting(DfuActivity.this);
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
