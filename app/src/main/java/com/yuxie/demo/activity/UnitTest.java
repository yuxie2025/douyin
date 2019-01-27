package com.yuxie.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.baidu.aip.face.AipFace;
import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.yuxie.demo.api.server.HostType;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.sy.Sy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * @author llk
 * @time 2018/6/13 13:46
 * @class describe
 */
public class UnitTest {

    public static void test() {

        //        long maxMemory=Runtime.getRuntime().maxMemory();
//        LruCache<String,Bitmap> mLruCache=new LruCache<String,Bitmap>((int)maxMemory){
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                int size=value.getByteCount()*value.getHeight();
//                return super.sizeOf(key, value);
//            }
//        };

//        NdkJniUtils NdkJniUtils = new NdkJniUtils();
//        NdkJniUtils.getCLanguageString();


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


//        update();

//        testWxPay();

        testAipFace();


    }

    private static void testAipFace() {

        String APP_ID = "15427448";
        String API_KEY = "4GWAkGu9wnsoZV4eOFwK2EcO";
        String SECRET_KEY = "ZcECb0eG9tsUpgryQgHol2M1aULmK5H9";

        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = Environment.getExternalStorageDirectory() + "/" + "1.jpg";
//        String path = "test.jpg";

        new Thread(() -> {
            try {
                JSONObject res = client.detect(path, "BASE64", new HashMap<String, String>());
                System.out.println(res.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

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

    private void testWxPay() {

//        WxBean bean = new WxBean();
//        bean.setPackageX("Sign=WXPay");
////        bean.setAppid("wx9d55a4dc0d12ccc5");
//        bean.setAppid("wxb75b4f3e17073cc8");
//        bean.setSign("AB8FF82DB8C55C57E339962B837916F8");
//        bean.setPartnerid("1501867921");
//        bean.setPrepayid("wx071618078933228e16960f883772874534");
//        bean.setNoncestr("EoNO7N1m907lLz2N");
//        bean.setTimestamp("1525681089");
//
//        WechatPayUtils.wxPay(context, bean);

    }

    public static void add(int a, int b) {

        System.out.print("a+b=" + a + b);

    }

    public static void test(Activity context) {

//        testUrl(context);

//        Intent intent = new Intent(context, ActivityWakeUp.class);
//        context.startActivity(intent);
//        Intent intent = new Intent(context, TestActivity.class);
//        context.startActivity(intent);

//        WebViewActivity.start(context,"百度一下","http://www.baidu.com");

//        Intent imageIntent = new Intent(Intent.ACTION_SEND);
//        imageIntent.setType("text/plain");
//        imageIntent.putExtra(Intent.EXTRA_SUBJECT, "分享");
//        imageIntent.putExtra(Intent.EXTRA_TEXT, "赶紧做项目了");
//        context.startActivity(Intent.createChooser(imageIntent, "分享"));

//        ServerApi.getInstance().getInfo()
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxSubscriber<BaseRespose>(context) {
//                    @Override
//                    protected void _onNext(BaseRespose baseRespose) {
//                        LogUtils.logd("baseRespose:" + baseRespose);
//                    }
//
//                    @Override
//                    protected void _onError(String message) {
//                        LogUtils.logd("message:" + message);
//                    }
//                });


//        String picPath = Environment.getExternalStorageDirectory() + "/1.png";
//        String imgData = Base64.encodeToString(FileIOUtils.readFile2BytesByStream(picPath), Base64.DEFAULT).toString();
//
//        ServerApi.getInstance().sendPic(imgData)
//                .compose(RxSchedulers.io_main())
//                .subscribe(new RxSubscriber<BaseRespose>(context) {
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

        new Thread(() -> {

            String url = "https://www.jianshu.com/p/0160ba4d1b70";
            String token = "9ec50e9dc69b8652bf2480f8f19cc9cc";
            Sy.contentCrawlers(url, token);
//            622647
//            Sy.sendNewsContents("622870", token);

//            Sy.missionsClick(token);

        }).start();


    }

    public static void testUrl(Context context) {

        String accountId = "1248119110873466880";
        String token = "158949eb3716484bb1bee84149c02f1a";
        String Authorization = "MTI0ODExOTExMDg3MzQ2Njg4MCFAIyQlVWlLbF4mKigpXyo5OUNCYGAwMD8/Ljw+";
        String app = "1.0.0";

        ServerApi.getInstance(HostType.HOST_TYPE_WEIXIN_FLAG).getApiService()
                .vehicleList(accountId, token, Authorization, app)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BaseRespose>(context) {
                    @Override
                    protected void _onNext(BaseRespose baseRespose) {

                    }

                    @Override
                    protected void _onError(String message) {

                    }
                });

    }

}
