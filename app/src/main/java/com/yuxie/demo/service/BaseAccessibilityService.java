package com.yuxie.demo.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * 辅助服务基类，包含公共的动作
 */
@SuppressWarnings("unused")
public class BaseAccessibilityService extends AccessibilityService {
    public static final String TAG = "test";
    public AccessibilityNodeInfo node = null;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 全局返回事件
     */
    public void home() {
        sleepTime(1000);
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    /**
     * 返回上一级
     */
    public void back() {
        sleepTime(1000);
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    /**
     * 滑动左到右
     */
    public void wipeLeftAndRight() {
        sleepTime(1000);
        performGlobalAction(GESTURE_SWIPE_RIGHT);
    }

    /**
     * 滑动下到上
     */
    public void swipeDownAndUp() {
        sleepTime(1000);
        performGlobalAction(GESTURE_SWIPE_UP);
    }

    /**
     * 模拟点击文字按钮事件
     *
     * @param text
     * @param i
     */
    @SuppressLint("NewApi")
    public void findTextAndClick(String text, int i) {
        // 查找当前窗口中包含“xx”文字的按钮
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        if (node == null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText(text);
            if (list.size() > 0) {
                node = list.get(i);
            }
        }
        targetNode = node;
        if (targetNode != null) {
            final AccessibilityNodeInfo n = targetNode;
            performClick(n);
        }
    }

    public AccessibilityNodeInfo findByClassName(String className) {
        // 查找当前窗口中包含“xx”文字的按钮
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        return findByClassName(nodeInfo, className);
    }

    private AccessibilityNodeInfo findByClassName(AccessibilityNodeInfo node, String className) {

        if (node == null) {
            return null;
        }

        if (node.getClassName().toString().contains(className)) {
            return node;
        }

        if (node.getChildCount() == 0) {
            if (node.getClassName().toString().contains(className)) {
                return node;
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    AccessibilityNodeInfo agent = findByClassName(node.getChild(i), className);
                    if (agent != null) return agent;
                }
            }
        }
        return null;
    }


    /**
     * 通过一部分TEXT获得全部TEXT
     *
     * @param text
     * @return
     */
    public String getTextByText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        return list.get(0).getText().toString();
        //String text = nodeInfo.getText().toString();
    }

    /**
     * 通过ID获得TEXT
     *
     * @param id
     * @return
     */

    public String getTextById(String id) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/" + id);
        return list.get(0).getText().toString();
    }


    /**
     * 模拟点击id事件
     *
     * @param id
     * @param i
     */
    public void findIdAndClick(String id, int i) {
        // 查找当前窗口中id为“id”的按钮
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;

        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo
                .findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
        if (list.size() > 0) {
            node = list.get(i);
        }
        targetNode = node;

        if (targetNode != null) {
            final AccessibilityNodeInfo n = targetNode;
            performClick(n);
        }
    }


    /**
     * 判断是否找到某个ID
     *
     * @param id
     * @return
     */
    public boolean isFindId(String id) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否找到某个ID
     *
     * @param id
     * @return
     */
    public boolean isFindId(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
        if (list.size() > 0) {
            node = list.get(i);
        }
        targetNode = node;
        if (targetNode != null) {
            return true;
        }
        return false;
    }


    public Rect isFindWXBouns(String id, int i, String parentid) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + parentid);
        AccessibilityNodeInfo targetNode = null;
        if (list.size() > 0) {
            targetNode = list.get(0);
            List<AccessibilityNodeInfo> list1 = targetNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
            if (list1.size() > 0) {
                Rect nodeRect = new Rect();
                list1.get(i).getBoundsInScreen(nodeRect);
                return nodeRect;
            }
        }
        return null;
    }

    public boolean isFindWXBouns(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
        AccessibilityNodeInfo targetNode = null;
        Log.i("zzz1", list.size() + "aaaa" + i);
        if (list.size() > 0) {

            Rect nodeRect = new Rect();
            AccessibilityNodeInfo accessibilityNodeInfo = list.get(i);
            Log.i("zzz1", list.size() + "bb" + accessibilityNodeInfo);
            accessibilityNodeInfo.getBoundsInScreen(nodeRect);
            Log.i("zzz1", nodeRect.bottom + "" + nodeRect.top);
            if (nodeRect.bottom == 1263 && nodeRect.top == 1167) {
                return true;
            }

        }
        return false;
    }


    /**
     * 判断是否找到某个文本
     *
     * @param text
     * @return
     */
    public boolean isFindText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 点击匹配的nodeInfo
     *
     * @param str text关键字
     */
    public void openNext(String str) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Toast.makeText(this, "rootWindow为空", Toast.LENGTH_SHORT).show();
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(str);
        if (list != null && list.size() > 0) {
            list.get(list.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            list.get(list.size() - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    /**
     * 执行具体的点击
     *
     * @param nodeInfo
     */
    public void performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            performClick(nodeInfo.getParent());
        }
    }

    /**
     * 复制方法
     *
     * @param string
     */
    public void copyToBoard(String string) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", string);
        cm.setPrimaryClip(clipData);
    }

    /**
     * 睡眠
     *
     * @param millis
     */
    public void sleepTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //自动输入打招呼内容
    public void inputHello(String hello) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        //找到当前获取焦点的view
        AccessibilityNodeInfo target = nodeInfo.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (target == null) {
            Log.d(TAG, "inputHello: null");
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", hello);
        clipboard.setPrimaryClip(clip);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            target.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
    }

    /**
     * 粘贴
     *
     * @param id
     * @param i
     */
    public void paste(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        try {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
            if (list.size() > 0)
                node = list.get(i);
            targetNode = node;
            if (targetNode != null) {
                targetNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                targetNode.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void findContentAndClick(String id, int i, String text, int child) {
        // 查找当前窗口中包含“xx”文字的按钮
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);
        if (list.size() > 0) {
            node = list.get(i);
        }
        targetNode = node;
        if (targetNode != null) {
            final AccessibilityNodeInfo n = targetNode.getChild(child);
            Log.i("123", n + "");
            if (n.getContentDescription().toString().equals(text)) {
                performClick(n);
            }
        }
    }

    public void performDialogClick(String id, int i, int child, String text) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/" + id);
        Log.i("dialog", list.size() + "");
        if (list.size() > 0) {
            node = list.get(i);
            Log.i("dialog", node + "");
        }
        targetNode = node;
        if (targetNode != null) {
            final AccessibilityNodeInfo n = targetNode.getChild(child);
            Log.i("dialog", targetNode.getChild(0) + "child");
            Log.i("dialog", targetNode.getChild(1) + "child");
            Log.i("dialog", targetNode.getChild(2) + "child");

            if (n.getClassName().toString().equals("android.widget.ImageView") || n.getContentDescription().toString().equals(text)) {
                performClick(n);
            }
        }
    }

    /*****
     * 根据id和text一起判断点击
     */
    public void WXfindIdTextAndClick(String id, String text, int i) {
        // 查找当前窗口中id为“id”的按钮
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        AccessibilityNodeInfo targetNode1 = null;
        node = null;
        List<AccessibilityNodeInfo> list = nodeInfo
                .findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + id);

        nodeInfo.findAccessibilityNodeInfosByText("");
        if (list.size() > 0) {
            targetNode = list.get(i);
            Log.i("890", targetNode + "node");
            if (targetNode.getText().equals(text)) {
                node = targetNode;
            }
        }
        targetNode1 = node;

        if (targetNode1 != null) {
            final AccessibilityNodeInfo n = targetNode1;
            performClick(n);
            Log.i("890", n + "node");
        }
    }

    /**
     * 普通模式
     *
     * @param millis
     */
    public void normalMode(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String getCurrentActivityName(AccessibilityEvent event) {
        String currentActivityName = "";
        try {
            ComponentName componentName = new ComponentName(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            );
            getPackageManager().getActivityInfo(componentName, 0);
            currentActivityName = componentName.flattenToShortString();
        } catch (PackageManager.NameNotFoundException e) {
        }
        return currentActivityName;
    }

    /**
     * 返回桌面
     */
    protected void backHome() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

}
