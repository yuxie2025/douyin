package com.yuxie.demo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.yuxie.demo.utils.CommonUtils;

import java.util.List;

public class RedPacketAccessibilityService extends BaseAccessibilityService {

    private static final String TAG = "RedPacket";
    private static final String WECHAT_NOTIFICATION_TIP = "[微信红包]";
    //com.tencent.mm/.plugin.luckymoney.ui.En_fba4b94f  com.tencent.mm/com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI
    private static final String WECHAT_LUCKMONEY_RECEIVE_ACTIVITY = ".plugin.luckymoney.ui";
    private static final String WECHAT_LUCKMONEY_GENERAL_ACTIVITY = "LauncherUI";
    private String currentActivityName = WECHAT_LUCKMONEY_GENERAL_ACTIVITY;

    boolean isClick = false;

    private HongbaoSignature signature = new HongbaoSignature();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        setCurrentActivityName(event);

        int eventType = event.getEventType();
        switch (eventType) {
            //通知栏来信息，判断是否含有微信红包字样，是的话跳转
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                watchNotifications(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:

                if (currentActivityName.contains(WECHAT_LUCKMONEY_GENERAL_ACTIVITY)) {
                    if (!isClick) {
                        return;
                    }
                    watchChat(event);
                }
                if (currentActivityName.contains(WECHAT_LUCKMONEY_RECEIVE_ACTIVITY)) {

                    if (!isClick) {
                        return;
                    }
                    isClick = false;

                    AccessibilityNodeInfo openRedPacket = findByClassName("android.widget.Button");
                    if (openRedPacket != null) {
                        performClick(openRedPacket);
                    }
                    CommonUtils.random(1, 2);
                    back();
                    sleepTime(500);
                    backHome();
                }
                break;
        }

    }

    @Override
    public void onInterrupt() {
        super.onInterrupt();
    }

    private void watchChat(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = event.getSource();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("微信红包");
        if (list.size() != 0) {
            CommonUtils.random(1, 2);
            performClick(list.get(list.size() - 1));
        }
    }

    private boolean watchNotifications(AccessibilityEvent event) {
        // Not a notification
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            return false;

        // Not a hongbao
        String tip = event.getText().toString();
        if (!tip.contains(WECHAT_NOTIFICATION_TIP)) return true;

        Parcelable parcelable = event.getParcelableData();
        if (parcelable instanceof Notification) {
            Notification notification = (Notification) parcelable;
            try {
                /* 清除signature,避免进入会话后误判 */
                isClick = true;
                signature.cleanSignature();
                notification.contentIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void setCurrentActivityName(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        try {
            ComponentName componentName = new ComponentName(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            );

            getPackageManager().getActivityInfo(componentName, 0);
            currentActivityName = componentName.flattenToShortString();
            Log.i(TAG, "setCurrentActivityName---currentActivityName:" + currentActivityName);
        } catch (PackageManager.NameNotFoundException e) {
            currentActivityName = "";
            Log.i(TAG, "setCurrentActivityName---activityName:" + e);
        }
    }


}
