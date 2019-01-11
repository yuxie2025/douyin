package com.yuxie.demo.novel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.uitls.NoBugLinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.txt.Txt;
import com.yuxie.demo.txt.TxtDir;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NovelDirActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multi_state_view)
    MultiStateView multiStateView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    NovelDirAdapter adapter;

    private static final String KEY_URL = "URL";
    private static final String KEY_NAME = "TextName";

    public static void start(Activity activity, String url, String textName) {
        Intent intent = new Intent(activity, NovelDirActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_NAME, textName);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_novel_dir;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String title = getIntent().getStringExtra(KEY_NAME);
        setTitle(title);

        recyclerViewInit();

        dir();

    }

    private void recyclerViewInit() {

        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        adapter = new NovelDirAdapter();
        recyclerView.setLayoutManager(new NoBugLinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((BaseQuickAdapter adapter1, View view, int position) -> {
            TxtDir item = adapter.getItem(position);
            getContent(item.getTitleDir(), item.getTitle());
        });

    }

    private void dir() {
        String url = getIntent().getStringExtra(KEY_URL);
        mRxManager.add(ServerApi.getInstance().dir(url)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<List<TxtDir>>>(mContext) {
                    @Override
                    protected void _onNext(BaseRespose<List<TxtDir>> txtBaseRespose) {
                        adapter.setNewData(txtBaseRespose.getData());
                    }

                    @Override
                    protected void _onError(String message) {
                        showToast(message);
                    }
                }));
    }

    private void getContent(String url, String title) {
        mRxManager.add(ServerApi.getInstance().getContent(url)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<String>>(mContext) {
                    @Override
                    protected void _onNext(BaseRespose<String> txtBaseRespose) {
                        String content = txtBaseRespose.getData();
                        if (TextUtils.isEmpty(content)) {
                            showToast("没有获取到内容");
                            return;
                        }
                        ReadNovelActivity.start(NovelDirActivity.this, title, content);
                    }

                    @Override
                    protected void _onError(String message) {
                        showToast(message);
                    }
                }));
    }


    @OnClick(R.id.btn_sort)
    public void onViewClicked() {
        List<TxtDir> data = adapter.getData();
        Collections.reverse(data);
        adapter.setNewData(data);
    }
}
