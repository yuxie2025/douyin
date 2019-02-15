package com.yuxie.demo.txt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.base.MyBaseActivity;
import com.yuxie.demo.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TxtDirActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lv_txt_dir)
    ListView lv_txt_dir;
    @BindView(R.id.btn_sort)
    Button btn_sort;

    private String TAG;
    private String txtName;

    private TxtDirAdapter txtDirAdapter;
    private List<TxtDir> data = new ArrayList<TxtDir>();

    public static void start(Context context, String TextName, String TxtDirUrl, String TAG) {
        Intent intent = new Intent(context, TxtDirActivity.class);
        intent.putExtra("TextName", TextName);
        intent.putExtra("TxtDirUrl", TxtDirUrl);
        intent.putExtra("TAG", TAG);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_txt_dir;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String txtDirUrl = intent.getStringExtra("TxtDirUrl");
        TAG = intent.getStringExtra("TAG");
        txtName = intent.getStringExtra("TextName");

        title.setText(txtName);

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
        if (ContentBiqugeModelImpl.TAG.equals(TAG)) {
            BiquziDir(txtDirUrl);
        } else if (ContentGxwztvModelImpl.TAG.equals(TAG)) {
            GxwztvDir(txtDirUrl);
        }
    }

    private void GxwztvDir(final String txtDirUrl) {
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

    private void BiquziDir(final String txtDirUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContentBiqugeModelImpl.TAG)
                .build();
        TxtNetApi api = retrofit.create(TxtNetApi.class);
        Call<ResponseBody> call = api.queryDirUrlString(txtDirUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = Utils.inputStreamToStringGbk(response.body().byteStream());

                    List<TxtDir> list = ContentBiqugeModelImpl.getInstance().chaptersList(s, "");
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
        String titleStr = data.get(i).getTitle();
        if (TextUtils.isEmpty(txtContentUrl)) {
            Toast.makeText(TxtDirActivity.this, "未获取到该章的内容地址...", Toast.LENGTH_SHORT).show();
            return;
        }
        ReadTxtActivity.start(mContext, titleStr, txtContentUrl, TAG);

    }

    @OnClick(R.id.rl_left)
    public void onViewClicked() {
        finish();
    }
}
