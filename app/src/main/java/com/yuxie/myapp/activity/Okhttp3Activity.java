package com.yuxie.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuxie.myapp.R;

//import okhttp3.Callback;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class Okhttp3Activity extends AppCompatActivity {

    private String TAG = "TAG";

    Button btnTest;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp3);

        btnTest=(Button)findViewById(R.id.btn_test);
        tv=(TextView)findViewById(R.id.tv);
        tv.setText("你好");
    }

    public void doClick(View view) {
        Log.i("TAG", "doClick");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testRetrofitPost();
//            }
//        }).start();
        String url = "http://www.baidu.com";
        async(url);

//        test();

    }

    /**
     * 异步请求：回调方法可以直接操作UI
     */
    private void async(String url) {

//        OkHttpUtil.getDefault(this).doGetAsync(HttpInfo.Builder().setUrl(url).build(), new com.okhttplib.callback.Callback() {
//            @Override
//            public void onSuccess(HttpInfo info) throws IOException {
//                Log.i("TAG", "异步请求成功：" + info.toString());
//                tv.setText("结果:"+info.toString());
//
//            }
//
//            @Override
//            public void onFailure(HttpInfo info) throws IOException {
//
//            }
//        });
    }

//                    @Override
//                    public void onFailure(HttpInfo info) throws IOException {
//                        String result = info.getRetDetail();
//                        Log.i("TAG", "异步请求失败：" + result);
//                    }
//
//                    @Override
//                    public void onSuccess(HttpInfo info) throws IOException {
//                        String result = info.getRetDetail();
//                        Log.i("TAG", "异步请求成功：" + result);
//                    }
//                });
//    }
//    public void test() {
//        //okhttp,异步访问测试
//        String url = "http://www.baidu.com";
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        //post请求参数构造
//        RequestBody body = new FormBody.Builder().add("page", "1")
//                .add("code", "news").build();
//        //构造请求信息
//        Request request = new Request.Builder().post(body).url(url).build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("TAG", "str:" + str);
//                boolean b=Thread.currentThread() == Looper.getMainLooper().getThread();
//                Log.i("TAG", "Thread.currentThread():" + Thread.currentThread());
//                Log.i("TAG", "Thread.currentThread():" + b );
////                tv.setText("结果:"+str);
//
//
//            }
//        });
//
//    }

//    //直接返回集合(已实现解析json数据)
//    public void testRetrofitReturnList() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
//                .build();
//
//        GitHubApi repo = retrofit.create(GitHubApi.class);
//
//        Call<List<Contributor>> call = repo.contributorsBySimpleGetList("square", "retrofit");
//        call.enqueue(new Callback<List<Contributor>>() {
//            @Override
//            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
//                List<Contributor> contributorList = response.body();
//                for (Contributor contributor : contributorList) {
//                    Log.i("TAG", "login:" + contributor.getLogin());
//                    Log.i("TAG", "contributions:" + contributor.getContributions());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Contributor>> call, Throwable t) {
//
//            }
//        });
//
//    }
//    public void testRetrofitReturnString() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .build();
//
//        GitHubApi repo = retrofit.create(GitHubApi.class);
//
//        Call<ResponseBody> call = repo.contributorsBySimpleGetString("square", "retrofit");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Gson gson = new Gson();
//                    ArrayList<Contributor> contributorsList = gson.fromJson(response.body().string(), new TypeToken<List<Contributor>>() {
//                    }.getType());
//                    for (Contributor contributor : contributorsList) {
//                        Log.i("TAG", "login:" + contributor.getLogin());
//                        Log.i("TAG", "contributions:" + contributor.getContributions());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }
    //get请求
//    public void testRetrofit() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
//                .build();
//
//        GitHubApi repo = retrofit.create(GitHubApi.class);
//
//        Map<String, String> map = new ArrayMap<>();
////        q=retrofit&since=2016-03-29&page=1&per_page=3
//        map.put("q", "retrofit");
//        map.put("since", "2016-03-29");
//        map.put("page", "1");
//        map.put("per_page", "3");
//        Call<RetrofitBean> call = repo.queryRetrofitByGetCall("retrofit", "2016-03-29", 1, 3);
//        call.enqueue(new Callback<RetrofitBean>() {
//            @Override
//            public void onResponse(Call<RetrofitBean> call, Response<RetrofitBean> response) {
//                Log.i(TAG, "---total:");
//                RetrofitBean retrofit = response.body();
//                Log.i(TAG, "total:" + retrofit.getTotal_count());
//                List<RetrofitBean.Items> list = retrofit.getItems();
//                if (list == null)
//                    return;
//                Log.i(TAG, "total:" + retrofit.getTotal_count());
//                Log.i(TAG, "incompleteResults:" + retrofit.getIncomplete_results());
//                Log.i(TAG, "----------------------");
//                for (RetrofitBean.Items item : list) {
//                    Log.i(TAG, "name:" + item.getName());
//                    Log.i(TAG, "full_name:" + item.getFull_name());
//                    Log.i(TAG, "description:" + item.getDescription());
//                    RetrofitBean.Owner owner = item.getOwner();
//                    Log.i(TAG, "login:" + owner.getLogin());
//                    Log.i(TAG, "type:" + owner.getType());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RetrofitBean> call, Throwable t) {
//
//            }
//        });
//    }

    //post请求
//    public void testRetrofitPost() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
//                .build();
//        GitHubApi repo = retrofit.create(GitHubApi.class);
//        Call<User> call=repo.updateUser("hello", "123");
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.i("TAG","onResponse---:"+response.toString());
//                User user= response.body();
//                Log.i("TAG","user.getFirst_name():"+user.getFirst_name());
//                Log.i("TAG","user.getLast_name():"+user.getLast_name());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.i("TAG","onFailure---");
//            }
//        });
//    }
}
