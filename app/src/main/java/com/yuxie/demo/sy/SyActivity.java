package com.yuxie.demo.sy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;

import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.ui.widget.ClearableEditTextWithIcon;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyActivity extends BaseActivity {

    @BindView(R.id.videoId)
    ClearableEditTextWithIcon videoId;
    @BindView(R.id.bigDayTask)
    AppCompatButton bigDayTask;
    @BindView(R.id.smallDayTask)
    AppCompatButton smallDayTask;
    @BindView(R.id.readTask)
    AppCompatButton readTask;
    @BindView(R.id.readTaskOne)
    AppCompatButton readTaskOne;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("三言");

    }

    @OnClick({R.id.bigDayTask, R.id.smallDayTask, R.id.readTask, R.id.readTaskOne})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bigDayTask:
                bigDayTask();
                break;
            case R.id.smallDayTask:
                smallDayTask();
                break;
            case R.id.readTask:
                readTask();
                break;
            case R.id.readTaskOne:
                readTaskOne();
                break;
        }
    }

    boolean isReadTaskOne = false;

    private void readTaskOne() {

        String newsId = videoId.getText().toString();

        if (TextUtils.isEmpty(newsId)) {
            showToast("请输入文章id");
            return;
        }
        if (newsId.length() != 6) {
            showToast("请输入正确文章id");
            return;
        }

        isReadTaskOne = !isReadTaskOne;
        if (isReadTaskOne) {
            readTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                Sy.readTask(newsId);
                runOnUiThread(() -> {
                    try {
                        showToast("阅读任务完成!");
                        readTask.setText("阅读任务(指定文章)");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            thread.start();
        } else {
            readTask.setText("阅读任务(指定文章)");
        }
    }


    //执行点赞任务
    boolean isReadTask = false;

    private void readTask() {
        isReadTask = !isReadTask;
        if (isReadTask) {
            readTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                String filePath = Sy.BASE_PATH + "day_token.txt";
                List<User> users = Sy.getUser(filePath);
                String token;
                for (int i = 0; i < users.size(); i++) {
                    if (!isReadTask) {
                        return;
                    }
                    token = users.get(i).getToken();
                    readNewsTask((i + 1), token);
                }
                runOnUiThread(() -> {
                    try {
                        showToast("阅读任务完成!");
                        readTask.setText("阅读任务");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            thread.start();
        } else {
            readTask.setText("阅读任务");
        }
    }

    public static void readNewsTask(int k, String token) {
        int number = 0;
        String newsId = "";
        List<NewsListBean.DataBean> datas = Sy.mineContens(token);
        for (int i = 0; i < datas.size(); i++) {
            if ("2".equals(datas.get(i).getStatus())) {
                number++;
                if (number > 4) {
                    break;
                }
                newsId = datas.get(i).getId();
                System.out.println("账号" + k + ",文章id:" + newsId);
                Sy.readTask(newsId);
            }
        }
    }

    //执行点赞任务
    boolean isBigDayTask = false;

    private void bigDayTask() {
        isBigDayTask = !isBigDayTask;
        if (isBigDayTask) {
            bigDayTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                exeBigDayTask();
                stopDayTask(true);
            });
            thread.start();
        } else {
            stopDayTask(true);
        }
    }

    // 大号每天任务
    public void exeBigDayTask() {
        String filePath = Sy.BASE_PATH + "day_token.txt";
        List<User> users = Sy.getUser(filePath);
        String token;
        for (int i = 0; i < users.size(); i++) {
            if (!isBigDayTask) {
                return;
            }
            token = users.get(i).getToken();
            dayTaskList(i, token, true);
        }
    }

    //执行点赞任务
    boolean isSmallDayTask = false;

    private void smallDayTask() {
        isSmallDayTask = !isSmallDayTask;
        if (isSmallDayTask) {
            smallDayTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                exeSmallDayTask();
                stopDayTask(false);
            });
            thread.start();
        } else {
            stopDayTask(false);
        }
    }

    // 大号每天任务
    public void exeSmallDayTask() {
        String filePath = Sy.BASE_PATH + "token.txt";
        List<User> users = Sy.getUser(filePath);
        String token;
        for (int i = 0; i < users.size(); i++) {
            if (!isSmallDayTask) {
                return;
            }
            token = users.get(i).getToken();
            dayTaskList(i, token, true);
        }
    }

    private void dayTaskList(int i, String token, boolean isBig) {
        System.out.println("第" + (i + 1) + "次日常任务");

        Sy.dayTask(mContext, token, isBig);
        // 领取任务
        Sy.missions(token);
        // 收取盐丸
        Sy.userPendingTAll(token);
        if (isBig) {
            // 收取回馈
            Sy.userPendingCashes(token);
        }
    }

    private void stopDayTask(boolean isBig) {
        runOnUiThread(() -> {
            try {
                if (isBig) {
                    showToast("大号每日任务完成");
                    bigDayTask.setText("大号每日任务");
                } else {
                    showToast("小号每日任务完成");
                    smallDayTask.setText("小号每日任务");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
