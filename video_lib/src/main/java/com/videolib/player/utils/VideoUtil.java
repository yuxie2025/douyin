package com.videolib.player.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.view.View;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.videolib.R;
import com.videolib.player.SampleCoverVideo;
import com.videolib.player.VideoModel;

/**
 * @author llk
 * @time 2018/5/24 16:39
 * @class describe
 */
public class VideoUtil {

    /**
     * @param mContext
     * @param gsyVideoOptionBuilder
     * @param gsyVideoPlayer
     * @param videoModel            http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4
     */
    public static void onBind(final Context mContext, final GSYVideoOptionBuilder gsyVideoOptionBuilder, final SampleCoverVideo gsyVideoPlayer, VideoModel videoModel) {

        String url = videoModel.getUrl();
        String title = videoModel.getTitle();
        int position = videoModel.getPosition();
        String thumbImage = videoModel.getThumbImage();

        gsyVideoPlayer.loadCoverImage(thumbImage, R.drawable.xxx1);

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)//是否可以滑动界面改变进度，声音等
                .setUrl(url)//播放本地文件需要加file:///
                .setSetUpLazy(true)//lazy可以防止滑动卡顿
                .setVideoTitle(title)
                .setCacheWithPlay(true)//是否边缓存，m3u8等无效
                .setRotateViewAuto(true)//是否开启自动旋转
                .setLockLand(true)//一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
                .setPlayTag(videoModel.getTag())
                .setShowFullAnimation(true)//全屏动画
                .setNeedLockFull(true)//是否需要全屏锁定屏幕功能
                .setPlayPosition(position)
                .setHideKey(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(false);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(false);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);


        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(mContext, gsyVideoPlayer);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private static void resolveFullBtn(Context mContext, final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(mContext, true, true);
    }

}
