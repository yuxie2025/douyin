package com.yuxie.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.baselib.base.BaseActivity;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.kennyc.view.MultiStateView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.videolib.player.VideoModel;
import com.videolib.player.ViewPageVideo;
import com.videolib.utils.VideoThumbnailUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.adapter.PlayVideosAdapter;
import com.yuxie.demo.widget.OnViewPagerListener;
import com.yuxie.demo.widget.ViewPagerLayoutManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class NativeVideosActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multi_state_view)
    MultiStateView multiStateView;

    PlayVideosAdapter adapter;

    private static final String KEY_DIR_PATH = "dirPath";


    public static void start(Activity activity) {
        start(activity, "");
    }

    public static void start(Activity activity, String dirPath) {
        Intent intent = new Intent(activity, NativeVideosActivity.class);
        intent.putExtra(KEY_DIR_PATH, dirPath);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_native_videos;
    }

    protected void setStatusBarColor() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        recyclerViewInit();

        NativeVideosActivityPermissionsDispatcher.readStorageNeedsWithPermissionCheck(this);

    }


    private List<VideoModel> getData() {

        List<VideoModel> datas = new ArrayList<>();

        String dirPath = getIntent().getStringExtra(KEY_DIR_PATH);
        if (!TextUtils.isEmpty(dirPath)) {
            List<VideoModel> models = getVideoModes(dirPath);
            datas.addAll(models);
        } else {
            List<String> paths = getDyPath();
            String dyPath = "";
            for (int i = 0; i < paths.size(); i++) {
                dyPath = paths.get(i);
                List<VideoModel> models = getVideoModes(dyPath);
                datas.addAll(models);
            }
        }
        return datas;
    }

    FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            //过虐 小文件
            long fileSize = file.length();
            if (fileSize <= 1 * 1024) {
                return false;
            }

            if (!TextUtils.isEmpty(file.getName())) {
                return true;
            }
            return false;
        }
    };


    List<VideoModel> getVideoModes(String dirPath) {
        List<VideoModel> re = new ArrayList<>();
        List<File> files = FileUtils.listFilesInDirWithFilter(dirPath, filter);
        VideoModel model;
        for (int i = 0; i < files.size(); i++) {
            if (i == 20) {
                showToast("文件较多,请耐心等待...");
            }
            model = new VideoModel();
            model.setUrl(files.get(i).getAbsolutePath());
            model.setThumbImage(VideoThumbnailUtils.getVideoThumbnail(mContext.getApplicationContext(), files.get(i).getAbsolutePath()));
            re.add(model);
        }
        return re;
    }

    List<String> getDyPath() {
        String dyPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cache";
        String dyPath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android/data/com.ss.android.ugc.aweme/cache/cachev2";
        List<String> re = new ArrayList<>();
        re.add(dyPath1);
        re.add(dyPath2);
        return re;
    }

    int oldPosition;

    private void recyclerViewInit() {

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
        View itemView = recyclerView.getChildAt(0);
        ViewPageVideo videoView = itemView.findViewById(R.id.video);
        videoView.startPlayLogic();
    }

    private void releaseVideo(int index) {
        View itemView = recyclerView.getChildAt(index);
        ViewPageVideo videoView = itemView.findViewById(R.id.video);
        videoView.release();
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

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorageNeeds() {

        new Thread(() -> {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            List<VideoModel> datas = getData();
            runOnUiThread(() -> {
                adapter.setNewData(datas);
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            });
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NativeVideosActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorageDenied() {
        suggestSetting("应用需要读取文件权限,去设置?");
    }


}
