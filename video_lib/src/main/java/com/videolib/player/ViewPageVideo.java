package com.videolib.player;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.videolib.R;

/**
 * Created by Lankun on 2019/05/24
 */
public class ViewPageVideo extends StandardGSYVideoPlayer {

    ImageView mCoverImage;
    ImageView play;

    String mCoverOriginUrl;

    int mDefaultRes;

    private static long lastClickTime = 0;

    public ViewPageVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public ViewPageVideo(Context context) {
        super(context);
    }

    public ViewPageVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);

        mCoverImage = findViewById(R.id.thumbImage);
        play = findViewById(R.id.play);

        if (mCoverImage != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mCoverImage.setVisibility(VISIBLE);
        }

        findViewById(R.id.rl_play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick(500)) {
                    return;
                }
                if (mCurrentState == CURRENT_STATE_PAUSE) {
                    GSYVideoManager.onResume();
                    play.setVisibility(GONE);
                } else if (mCurrentState == CURRENT_STATE_PLAYING) {
                    GSYVideoManager.onPause();
                    play.setVisibility(VISIBLE);
                } else {
                    startPlayLogic();
                    play.setVisibility(GONE);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_page_video_layout;
    }

    public void loadCoverImage(String url, int res) {
        mCoverOriginUrl = url;
        mDefaultRes = res;

        Glide.with(getContext().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(mCoverOriginUrl)
                .into(mCoverImage);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, false, false);
        SampleCoverVideo sampleCoverVideo = (SampleCoverVideo) gsyBaseVideoPlayer;
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        return gsyBaseVideoPlayer;
    }

    /**
     * @param delayTime 间隔时间(自定义间隔时间)
     * @return 结果
     */
    public static boolean isDoubleClick(long delayTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > delayTime || currentTime - lastClickTime < 0) {
            lastClickTime = currentTime;
            return false;
        }
        return true;
    }
}
