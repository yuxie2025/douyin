package com.yuxie.demo.sy;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;

import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.baselib.ui.widget.ClearableEditTextWithIcon;
import com.blankj.utilcode.util.LogUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.HostType;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.base.MyBaseActivity;
import com.yuxie.demo.entity.UrlLibBean;
import com.yuxie.demo.entity.UrlLibraryBean;
import com.yuxie.demo.greendao.UrlLibraryBeanDao;
import com.yuxie.demo.utils.db.EntityManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyActivity extends MyBaseActivity {

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
    @BindView(R.id.sendNews)
    AppCompatButton sendNews;
    @BindView(R.id.sendVideo)
    AppCompatButton sendVideo;

    UrlLibraryBeanDao urlLibraryBeanDao = EntityManager.getInstance().getUrlLibraryBeanDao();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("三言");

    }

    @OnClick({R.id.bigDayTask, R.id.smallDayTask, R.id.readTask, R.id.readTaskOne, R.id.sendNews, R.id.sendVideo, R.id.getLib})
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
            case R.id.sendNews:
                sendNews();
                break;
            case R.id.sendVideo:
                sendVideo();
                break;
            case R.id.getLib:
                getLib();
                break;
        }
    }

    private void getLib() {

        ServerApi.getInstance(HostType.HOST_TYPE_SY).getUrlLib()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose<List<UrlLibBean>>>(mContext, false) {
                    @Override
                    protected void _onNext(BaseRespose<List<UrlLibBean>> baseRespose) {
                        if (baseRespose.isSuccess()) {
                            insertData(baseRespose.getData());
                        }
                        showToast("导入成功!");
                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtils.d("message:" + message);
                        showToast(message);
                    }
                });

    }

    private void insertData(List<UrlLibBean> data) {

        UrlLibBean bean;
        for (int i = 0; i < data.size(); i++) {
            bean = data.get(i);
            long count = urlLibraryBeanDao.queryBuilder().where(UrlLibraryBeanDao.Properties.Url.eq(bean.getUrl())).count();
            if (count == 0) {
                urlLibraryBeanDao.insertOrReplace(new UrlLibraryBean(null, bean.getType(), bean.getUrl(), false));
            }
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


    boolean isSendNews = false;

    private void sendNews() {

        isSendNews = !isSendNews;
        if (isSendNews) {
            sendNews.setText("结束任务");
            Thread thread = new Thread(() -> {

                String filePath = Sy.BASE_PATH + "day_token.txt";
                List<User> users = Sy.getUser(filePath);

                User user;
                for (int i = 0; i < users.size(); i++) {
                    LogUtils.d("j:" + i + ",users.size():" + users.size());
                    user = users.get(i);
                    UrlLibraryBean url;
                    int total = 0;
                    List<UrlLibraryBean> urls = urlLibraryBeanDao.queryBuilder().where(UrlLibraryBeanDao.Properties.IsUse.eq(false), UrlLibraryBeanDao.Properties.Type.eq(1)).list();
                    for (int j = 0; j < urls.size(); j++) {
                        LogUtils.d("j:" + j + ",urls.size():" + urls.size());
                        if (!isSendNews) {
                            runOnUiThread(() -> {
                                try {
                                    showToast("发布新闻任务完成!");
                                    sendNews.setText("发布新闻");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            return;
                        }

                        if (total > 5) {
                            break;
                        }
                        url = urls.get(j);
                        String re = Sy.sendNews(url.getUrl(), user.getToken());
                        LogUtils.d("re:" + re);
                        if (!TextUtils.isEmpty(re) && re.contains("成功")) {
                            total++;
                        }
                        if (!TextUtils.isEmpty(re) && re.contains("请明日再来")) {
                            break;
                        }

                        UrlLibraryBean updataBean = url;
                        updataBean.setIsUse(true);
                        urlLibraryBeanDao.insertOrReplace(updataBean);
                        CommonUtils.random(1, 2);
                    }
                }

                runOnUiThread(() -> {
                    try {
                        showToast("发布新闻任务完成!");
                        sendNews.setText("发布新闻");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            thread.start();
        } else {
            sendNews.setText("发布新闻");
        }
    }

    boolean isSendVideo = false;

    private void sendVideo() {

        isSendVideo = !isSendVideo;
        if (isSendVideo) {
            sendVideo.setText("结束任务");
            Thread thread = new Thread(() -> {

                String filePath = Sy.BASE_PATH + "day_token.txt";
                List<User> users = Sy.getUser(filePath);

                User user;
                for (int i = 0; i < users.size(); i++) {
                    user = users.get(i);
                    UrlLibraryBean url;
                    int total = 0;
                    List<UrlLibraryBean> urls = urlLibraryBeanDao.queryBuilder().where(UrlLibraryBeanDao.Properties.IsUse.eq(false), UrlLibraryBeanDao.Properties.Type.eq(2)).list();
                    for (int j = 0; j < urls.size(); j++) {
                        if (!isSendVideo) {
                            runOnUiThread(() -> {
                                try {
                                    showToast("发布视频任务完成!");
                                    sendVideo.setText("发布视频");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            return;
                        }

                        if (total > 4) {
                            break;
                        }
                        url = urls.get(j);
                        boolean re = Sy.sendVideo(url.getUrl(), user.getToken());
                        if (re) {
                            total++;
                        }
                        UrlLibraryBean updataBean = url;
                        updataBean.setIsUse(true);
                        urlLibraryBeanDao.insertOrReplace(updataBean);
                        CommonUtils.random(1, 2);
                    }
                }

                runOnUiThread(() -> {
                    try {
                        showToast("发布视频任务完成!");
                        sendVideo.setText("发布视频");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            thread.start();
        } else {
            sendVideo.setText("发布视频");
        }
    }


}
