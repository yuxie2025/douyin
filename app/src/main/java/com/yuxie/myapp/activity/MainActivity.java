package com.yuxie.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.apkupdate.UpdateActivity;
import com.baselib.base.BaseActivity;
import com.baselib.takephoto.app.SelectPhotoActivity;
import com.baselib.utilcode.util.ToastUtils;
import com.yuxie.myapp.R;
import com.yuxie.myapp.controlpc.RemoteControlActivity;
import com.yuxie.myapp.music.MusicActivity;
import com.yuxie.myapp.mvvp.MvvpActivity;
import com.yuxie.myapp.sms.SmsApiActivity;
import com.yuxie.myapp.txt.TxtActivity;
import com.yuxie.myapp.video.VideoListActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import no.nordicsemi.android.dfuutils.DfuActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    ListView lv_test;
    Context mContext;
    @Bind(R.id.rl_left)
    RelativeLayout rlLeft;
    @Bind(R.id.title)
    TextView title;
//    private Handler mHandler=new InnerHandler(this);

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
        //        long maxMemory=Runtime.getRuntime().maxMemory();
//        LruCache<String,Bitmap> mLruCache=new LruCache<String,Bitmap>((int)maxMemory){
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                int size=value.getByteCount()*value.getHeight();
//                return super.sizeOf(key, value);
//            }
//        };
    }

//    private static class InnerHandler extends Handler{
//        WeakReference<MainActivity> weakReference=null;
//        //非静态内部类隐式,持有外部类的引用
//        //使用弱引用持有activity对象的引用,避免handler引起的内存泄露
//        public InnerHandler( MainActivity activity) {
//            weakReference=new WeakReference<MainActivity>(activity);
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            MainActivity activity=weakReference.get();
//            if (activity!=null){
//                if (msg.what==0){
//                    activity.mContext.getApplicationContext();
//                }
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy()---");
        //释放handler,避免内存泄露
//        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("TAG", "onConfigurationChanged()---");
    }

    private void init() {
        mContext = this;
        lv_test = (ListView) findViewById(R.id.lv_test);
        final List<Map<String, Object>> data = getMovieList();
        SimpleAdapter adp = new SimpleAdapter(this, data, R.layout.item_test, new String[]{"testName"},
                new int[]{R.id.tv_test_name});
        lv_test.setAdapter(adp);
        lv_test.setOnItemClickListener(this);
    }


    //测试方法
    private void test() {

//        NdkJniUtils NdkJniUtils=new NdkJniUtils();
//        NdkJniUtils.getCLanguageString();

//        Utils.getInstance().getSignature();
//
//        String packageName="com.b3ad.marketing";
//
//        String md5=Utils.signatureMD5(Utils.getSignature(packageName));
//        Log.i("TAG","md5:"+md5);
//
//        String sha1=Utils.signatureSHA1(Utils.getSignature(packageName));
//        Log.i("TAG","sha1:"+sha1);
//
//        String sha256=Utils.signatureSHA256(Utils.getSignature(packageName));
//        Log.i("TAG","sha256:"+sha256);

//        List<String> list=new ArrayList<String>();
//        list.add("123");
//        list.add("789");
//        list.add("456");
//        list.add("000");
//        Log.i("TAG","---list:"+list.toString());
//
////        Collections.sort(list);
//
//        Log.i("TAG","---list---:"+list.toString());
//
//        Collections.reverse(list);
//
//        Log.i("TAG","reverse---list---:"+list.toString());

//        BaseDao baseDao= BaseDaoFactory.getInstall().getDataHelper(UserDao.class, User.class);
//        User user=new User("admin","123456");
//        baseDao.insert(user);

//        BaseDao baseDao= BaseDaoFactory.getInstall().getDataHelper(FileDao.class, FileBean.class);
//
//        FileBean fileBean=new FileBean();
//        fileBean.setTime("2017-09-11");
//        fileBean.setPath("cd:");
//        fileBean.setDecripte("今天天气真好!");
//
//        baseDao.insert(fileBean);

//        String url = "http://v.juhe.cn/toutiao/index?type=top&key=29da5e8be9ba88b932394b7261092f71";
//
//        for (int i = 0; i < 10; i++) {
//            News news = new News();
//            Volley.sendRequest(null, url, News.class, new IDataListener<News>() {
//                @Override
//                public void onSuccess(News news) {
//                    Log.i("TAG", news.toString());
//                }
//
//                @Override
//                public void onFail() {
//
//                }
//            });
//        }

//        UserDao userDao = EntityManager.getInstance().getUserDao();
//        userDao.insertOrReplace(new User(1l, "小明", "123", "北京"));
//
//        List<User> list = userDao.loadAll();
//
//        Log.d("TAG", "list:" + list.toString());
//        Log.d("TAG", "list:" + list.toString());

//        update();

        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(intent);
//        Intent intent = new Intent(MainActivity.this, DfuActivity.class);
//        startActivity(intent);

//        PerfectPopWindow popWindow = new PerfectPopWindow(MainActivity.this, MainActivity.class);
//        popWindow.showPopupWindow();

    }

    // 返回电影列表信息
    private List<Map<String, Object>> getMovieList() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        alist = new ArrayList<Class<? extends Activity>>();

        Map<String, Object> map100 = new HashMap<String, Object>();
        map100.put("testName", "------test方法------");
        alist.add(MvvpActivity.class);
        data.add(map100);

        Map<String, Object> map10 = new HashMap<String, Object>();
        map10.put("testName", "Txt阅读");
        alist.add(TxtActivity.class);
        data.add(map10);

        Map<String, Object> map0 = new HashMap<String, Object>();
        map0.put("testName", "Vip视频播放");
        alist.add(VideoListActivity.class);
        data.add(map0);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("testName", "网易音乐");
        alist.add(MusicActivity.class);
        data.add(map1);

//        Map<String, Object> map2 = new HashMap<String, Object>();
//        map2.put("testName", "WebView基本使用和与js交互");
//        alist.add(WebViewJsActivity.class);
//        data.add(map2);

//        Map<String, Object> map3 = new HashMap<String, Object>();
//        map3.put("testName", "okhttp3的简单使用和封装工具类的使用");
//        alist.add(Okhttp3Activity.class);
//        data.add(map3);

//        Map<String, Object> map4 = new HashMap<String, Object>();
//        map4.put("testName", "glide和picasso加载图片框架的使用");
//        alist.add(GlideAndPicassoActivity.class);
//        data.add(map4);

//        Map<String, Object> map5 = new HashMap<String, Object>();
//        map5.put("testName", "自定义view画圆和雷达页面");
//        alist.add(ViewActivity.class);
//        data.add(map5);

//        Map<String, Object> map6 = new HashMap<String, Object>();
//        map6.put("testName", "mvp简单使用");
//        alist.add(UserActivity.class);
//        data.add(map6);

//        Map<String, Object> map7 = new HashMap<String, Object>();
//        map7.put("testName", "mvp登录,用MVPHelper创建");
//        alist.add(LoginActivity.class);
//        data.add(map7);

//        Map<String, Object> map8 = new HashMap<String, Object>();
//        map8.put("testName", "Rxjava(观察者模式)");
//        alist.add(RxjavaActivity.class);
//        data.add(map8);

//        Map<String, Object> map9 = new HashMap<String, Object>();
//        map9.put("testName", "mvvp简单使用");
//        alist.add(MvvpActivity.class);
//        data.add(map9);
//        Map<String, Object> map11 = new HashMap<String, Object>();
//        map11.put("testName", "listview和recyclerview使用");
//        alist.add(ListAndRecyclerActivity.class);
//        data.add(map11);
        Map<String, Object> map12 = new HashMap<String, Object>();
        map12.put("testName", "手机控制pc");
        alist.add(RemoteControlActivity.class);
        data.add(map12);
        Map<String, Object> map13 = new HashMap<String, Object>();
        map13.put("testName", "短信轰炸");
        alist.add(SmsApiActivity.class);
        data.add(map13);

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //执行测试方法
        if (i == 0) {
            test();
            return;
        }
        //跳转需要测试的页面
        Intent intent = new Intent(this, alist.get(i));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 回调选择头像地址
         */
        if (resultCode == SelectPhotoActivity.RESULT_CODE) {
            switch (requestCode) {
                case SelectPhotoActivity.REQUEST_CODE:   // 调用相机拍照
                    File file = new File(data.getStringExtra(SelectPhotoActivity.PATH));
                    if (file.exists() && file.length() == 0) {
                        return;
                    }
                    ToastUtils.showShort("file:" + file.getAbsolutePath());
                    //getPresenter().setModifyHeadParam(file);
                    break;
            }
        }
    }


}
