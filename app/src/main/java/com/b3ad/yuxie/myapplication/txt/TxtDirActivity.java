package com.b3ad.yuxie.myapplication.txt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TxtDirActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_txt_dir;
    private Button btn_sort;
    private TextView tv_txt_name;
    private String TAG;
    private String TextName;

    private TxtDirAdapter txtDirAdapter;
    private List<TxtDir> data = new ArrayList<TxtDir>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_dir);

        Intent intent = getIntent();
        String txtDirUrl = intent.getStringExtra("TxtDirUrl");
        TAG=intent.getStringExtra("TAG");
        TextName=intent.getStringExtra("TextName");

        Log.i("TAG", "onCreate: txtDirUrl:" + txtDirUrl);

        lv_txt_dir = (ListView) findViewById(R.id.lv_txt_dir);
        btn_sort = (Button) findViewById(R.id.btn_sort);
        tv_txt_name = (TextView) findViewById(R.id.tv_txt_name);

        txtDirAdapter = new TxtDirAdapter(this, data, R.layout.item_txt_dir);

        lv_txt_dir.setAdapter(txtDirAdapter);
        lv_txt_dir.setOnItemClickListener(this);

        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.reverse(data);
                txtDirAdapter.refreshData(data);
            }
        });
        initData(txtDirUrl);

    }

    private void initData(final String txtDirUrl) {

        tv_txt_name.setText(TextName);

        Log.i("TAG","TAG:"+TAG);
        if (ContentBiquziModelImpl.TAG.equals(TAG)){
            BiquziDir(txtDirUrl);
        }else if(ContentGxwztvModelImpl.TAG.equals(TAG)){
            GxwztvDir(txtDirUrl);
        }
    }

    private void  GxwztvDir(final String txtDirUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContentGxwztvModelImpl.TAG)
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);
        Call<ResponseBody> call = api.queryDirUrlString(txtDirUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();

                    List<TxtDir> list = ContentGxwztvModelImpl.getInstance().chaptersList(s, "");
                    if (list.size() == 0) {
                        Toast.makeText(TxtDirActivity.this, txtDirUrl + ",未收录...", Toast.LENGTH_SHORT).show();
                    }
                    data.clear();
                    data.addAll(list);
                    txtDirAdapter.refreshData(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TxtDirActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  BiquziDir(final String txtDirUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContentBiquziModelImpl.TAG)
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);
        Call<ResponseBody> call = api.queryDirUrlString(txtDirUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s= Utils.inputStreamToStringGbk(response.body().byteStream());

                    List<TxtDir> list = ContentBiquziModelImpl.getInstance().chaptersList(s, "");
                    if (list.size() == 0) {
                        Toast.makeText(TxtDirActivity.this, txtDirUrl + ",未收录...", Toast.LENGTH_SHORT).show();
                    }
                    data.clear();
                    data.addAll(list);
                    txtDirAdapter.refreshData(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TxtDirActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String txtContentUrl = data.get(i).getTitleDir();
        if (TextUtils.isEmpty(txtContentUrl)) {
            Toast.makeText(TxtDirActivity.this, "未获取到该章的内容地址...", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ReadTxtActivity.class);
        intent.putExtra("txtContentUrl", txtContentUrl);
        intent.putExtra("TAG", TAG);
        startActivity(intent);

    }
}
