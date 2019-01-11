package com.yuxie.demo.novel;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.uitls.CommonUtils;
import com.baselib.uitls.NoBugLinearLayoutManager;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.yuxie.demo.R;
import com.yuxie.demo.api.ServerApiService;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.txt.Txt;
import com.yuxie.demo.txt.TxtActivity;
import com.yuxie.demo.txt.TxtDirActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchNovelActivity extends BaseActivity {

    @BindView(R.id.et_txt_name)
    EditText etTxtName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multi_state_view)
    MultiStateView multiStateView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private static final String KEY_KEY = "hKey";


    SearchNovelAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_novel;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("小说");

        String hKey = SPUtils.getInstance().getString(KEY_KEY);
        etTxtName.setText(hKey);

        recyclerViewInit();

    }

    private void recyclerViewInit() {

        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        adapter = new SearchNovelAdapter();
        recyclerView.setLayoutManager(new NoBugLinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((BaseQuickAdapter adapter1, View view, int position) -> {
            Txt item = adapter.getItem(position);
            //打开小说目录
            NovelDirActivity.start(SearchNovelActivity.this, item.getDirUrl(), item.getTxtName());
        });

    }


    @OnClick(R.id.btn_serach)
    public void onViewClicked() {

        String key = etTxtName.getText().toString().trim();

        if (TextUtils.isEmpty(key)) {
            showToast("请输入小说名称!");
            return;
        }

        SPUtils.getInstance().put(KEY_KEY, key);

        //根据关键字查询小说信息
        search(key);
    }

    private void search(String key) {
        mRxManager.add(ServerApi.getInstance().search(key)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<List<Txt>>>(mContext) {
                    @Override
                    protected void _onNext(BaseRespose<List<Txt>> txtBaseRespose) {
                        adapter.setNewData(txtBaseRespose.getData());
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                }));
    }
}
