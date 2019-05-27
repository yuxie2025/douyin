package com.yuxie.demo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.videolib.player.VideoModel;
import com.videolib.player.ViewPageVideo;
import com.videolib.utils.VideoUtil;
import com.yuxie.demo.R;


/**
 * Created by Lankun on 2019/05/24
 */
public class PlayVideosAdapter extends BaseQuickAdapter<VideoModel, BaseViewHolder> {

    public PlayVideosAdapter() {
        super(R.layout.item_play_videos_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoModel item) {
        ViewPageVideo video = helper.getView(R.id.video);
        VideoUtil.onBind(mContext, new GSYVideoOptionBuilder(), video, item);
    }
}
