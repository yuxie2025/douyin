package com.yuxie.demo.txt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TxtActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.title)
    TextView title;
    private ListView lv_musics;
    private TxtListAdapter txtListAdapter;
    private List<Txt> data = new ArrayList<Txt>();

    private Button btn_serach;
    private EditText et_txt_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_txt;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("小说");
        lv_musics = (ListView) findViewById(R.id.lv_musics);
        txtListAdapter = new TxtListAdapter(this, data, R.layout.item_txt);
        lv_musics.setAdapter(txtListAdapter);

        lv_musics.setOnItemClickListener(this);

        btn_serach = (Button) findViewById(R.id.btn_serach);
        et_txt_name = (EditText) findViewById(R.id.et_txt_name);

        btn_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = et_txt_name.getText().toString().trim();

                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(TxtActivity.this, "请输入小说名称!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //根据关键字查询小说信息
                getBiqugeByKey(key);
            }
        });
    }

    public List<Txt> getGxwztvByKey(final String key) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContentGxwztvModelImpl.TAG)
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);
        Call<ResponseBody> call = api.queryTxtList(key);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    List<Txt> list = ContentGxwztvModelImpl.getInstance().analyBookDir(s, "");
                    if (list.size() == 0 && data.size() == 0) {
                        Toast.makeText(TxtActivity.this, key + ",未收录...", Toast.LENGTH_SHORT).show();
                    }
                    data.addAll(list);
                    txtListAdapter.refreshData(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TxtActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    public List<Txt> getBiqugeByKey(final String key) {

        final String bqgUrl = "http://www.biquge5200.com";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(bqgUrl)
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);
        Call<ResponseBody> call = api.queryBQGTxtList(key);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    List<Txt> list = ContentBiqugeModelImpl.getInstance().analyBookDir(s, "");

                    data.clear();
                    data.addAll(list);
                    txtListAdapter.refreshData(data);

                    getGxwztvByKey(key);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("TAG", "onResponse: " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TxtActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        String titleStr = data.get(i).getLatestTitle();
        String txtContentUrl = data.get(i).getLatestUrl();
        String tag = data.get(i).getTag();

        ReadTxtActivity.start(mContext, titleStr, txtContentUrl, tag);

    }

    @OnClick(R.id.rl_left)
    public void onViewClicked() {
        finish();
    }
}
