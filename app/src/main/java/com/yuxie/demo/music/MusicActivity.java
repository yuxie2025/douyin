package com.yuxie.demo.music;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.entity.Musics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_music_name)
    EditText etMusicName;
    @BindView(R.id.lv_musics)
    ListView lvMusics;
    private MusicListAdapter musicsAdapter;
    private List<Musics.ResultBean.SongsBean> data = new ArrayList<Musics.ResultBean.SongsBean>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_mp3;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("音乐");
        musicsAdapter = new MusicListAdapter(this, data, R.layout.item_musics);
        lvMusics.setAdapter(musicsAdapter);
    }

    //get请求
    public void queryMapListBykey(String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://s.music.163.com/")
                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
                .build();
        MusicNetApi api = retrofit.create(MusicNetApi.class);
        Call<Musics> call = api.queryMp3List("1", key, "20");
        call.enqueue(new Callback<Musics>() {
            @Override
            public void onResponse(Call<Musics> call, Response<Musics> response) {
                Musics musics = response.body();
                data = musics.getResult().getSongs();
                musicsAdapter.refreshData(data);
            }

            @Override
            public void onFailure(Call<Musics> call, Throwable t) {
                Toast.makeText(MusicActivity.this, "网络异常," + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.rl_left, R.id.btn_serach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_left:
                finish();
                break;
            case R.id.btn_serach:
                String key = etMusicName.getText().toString().trim();

                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(MusicActivity.this, "请输入音乐名称!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //根据关键字查询歌曲信息
                queryMapListBykey(key);
                break;
        }
    }
}
