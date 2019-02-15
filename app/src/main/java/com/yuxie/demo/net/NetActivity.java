package com.yuxie.demo.net;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.api.UrlUtils;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.base.MyBaseActivity;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.Result;

public class NetActivity extends MyBaseActivity {

    @BindView(R.id.et_url)
    AppCompatEditText etUrl;
    @BindView(R.id.ed_time)
    AppCompatEditText edTime;
    @BindView(R.id.ed_thread_number)
    AppCompatEditText edThreadNumber;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private ThreadPoolExecutor poolExecutor;
    private long timeLong;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.start, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.stop:
                if (poolExecutor != null) {
                    poolExecutor.shutdownNow();
                }
                break;
        }
    }

    private void start() {

        String url = CommonUtils.getViewContent(etUrl);

        String time = CommonUtils.getViewContent(edTime);
        String threadNumber = CommonUtils.getViewContent(edThreadNumber);

        int threadNumberLong = CommonUtils.string2Int(threadNumber);
        if (threadNumberLong == 0) {
            threadNumberLong = 1;
        }

        timeLong = CommonUtils.string2Long(time);
        if (timeLong == 0) {
            timeLong = 1000;
        }

        poolExecutor = new ThreadPoolExecutor(threadNumberLong, 10, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        Runnable runnable = new InnerRunnable(url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000 * 1000; i++) {
                        if (poolExecutor.isShutdown()) {
                            return;
                        }
                        poolExecutor.execute(runnable);
                        Thread.sleep(timeLong);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class InnerRunnable implements Runnable {

        String url;

        InnerRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {

            try {
                net(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void net(String url) {
        String[] urls = UrlUtils.string2Url(url);

        mRxManager.add(ServerApi.getInstance(urls[0]).getUrl(urls[1]).compose(RxSchedulers.io_main()).subscribe(new RxSubscriber<String>(mContext, false) {

            @Override
            protected void _onNext(String stringResult) {
                LogUtils.d("stringResult:" + stringResult);
                if (tvRecord != null) {
                    //设置ScrollView滚动到顶部
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    tvRecord.append("成功一次!\n");
                }
            }

            @Override
            protected void _onError(String message) {
                LogUtils.d("e:" + message);
                if (tvRecord != null) {
                    //设置ScrollView滚动到顶部
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    tvRecord.append("失败一次:" + message + "\n");
                }
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (poolExecutor != null) {
            poolExecutor.shutdownNow();
        }
    }
}
