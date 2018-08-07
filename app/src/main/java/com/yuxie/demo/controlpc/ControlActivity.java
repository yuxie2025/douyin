package com.yuxie.demo.controlpc;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.audio.control.MyRecognizer;
import com.audio.control.MyWakeup;
import com.audio.recognization.IStatus;
import com.audio.recognization.MessageStatusRecogListener;
import com.audio.recognization.StatusRecogListener;
import com.audio.util.Logger;
import com.audio.wakeup.IWakeupListener;
import com.audio.wakeup.RecogWakeupListener;
import com.audio.wakeup.WakeUpResult;
import com.baidu.speech.asr.SpeechConstant;
import com.baselib.base.BaseActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.yuxie.demo.R;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ControlActivity extends BaseActivity implements IStatus {

    private static float mx = 0; // 发送的鼠标移动的差值
    private static float my = 0;
    private static float lx; // 记录上次鼠标的位置
    private static float ly;
    private static float fx; // 手指第一次接触屏幕时的坐标
    private static float fy;
    private static float lbx = 0; // 鼠标左键移动初始化坐标
    private static float lby = 0;

    private FrameLayout leftButton;
    private FrameLayout rightButton;
    private FrameLayout middleButton;
    FrameLayout touch;
    TextView spinner;

    private Button btn_left;
    private Button btn_right;

    private DatagramSocket socket;
    private QMUIListPopup mListPopup;

    protected MyWakeup myWakeup;

    private static final String TAG = "ControlActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        initTouch();
    }


    private void initTouch() {

        ControlActivityPermissionsDispatcher.audioNeedsWithCheck(this);

        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);

        leftButton = (FrameLayout) findViewById(R.id.leftButton);
        rightButton = (FrameLayout) findViewById(R.id.rightButton);
        middleButton = (FrameLayout) findViewById(R.id.middleButton);

        touch = (FrameLayout) findViewById(R.id.touch);
        spinner = (TextView) findViewById(R.id.spinner);

        spinner.setText("桌面");

        // let's set up a touch listener
        touch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_MOVE)
                    onMouseMove(ev);
                if (ev.getAction() == MotionEvent.ACTION_DOWN)
                    onMouseDown(ev);
                if (ev.getAction() == MotionEvent.ACTION_UP)
                    onMouseUp(ev);
                return true;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    onLeftButton("down");
                    leftButton.setBackgroundResource(R.drawable.zuoc);
                }
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    onLeftButton("release");
                    lbx = 0;
                    lby = 0;
                    leftButton.setBackgroundResource(R.drawable.zuo);
                }
                if (ev.getAction() == MotionEvent.ACTION_MOVE)
                    moveMouseWithSecondFinger(ev);
                return true;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    onRightButton("down");
                    rightButton.setBackgroundResource(R.drawable.youc);
                }
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    onRightButton("release");
                    rightButton.setBackgroundResource(R.drawable.you);
                }
                return true;
            }
        });
        middleButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN)
                    onMiddleButtonDown(ev);
                if (ev.getAction() == MotionEvent.ACTION_MOVE)
                    onMiddleButtonMove(ev);
                return true;
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("keyboard:key,Left,down");
                sendMessage("keyboard:key,Left,up");
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("keyboard:key,Right,down");
                sendMessage("keyboard:key,Right,up");
            }
        });

        spinner.setOnClickListener(view -> {
            initListPopupIfNeed();
            mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
            mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
            mListPopup.show(view);
        });

    }

    private void initListPopupIfNeed() {
        if (mListPopup == null) {

            List<String> datas = new ArrayList<>();
            datas.add("关机");
            datas.add("重启");
            datas.add("1小时后关机");
            datas.add("取消关机");
            datas.add("空格");
            datas.add("关闭页面");
            datas.add("桌面");

            List<String> cmds = new ArrayList<>();
            cmds.add("cmd:shutdown -s -t 3");
            cmds.add("cmd:shutdown -r -t 3");
            cmds.add("cmd:shutdown -s -t 3600");
            cmds.add("cmd:shutdown -a");
            cmds.add("keyboard:key,Space,click");
            cmds.add("keyboard:key,Alt,F4");
            cmds.add("keyboard:key,Ctrl,E");

            spinner.setText(datas.get(0));

            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, datas);

            mListPopup = new QMUIListPopup(this, QMUIPopup.DIRECTION_NONE, adapter);
            mListPopup.create(QMUIDisplayHelper.dp2px(this, 250), QMUIDisplayHelper.dp2px(this, 400), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner.setText(datas.get(i));
                    sendMessage(cmds.get(i));
                    mListPopup.dismiss();
                }
            });
        }
    }


    private void moveMouseWithSecondFinger(MotionEvent event) {
        int count = event.getPointerCount();
        if (count == 2) {
            if (lbx == 0 && lby == 0) {
                lbx = event.getX(1);
                lby = event.getY(1);
                return;
            }
            float x = event.getX(1);
            float y = event.getY(1);
            sendMouseEvent("mouse", x - lbx, y - lby);
            lbx = x;
            lby = y;
        }
        if (count == 1) {
            lbx = 0;
            lby = 0;
        }

    }

    private void onMouseDown(MotionEvent ev) {
        lx = ev.getX(); // 当手机第一放入时 把当前坐标付给lx
        ly = ev.getY();
        fx = ev.getX();
        fy = ev.getY();
    }

    private void onMouseMove(MotionEvent ev) {
        float x = ev.getX();
        mx = x - lx; // 当前鼠标位置 - 上次鼠标的位置
        lx = x; // 把当前鼠标的位置付给lx 以备下次使用
        float y = ev.getY();
        my = y - ly;
        ly = y;
        if (mx != 0 && my != 0)
            this.sendMouseEvent("mouse", mx, my);

    }

    private void onMouseUp(MotionEvent ev) {
        if (fx == ev.getX() && fy == ev.getY()) {
            sendMessage("leftButton:down");
            sendMessage("leftButton:release");
        }

    }

    private void sendMouseEvent(String type, float x, float y) {
        String str = type + ":" + x + "," + y;
        sendMessage(str);
    }

    private void onLeftButton(String type) {
        String str = "leftButton" + ":" + type;
        sendMessage(str);

    }

    private void onRightButton(String type) {
        String str = "rightButton" + ":" + type;
        sendMessage(str);
    }

    private void sendMessage(final String str) {
        new Thread(new InnerRunnable(str)).start();
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    void audioNeeds() {
        start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ControlActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO)
    void audioDenied() {
        showToast("请开启语音权限,去开启");
    }

    /**
     * 发送消息
     */
    private class InnerRunnable implements Runnable {

        String str = "";

        public InnerRunnable(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            try {
                // 首先创建一个DatagramSocket对象

                // 创建一个InetAddree
                InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
                byte data[] = str.getBytes();
                // 创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号
                DatagramPacket packet = new DatagramPacket(data, data.length,
                        serverAddress, Settings.scoketnum);
                // 调用socket对象的send方法，发送数据
                socket.send(packet);
                Log.i("TAG", "sendMessage: Srt:" + str);
            } catch (Exception e) {
                //e.printStackTrace();
                //Log.i("TAG", "sendMessage: e:" + e.toString());
            }
        }
    }

    private void onMiddleButtonDown(MotionEvent ev) {
        ly = ev.getY();

    }

    private void onMiddleButtonMove(MotionEvent ev) {
        // count++;

        float y = ev.getY();
        my = y - ly;
        ly = y;
        if (my > 3 || my < -3) { // 减少发送次数 滑轮移动慢点
            String str = "mousewheel" + ":" + my;
            sendMessage(str);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            sendMessage("keyboard:key,Down,down");
            sendMessage("keyboard:key,Down,up");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendMessage("keyboard:key,Up,down");
            sendMessage("keyboard:key,Up,up");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 0: 方案1， 唤醒词说完后，直接接句子，中间没有停顿。
     * >0 : 方案2： 唤醒词说完后，中间有停顿，然后接句子。推荐4个字 1500ms
     * <p>
     * backTrackInMs 最大 15000，即15s
     */
    private int backTrackInMs = 1500;

    /**
     * 唤醒监听
     */
    public class SimpleWakeupListener implements IWakeupListener {

        private static final String TAG = "SimpleWakeupListener";

        @Override
        public void onSuccess(String word, WakeUpResult result) {
            Logger.info(TAG, "唤醒成功，唤醒词：" + word);

            switch (word) {
                case "上一首":
                    sendMessage("keyboard:key,Alt,Left");
                    break;
                case "下一首":
                    sendMessage("keyboard:key,Alt,Right");
                    break;
                case "播放":
                    sendMessage("keyboard:key,Space,click");
                    break;
                case "暂停":
                    sendMessage("keyboard:key,Space,click");
                    break;
                case "停止":
                    sendMessage("keyboard:key,Space,click");
                    break;
                case "马上关机":
                    sendMessage("cmd:shutdown -s -t 3");
                    break;
                case "重启电脑":
                    sendMessage("cmd:shutdown -r -t 3");
                    break;
                case "返回桌面":
                    sendMessage("keyboard:key,Ctrl,E");
                    break;
                case "增大音量":
                    sendMessage("keyboard:key,Up,down");
                    sendMessage("keyboard:key,Up,up");
                    break;
                case "减小音量":
                    sendMessage("keyboard:key,Down,down");
                    sendMessage("keyboard:key,Down,up");
                    break;
            }

            // 此处 开始正常识别流程
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
            params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
            // 如识别短句，不需要需要逗号，使用1536搜索模型。其它PID参数请看文档
            params.put(SpeechConstant.PID, 1536);
            if (backTrackInMs > 0) { // 方案1， 唤醒词说完后，直接接句子，中间没有停顿。
                params.put(SpeechConstant.AUDIO_MILLS, System.currentTimeMillis() - backTrackInMs);

            }
            myRecognizer.cancel();
            myRecognizer.start(params);

        }

        @Override
        public void onStop() {
            Logger.info(TAG, "唤醒词识别结束：");
        }

        @Override
        public void onError(int errorCode, String errorMessge, WakeUpResult result) {
            Logger.info(TAG, "唤醒错误：" + errorCode + ";错误消息：" + errorMessge + "; 原始返回" + result.getOrigalJson());
        }

        @Override
        public void onASrAudio(byte[] data, int offset, int length) {
            Logger.error(TAG, "audio data： " + data.length);
        }

    }

    protected Handler handler;

    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;

    private void start() {

        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: msg" + msg);
            }
        };

//        SimpleWakeupListener listener = new SimpleWakeupListener();
//        // 改为 SimpleWakeupListener 后，不依赖handler，但将不会在UI界面上显示
//        myWakeup = new MyWakeup(this, listener);
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
//        // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
//
//        // params.put(SpeechConstant.ACCEPT_AUDIO_DATA,true);
//        // params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,true);
//        // params.put(SpeechConstant.IN_FILE,"res:///com/baidu/android/voicedemo/wakeup.pcm");
//        // params里 "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
//        myWakeup.start(params);


        // 初始化识别引擎

        StatusRecogListener recogListener = new MessageStatusRecogListener(handler);
        // 改为 SimpleWakeupListener 后，不依赖handler，但将不会在UI界面上显示
        myRecognizer = new MyRecognizer(this, recogListener);

        SimpleWakeupListener listener = new SimpleWakeupListener();
        myWakeup = new MyWakeup(this, listener);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下

        // params.put(SpeechConstant.ACCEPT_AUDIO_DATA,true);
        // params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,true);
        // params.put(SpeechConstant.IN_FILE,"res:///com/baidu/android/voicedemo/wakeup.pcm");
        // params里 "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下
        myWakeup.start(params);


    }

    protected void stop() {
        myWakeup.stop();
    }

    @Override
    protected void onDestroy() {
        stop();
        myRecognizer.stop();
        myWakeup.release();
        super.onDestroy();
    }


}
