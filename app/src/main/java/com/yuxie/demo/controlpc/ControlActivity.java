package com.yuxie.demo.controlpc;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


import com.yuxie.demo.R;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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

    private Button btn_left;
    private Button btn_right;

    Handler handler = new Handler();
    Runnable leftButtonDown;
    Runnable leftButtonRealease;
    Runnable rightButtonDown;
    Runnable rightButtonRealease;

    private DatagramSocket socket;
    private boolean isUSLR = true;

    private int count = 0;
    private long firClick = 0;
    private long secClick = 0;
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int interval = 150;

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

        FrameLayout touch = (FrameLayout) this.findViewById(R.id.touch);

        // let's set up a touch listener
        touch.setOnTouchListener(new View.OnTouchListener() {
            long hTime = 0L;

            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    onMouseDown(ev);
                }
                if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                    onMouseMove(ev);
                }

                return true;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    onLeftButton("down");
                    handler.post(leftButtonDown);
                }
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    onLeftButton("release");
                    lbx = 0;
                    lby = 0;
                    handler.post(leftButtonRealease);
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
                    handler.post(rightButtonDown);
                }
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    onRightButton("release");
                    handler.post(rightButtonRealease);
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

        this.leftButtonDown = new Runnable() {
            public void run() {
                drawLeftButtonDown(leftButton);
            }

            private void drawLeftButtonDown(FrameLayout fl) {
                fl.setBackgroundResource(R.drawable.zuoc);
            }
        };

        this.rightButtonDown = new Runnable() {
            public void run() {
                drawButtonDown(rightButton);
            }

            private void drawButtonDown(FrameLayout fl) {
                fl.setBackgroundResource(R.drawable.youc);
            }
        };

        this.leftButtonRealease = new Runnable() {
            public void run() {
                drawLeftButtonRealease(leftButton);
            }

            private void drawLeftButtonRealease(FrameLayout fl) {
                fl.setBackgroundResource(R.drawable.zuo);

            }
        };

        this.rightButtonRealease = new Runnable() {
            public void run() {
                drawButtonRealease(rightButton);
            }

            private void drawButtonRealease(FrameLayout fl) {
                fl.setBackgroundResource(R.drawable.you);

            }
        };

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
//        else if( keyCode== KeyEvent.KEYCODE_HOME){
//            return true;
//        } else if( keyCode== KeyEvent.KEYCODE_BACK){
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

}
