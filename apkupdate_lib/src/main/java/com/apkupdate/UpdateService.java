package com.apkupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.apkupdate.utils.UpdateUtils;

import java.io.File;

/**
 * 更新服务
 * Created by luo on 2018/3/6.
 */
@SuppressWarnings("unused")
public class UpdateService extends Service {

    private NotificationManager notificationManager;
    private ApkUpdateParamSet mApkUpdateParamSet;
    private LocalBroadcastManager localBroadcastManager;
    public static final String BROADCAST_UPDATE_PROCESS_SUCCESS = "broadcast_update_process_success";
    public static final String BROADCAST_UPDATE_PROCESS_ERROR = "broadcast_update_process_error";
    public static final String BROADCAST_UPDATE_PROCESS_FINISH = "broadcast_update_process_finish";
    public static final String BROADCAST_UPDATE_PROCESS_VALUE = "broadcast_update_process_value";

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
        } else {
            ApkUpdateParamSet paramSet = (ApkUpdateParamSet) intent.getSerializableExtra(ApkUpdateParamSet.NAME);
            mApkUpdateParamSet = checkApkUpdateParamSet(paramSet);
            startDownload(mApkUpdateParamSet);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 检查参数
     *
     * @param updateParamSet
     * @return
     */
    private ApkUpdateParamSet checkApkUpdateParamSet(ApkUpdateParamSet updateParamSet) {

        if (TextUtils.isEmpty(updateParamSet.getLocalPath()))
            updateParamSet.setLocalPath(Environment.getExternalStorageDirectory() + "/versionUpdate.apk");
        if (TextUtils.isEmpty(updateParamSet.getNotifyStartMessage()))
            updateParamSet.setNotifyStartMessage("开始下载");
        if (TextUtils.isEmpty(updateParamSet.getNotifyIngMessage()))
            updateParamSet.setNotifyIngMessage("正在下载中...");
        if (TextUtils.isEmpty(updateParamSet.getNotifyEndMessage()))
            updateParamSet.setNotifyEndMessage("下载完成");
        if (0 == updateParamSet.getLogoRes())
            updateParamSet.setLogoRes(R.drawable.ic_default_notify_logo);

        return updateParamSet;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 单例下载
     *
     * @param updateParamSet
     */
    private void startDownload(final ApkUpdateParamSet updateParamSet) {
        UpdateManager.getInstance().startDownloads(updateParamSet.getApkUrl(), updateParamSet.getLocalPath(), new UpdateDownloadListener() {
            @Override
            public void onStarted() {
                if (updateParamSet.isEnableNotifycation())
                    notifyUser(updateParamSet.getNotifyStartMessage(), 0);
                if (updateParamSet.isEnableBroadcast())
                    sendBroadCastProcess(BROADCAST_UPDATE_PROCESS_SUCCESS, 0);
            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                if (updateParamSet.isEnableNotifycation())
                    notifyUser(updateParamSet.getNotifyIngMessage(), progress);
                if (updateParamSet.isEnableBroadcast())
                    sendBroadCastProcess(BROADCAST_UPDATE_PROCESS_SUCCESS, progress);
            }

            @Override
            public void onFinished(float completeSize, String downloadUrl) {
                if (updateParamSet.isEnableNotifycation())
                    notifyUser(updateParamSet.getNotifyEndMessage(), 100);
                if (updateParamSet.isEnableBroadcast())
                    sendBroadCastProcess(BROADCAST_UPDATE_PROCESS_FINISH, 100);
                install();
                stopSelf();
            }

            @Override
            public void onFailure() {
                if (updateParamSet.isEnableNotifycation())
                    notifyUser(updateParamSet.getNotifyErrorMessage(), 0);
                if (updateParamSet.isEnableBroadcast())
                    sendBroadCastProcess(BROADCAST_UPDATE_PROCESS_ERROR, 0);
                stopSelf();
            }
        });
    }

    /**
     * 更新通知UI
     *
     * @param message
     * @param process
     */
    private void notifyUser(String message, int process) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "");
        builder.setSmallIcon(mApkUpdateParamSet.getLogoRes())
                .setContentText(message)
                .setContentTitle(mApkUpdateParamSet.getNotifyTitle());
        if (process > 0 && process < 100) {
            builder.setProgress(100, process, false);
        } else {
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(message);
//        Intent intent=new Intent(UpdateService.this,UpdateActivity.class);
//        builder.setContentIntent(process >= 100 ? getPendingIntent()
//                : PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        Notification notification = builder.build();
        notificationManager.notify(0, notification);

    }

    /**
     * 返回安装程序的PendingIntent
     *
     * @return
     */
    private PendingIntent getPendingIntent() {
        File apkFile = new File(mApkUpdateParamSet.getLocalPath());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(apkFile);
        } else {
            data = FileProvider.getUriForFile(this.getApplicationContext(), this.getApplicationContext().getPackageName() + ".fileprovider", apkFile);
        }
        intent.setDataAndType(data, type);
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 安装程序
     */
    private void install() {
        File apkFile = new File(mApkUpdateParamSet.getLocalPath());
        this.startActivity(UpdateUtils.getInstallAppIntent(UpdateService.this.getApplicationContext(), apkFile, this.getApplicationContext().getPackageName() + ".fileprovider"));
    }


    /**
     * 发送广播
     *
     * @param process
     */
    private void sendBroadCastProcess(final String action, int process) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(BROADCAST_UPDATE_PROCESS_VALUE, process);
        localBroadcastManager.sendBroadcast(intent);
    }

}