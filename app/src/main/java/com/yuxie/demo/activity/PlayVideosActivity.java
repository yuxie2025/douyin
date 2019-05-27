package com.yuxie.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.videolib.player.SampleCoverVideo;
import com.videolib.player.VideoModel;
import com.videolib.player.ViewPageVideo;
import com.yuxie.demo.R;
import com.yuxie.demo.adapter.PlayVideosAdapter;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.bean.VideoListBean;
import com.yuxie.demo.widget.OnViewPagerListener;
import com.yuxie.demo.widget.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlayVideosActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.multi_state_view)
    MultiStateView multiStateView;

    PlayVideosAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_videos;
    }

    protected void setStatusBarColor() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        recyclerViewInit();

        getData();

    }

    private void getData() {

        mRxManager.add(ServerApi.getInstance().videoList().compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<List<VideoListBean>>>(mContext, false) {
                    @Override
                    protected void _onNext(BaseRespose<List<VideoListBean>> baseRespose) {
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }

                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        List<VideoModel> datas = new ArrayList<>();
                        VideoModel model;
                        for (int i = 0; i < baseRespose.getData().size(); i++) {
                            model = new VideoModel();
                            model.setUrl(baseRespose.getData().get(i).getUrl());
                            model.setThumbImage(baseRespose.getData().get(i).getThumbImageUrl());
                            datas.add(model);
                        }
                        adapter.setNewData(datas);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }
                        showToast(message);
                    }
                }));

    }

    int oldPosition;

    private void recyclerViewInit() {

        swipeRefresh.setOnRefreshListener(() -> {
            getData();
        });

        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(mContext, LinearLayout.VERTICAL);

        adapter = new PlayVideosAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index = 0;
                if (!isNext) {
                    index = 1;
                }
                releaseVideo(index);

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (oldPosition == position) {
                    return;
                }
                oldPosition = position;
                playVideo(position);
            }
        });

    }

    private void playVideo(int position) {
        try {
            View itemView = recyclerView.getChildAt(0);
            ViewPageVideo videoView = itemView.findViewById(R.id.video);
            videoView.startPlayLogic();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void releaseVideo(int index) {
        try {
            View itemView = recyclerView.getChildAt(index);
            ViewPageVideo videoView = itemView.findViewById(R.id.video);
            videoView.release();
        } catch (Exception e) {
            //e.printStackTrace();
        }
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
