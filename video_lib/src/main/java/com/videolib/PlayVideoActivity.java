package com.videolib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.videolib.player.SampleCoverVideo;
import com.videolib.player.SampleVideo;
import com.videolib.player.VideoModel;
import com.videolib.utils.VideoUtil;

import java.util.IllegalFormatException;

public class PlayVideoActivity extends AppCompatActivity {

    SampleCoverVideo sampleCoverVideo;

    private static final String KEY_VIDEO_MODEL = "videoModel";

    public static void start(Activity activity, VideoModel videoModel) {
        if (videoModel == null) {
            Toast.makeText(activity, "请先传入播放信息!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(activity, PlayVideoActivity.class);
        intent.putExtra(KEY_VIDEO_MODEL, videoModel);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        sampleCoverVideo = findViewById(R.id.sampleCoverVideo);

        VideoModel videoModel = (VideoModel) getIntent().getSerializableExtra(KEY_VIDEO_MODEL);
        VideoUtil.onBind(this, new GSYVideoOptionBuilder(), sampleCoverVideo, videoModel);
        sampleCoverVideo.startPlayLogic();
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
