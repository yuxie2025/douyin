package com.yuxie.demo.txt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReadTxtActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_txt_show)
    TextView tv_txt_show;
    private String TAG;

    public static void start(Context context, String title, String txtContentUrl, String tag) {
        Intent intent = new Intent(context, ReadTxtActivity.class);
        intent.putExtra("txtContentUrl", txtContentUrl);
        intent.putExtra("TAG", tag);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_txt;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String txtContentUrl = intent.getStringExtra("txtContentUrl");
        TAG = intent.getStringExtra("TAG");
        String titleStr = intent.getStringExtra("title");

        title.setText(titleStr);

        if (TextUtils.isEmpty(txtContentUrl) || TextUtils.isEmpty(TAG)) {
            Toast.makeText(this, "小说的url为空!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initData(txtContentUrl);
    }

    private void initData(String txtContentUrl) {

        if (txtContentUrl.contains("com/")) {
            txtContentUrl = txtContentUrl.substring(txtContentUrl.indexOf("com/") + 4, txtContentUrl.length());
        }

        //获取最新章节类容
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TAG)
                //增加返回值为String的支持
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);

        Call<ResponseBody> call = api.queryUrlString(txtContentUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = null;
                    String content = null;
                    if (ContentGxwztvModelImpl.TAG.equals(TAG)) {
                        s = response.body().string();
                        content = ContentGxwztvModelImpl.getInstance().analyBookcontent(s, "");
                    } else if (ContentBiqugeModelImpl.TAG.equals(TAG)) {
                        s = Utils.inputStreamToStringGbk(response.body().byteStream());
                        content = ContentBiqugeModelImpl.getInstance().analyBookcontent(s, "");
                    }

                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(ReadTxtActivity.this, "没有抓取到内容!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tv_txt_show.setText(content.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ReadTxtActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.rl_left)
    public void onViewClicked() {
        finish();
    }
}
