package com.yuxie.demo.dq;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.baselib.base.BaseActivity;
import com.baselib.ui.widget.ClearableEditTextWithIcon;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.yuxie.demo.R;
import com.yuxie.demo.greendao.LikeReBeanDao;
import com.yuxie.demo.greendao.UserBeanDao;
import com.yuxie.demo.utils.db.EntityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

public class DqActivity extends BaseActivity {

    @BindView(R.id.videoId)
    ClearableEditTextWithIcon videoId;
    @BindView(R.id.number)
    ClearableEditTextWithIcon number;

    @BindView(R.id.exeTask)
    AppCompatButton exeTask;

    UserBeanDao userBeanDao = EntityManager.getInstance().getUserBeanDao();
    LikeReBeanDao likeReDao = EntityManager.getInstance().getLikeReBeanDao();

    List<String> urls = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dq;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("多奇视频");

    }

    @OnClick({R.id.exeTask, R.id.importToken, R.id.clearToken, R.id.addUrl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exeTask:
                exeTask();
                break;
            case R.id.importToken:
                importToken();
                break;
            case R.id.clearToken:
                clearToken();
                break;
            case R.id.addUrl:
                addUrl();
                break;
        }
    }

    private void addUrl() {

        String videoIdStr = CommonUtils.getViewContent(videoId);

        if (TextUtils.isEmpty(videoIdStr)) {
            showToast("请输入url");
            return;
        }

        //http://mz.qiaosong.net:8080/sheding/shareVideo?videoId=901770&userId=4bf4e573e0064f2d9c1642c5bf8a20cd&from=singlemessage
        if (videoIdStr.contains("videoId=")) {
            videoIdStr = CommonUtils.getMatches(videoIdStr, "videoId=(.+?)&");
        }

        if (!isAdd(videoIdStr)) {
            urls.add(videoIdStr);
            showToast("添加成功:" + videoIdStr);
        } else {
            showToast("已添加过");
        }

    }

    private boolean isAdd(String url) {
        boolean re = false;
        for (int i = 0; i < urls.size(); i++) {
            if (urls.get(i).equals(url)) {
                re = true;
                break;
            }
        }
        return re;
    }

    //导入token
    private void importToken() {

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "dq.txt";
        List<String> users = getUser(filePath);

        List<UserBean> datas = userBeanDao.loadAll();

        List<UserBean> insertData = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            boolean isEqual = false;
            for (int j = 0; j < datas.size(); j++) {
                if (users.get(i).equals(datas.get(j).getToken())) {
                    isEqual = true;
                }
            }
            if (!isEqual) {
                insertData.add(new UserBean(null, users.get(i)));
            }
        }
        userBeanDao.insertInTx(insertData);

        showToast("插入token完成,插入成功" + insertData.size() + "条");

    }

    //清除token
    private void clearToken() {
        userBeanDao.deleteAll();
        showToast("清除token完成!");
    }

    //获取文本token内容
    private List<String> getUser(String filePath) {

        List<String> users = new ArrayList<>();

        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");
        if (datas.size() == 0) {
            return users;
        }
        String data = "";
        for (int i = 0; i < datas.size(); i++) {
            data = datas.get(i).trim();

            if (data == null || data == "") {
                continue;
            }

            // 去除前面的影响字符
            data = data.substring(data.indexOf("{"), data.length());

            users.add(data);
        }
        return users;
    }

    //执行点赞任务
    private void exeTask() {

        String numberStr = CommonUtils.getViewContent(number);

        if (urls.size() == 0) {
            showToast("请先添加视频url");
            return;
        }

        isTask = !isTask;
        if (isTask) {
            exeTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                String videoIdStr;
                for (int i = 0; i < urls.size(); i++) {
                    videoIdStr = urls.get(i);
                    exeLikeTask(numberStr, videoIdStr);
                }
                stopTask();
            });
            thread.start();
        } else {
            exeTask.setText("开始任务");
        }
    }

    boolean isTask = false;

    private void exeLikeTask(String numberStr, String videoIdStr) {

        List<User> users = getRandomUser(videoIdStr, CommonUtils.string2Int(numberStr));

        for (int i = 0; i < users.size(); i++) {
            if (!isTask) {
                interruptTask();
                return;
            }
            boolean re = DqTest.exeDianZanTask(i, users.get(i), videoIdStr);
            if (re) {
                likeReDao.insert(new LikeReBean(null, videoIdStr, users.get(i).getData().getToken()));
            }
        }
    }

    private void interruptTask() {
        runOnUiThread(() -> {
            showToast("任务提前结束");
            if (exeTask != null) {
                exeTask.setText("开始任务");
            }
        });

    }

    private void stopTask() {
        runOnUiThread(() -> {
            showToast("任务完成");
            isTask = false;
            if (exeTask != null) {
                exeTask.setText("开始任务");
            }
        });

    }

    private List<User> getRandomUser(String videoIdStr, int number) {
        List<User> re = new ArrayList<>();
        List<User> allUser = getAllUser();
        Collections.shuffle(allUser);//随机排序

        int addNumber = 0;
        for (int i = 0; i < allUser.size(); i++) {

            if (addNumber == number) {
                break;
            }

            User user = allUser.get(i);
            Long count = likeReDao.queryBuilder().where(LikeReBeanDao.Properties.VoideoId.eq(videoIdStr),
                    LikeReBeanDao.Properties.Token.eq(user.getData().getToken())).count();
            if (count != 0) {
                continue;
            }
            addNumber++;
            re.add(user);
        }
        return re;
    }

    private List<User> getAllUser() {
        List<User> re = new ArrayList<>();
        List<UserBean> datas = userBeanDao.loadAll();
        User user;
        for (int i = 0; i < datas.size(); i++) {
            user = new Gson().fromJson(datas.get(i).getToken(), User.class);
            re.add(user);
        }
        return re;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isTask = false;
    }
}
