package com.audio;

import android.view.View;

import com.audio.control.MyWakeup;
import com.audio.recognization.IStatus;
import com.audio.wakeup.IWakeupListener;
import com.audio.wakeup.RecogWakeupListener;
import com.baidu.speech.asr.SpeechConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 集成文档： http://ai.baidu.com/docs#/ASR-Android-SDK/top 集成指南一节
 * 唤醒词功能
 */
public class ActivityWakeUp extends ActivityCommon implements IStatus {
    {
        layout = R.layout.common_without_setting;
        descText = "唤醒词功能即SDK识别唤醒词，或者认为是SDK识别出用户一大段话中的\"关键词\"。"
                + " 与Android系统自身的锁屏唤醒无关\n"
                + "唤醒词是纯离线功能，需要获取正式授权文件（与离线命令词的正式授权文件是同一个）。\n"
                + " 第一次联网使用唤醒词功能自动获取正式授权文件。之后可以断网测试\n"
                + "请说“小度你好”或者 “百度一下”\n\n"
                + "集成指南：如果无法正常使用请检查正式授权文件问题:\n"
                + " 1. 是否在AndroidManifest.xml配置了APP_ID\n"
                + " 2. 是否在开放平台对应应用绑定了包名, 本demo的包名是com.baidu.speech.recognizerdemo"
                + "定义在build.gradle文件中。\n\n";
    }

    private static final String TAG = "ActivityWakeUp";
    protected MyWakeup myWakeup;

    private int status = STATUS_NONE;

    @Override
    protected void initRecog() {
        IWakeupListener listener = new RecogWakeupListener(handler);
        // 改为 SimpleWakeupListener 后，不依赖handler，但将不会在UI界面上显示
        myWakeup = new MyWakeup(this, listener);
    }

    private void start() {
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

    protected void initView() {
        super.initView();
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (status) {
                    case STATUS_NONE:
                        start();
                        status = STATUS_WAITING_READY;
                        updateBtnTextByStatus();
                        txtLog.setText("");
                        txtResult.setText("");
                        break;
                    case STATUS_WAITING_READY:
                        stop();
                        status = STATUS_NONE;
                        updateBtnTextByStatus();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void updateBtnTextByStatus() {
        switch (status) {
            case STATUS_NONE:
                btn.setText("启动唤醒");
                break;
            case STATUS_WAITING_READY:
                btn.setText("停止唤醒");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        myWakeup.release();
        super.onDestroy();
    }
}
