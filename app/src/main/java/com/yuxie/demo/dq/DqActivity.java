package com.yuxie.demo.dq;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.baselib.base.BaseActivity;
import com.baselib.uitls.CommonUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.yuxie.demo.R;
import com.yuxie.demo.greendao.UserBeanDao;
import com.yuxie.demo.utils.db.EntityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DqActivity extends BaseActivity {

    @BindView(R.id.videoId)
    AppCompatEditText videoId;
    @BindView(R.id.exeTask)
    AppCompatButton exeTask;

    UserBeanDao userBeanDao = EntityManager.getInstance().getUserBeanDao();
    @BindView(R.id.number)
    AppCompatEditText number;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dq;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("多奇视频");

    }

    @OnClick({R.id.exeTask, R.id.importToken, R.id.clearToken})
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
        }
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
                insertData.add(new UserBean(null, users.get(i).toString()));
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

        String videoIdStr = CommonUtils.getViewContent(videoId);

        isTask = !isTask;
        if (isTask) {
            exeTask.setText("结束任务");
            Thread thread = new Thread(() -> {
                exeLikeTask(numberStr, videoIdStr);
            });
            thread.start();
        } else {
            exeTask.setText("开始任务");
        }
    }

    boolean isTask = false;

    private void exeLikeTask(String numberStr, String videoIdStr) {

        //http://mz.qiaosong.net:8080/sheding/shareVideo?videoId=901770&userId=4bf4e573e0064f2d9c1642c5bf8a20cd&from=singlemessage
        if (videoIdStr.contains("videoId=")) {
            List<String> str = RegexUtils.getMatches("videoId=(.*)&", videoIdStr);
            videoIdStr = str.get(0);
        }

        List<User> users = getRandomUser(CommonUtils.string2Int(numberStr));

        for (int i = 0; i < users.size(); i++) {
            if (!isTask) {
                runOnUiThread(() -> {
                    showToast("任务提前结束");
                    exeTask.setText("开始任务");
                });
                return;
            }
            DqTest.exeDianZanTask(i, users.get(i), videoIdStr);
        }
        runOnUiThread(() -> {
            showToast("任务完成");
            isTask = false;
            exeTask.setText("开始任务");
        });
    }

    private List<User> getRandomUser(int number) {
        List<User> re = new ArrayList<>();
        List<User> allUser = getAllUser();
        Collections.shuffle(allUser);//随机排序

        int total;
        if (allUser.size() >= number) {
            total = number;
        } else {
            total = allUser.size();
        }

        for (int i = 0; i < total; i++) {
            re.add(allUser.get(i));
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
