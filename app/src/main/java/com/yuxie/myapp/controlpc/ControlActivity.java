package com.yuxie.myapp.controlpc;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.commonutils.LogUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogMenuItemView;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import com.yuxie.myapp.R;
import com.yuxie.myapp.base.CommonAdapter;
import com.yuxie.myapp.base.ViewHolder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlActivity extends Activity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        initTouch();
    }

    private void initTouch() {

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
                e.printStackTrace();
                Log.i("TAG", "sendMessage: e:" + e.toString());
            }
        }
    }


    private void onMiddleButtonDown(MotionEvent ev) {
        ly = ev.getY();
    }

    private void onMiddleButtonMove(MotionEvent ev) {
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

}
