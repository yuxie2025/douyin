package com.yuxie.myapp.txt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yuxie.myapp.R;
import com.yuxie.myapp.utils.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReadTxtActivity extends AppCompatActivity {

    private TextView tv_txt_show;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_txt);

        Intent intent=getIntent();
        String txtContentUrl=intent.getStringExtra("txtContentUrl");
        TAG=intent.getStringExtra("TAG");


        if(TextUtils.isEmpty(txtContentUrl)||TextUtils.isEmpty(TAG)){
            Toast.makeText(this, "小说的url为空!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tv_txt_show=(TextView)findViewById(R.id.tv_txt_show);

        initData(txtContentUrl);
    }

    private void initData(String txtContentUrl) {

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
                    String s=null;
                    String content=null;
                    if (ContentGxwztvModelImpl.TAG.equals(TAG)){
                        s=response.body().string();
                        content=ContentGxwztvModelImpl.getInstance().analyBookcontent(s,"");
                    }else if (ContentBiquziModelImpl.TAG.equals(TAG)) {
                        s= Utils.inputStreamToStringGbk(response.body().byteStream());
                        content=ContentBiquziModelImpl.getInstance().analyBookcontent(s,"");
                    }

                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(ReadTxtActivity.this, "没有抓取到内容!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tv_txt_show.setText(content.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("TAG","Exception:"+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ReadTxtActivity.this, "网络异常,"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
