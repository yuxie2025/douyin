package com.yuxie.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.controlpc.RemoteControlActivity;
import com.yuxie.demo.dq.DqActivity;
import com.yuxie.demo.music.MusicActivity;
import com.yuxie.demo.mvvp.MvvpActivity;
import com.yuxie.demo.net.NetActivity;
import com.yuxie.demo.novel.SearchNovelActivity;
import com.yuxie.demo.sms.SmsApiActivity;
import com.yuxie.demo.sy.SyActivity;
import com.yuxie.demo.txt.TxtActivity;
import com.yuxie.demo.video.VideoListActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView lv_test;
    Context mContext;


    List<Map<String, Object>> data;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.title)
    TextView title;

    private List<Class<? extends Activity>> alist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("首页");
        rlLeft.setVisibility(View.INVISIBLE);

        init();

        info();

    }

    @Override
    protected void onStart() {
        super.onStart();
        MainActivityPermissionsDispatcher.storageNeedWithPermissionCheck(this);
    }

    private void init() {
        mContext = this;
        lv_test = findViewById(R.id.lv_test);
        data = getList();
        SimpleAdapter adp = new SimpleAdapter(this, data, R.layout.item_test, new String[]{"testName"},
                new int[]{R.id.tv_test_name});
        lv_test.setAdapter(adp);
        lv_test.setOnItemClickListener(this);
    }

    //测试方法
    private void test() {
        //测试方法
        UnitTest.test(this);
    }

    // 返回列表信息
    private List<Map<String, Object>> getList() {
        List<Map<String, Object>> data = new ArrayList<>();
        alist = new ArrayList<>();

//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("testName", "WebView基本使用和与js交互");
//        alist.add(WebViewJsActivity.class);
//        data.add(map2);
//        Map<String, Object> map3 = new HashMap<>();
//        map3.put("testName", "okhttp3的简单使用和封装工具类的使用");
//        alist.add(Okhttp3Activity.class);
//        data.add(map3);
//        Map<String, Object> map4 = new HashMap<>();
//        map4.put("testName", "glide和picasso加载图片框架的使用");
//        alist.add(GlideAndPicassoActivity.class);
//        data.add(map4);
//        Map<String, Object> map5 = new HashMap<>();
//        map5.put("testName", "自定义view画圆和雷达页面");
//        alist.add(ViewActivity.class);
//        data.add(map5);
//        Map<String, Object> map6 = new HashMap<>();
//        map6.put("testName", "mvp简单使用");
//        alist.add(UserActivity.class);
//        data.add(map6);
//        Map<String, Object> map7 = new HashMap<>();
//        map7.put("testName", "mvp登录,用MVPHelper创建");
//        alist.add(LoginActivity.class);
//        data.add(map7);
//        Map<String, Object> map8 = new HashMap<>();
//        map8.put("testName", "Rxjava(观察者模式)");
//        alist.add(RxjavaActivity.class);
//        data.add(map8);
//        Map<String, Object> map9 = new HashMap<>();
//        map9.put("testName", "mvvp简单使用");
//        alist.add(MvvpActivity.class);
//        data.add(map9);
//        Map<String, Object> map11 = new HashMap<>();
//        map11.put("testName", "listview和recyclerview使用");
//        alist.add(ListAndRecyclerActivity.class);
//        data.add(map11);


        Map<String, Object> map100 = new HashMap<>();
        map100.put("testName", "------test方法------");
        alist.add(MvvpActivity.class);
        data.add(map100);

        Map<String, Object> map10 = new HashMap<>();
        map10.put("testName", "Txt阅读");
        alist.add(SearchNovelActivity.class);
        data.add(map10);

        Map<String, Object> map0 = new HashMap<>();
        map0.put("testName", "Vip视频播放");
        alist.add(VideoListActivity.class);
        data.add(map0);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("testName", "网易音乐");
        alist.add(MusicActivity.class);
        data.add(map1);

        Map<String, Object> map12 = new HashMap<>();
        map12.put("testName", "手机控制pc");
        alist.add(RemoteControlActivity.class);
        data.add(map12);
        Map<String, Object> map13 = new HashMap<>();
        map13.put("testName", "短信");
        alist.add(SmsApiActivity.class);
        data.add(map13);
//        Map<String, Object> map14 = new HashMap<>();
//        map14.put("testName", "视频播放");
//        alist.add(PalyActivity.class);
//        data.add(map14);
        Map<String, Object> map15 = new HashMap<>();
        map15.put("testName", "网络请求");
        alist.add(NetActivity.class);
        data.add(map15);

        Map<String, Object> map16 = new HashMap<>();
        map16.put("testName", "多奇视频");
        alist.add(DqActivity.class);
        data.add(map16);

        Map<String, Object> map17 = new HashMap<>();
        map17.put("testName", "三言");
        alist.add(SyActivity.class);
        data.add(map17);


        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //执行测试方法
        if ("------test方法------".equals(data.get(i).get("testName"))) {
            test();
            return;
        }
        //跳转需要测试的页面
        Intent intent = new Intent(this, alist.get(i));
        startActivity(intent);
    }


    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageNeed() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void storageDenied() {
        mustSetting("应用需要存储权限,去设置?");
    }


    public void info() {
        ServerApi.getInstance().info(23.1234, 113.5698, "广州", DeviceUtils.getAndroidID())
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose>(mContext, false) {
                    @Override
                    protected void _onNext(BaseRespose baseRespose) {
                        LogUtils.d("baseRespose:" + baseRespose);
                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtils.d("message:" + message);
                    }
                });
    }


//    public void info() {
//
//        String imei = "866174010882153";
//        String token = "4d570255ffc544163b32f68c8edfbfe0";
//        String content_id = "510827";
//        String content = "不管了走吧.";
//
//        SPUtils.getInstance().put("imei", imei);
//        SPUtils.getInstance().put("token", token);
//
//        ServerApi.getInstance().comments(content_id, content, "")
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxSubscriber<BaseRespose>(mContext, false) {
//                    @Override
//                    protected void _onNext(BaseRespose baseRespose) {
//                        LogUtils.d("baseRespose:" + baseRespose);
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//                        LogUtils.d("message:" + message);
//                    }
//                });
//    }

}
