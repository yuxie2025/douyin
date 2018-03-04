package com.yuxie.myapp.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yuxie.myapp.R;
import com.yuxie.myapp.entity.Musics;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicActivity extends AppCompatActivity {

    private ListView lv_musics;
    private MusicListAdapter musicsAdapter;
    private List<Musics.ResultBean.SongsBean> data=new ArrayList<Musics.ResultBean.SongsBean>();

    private Button btn_serach;
    private EditText et_music_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);

        lv_musics=(ListView)findViewById(R.id.lv_musics);
        musicsAdapter=new MusicListAdapter(this,data,R.layout.item_musics);
        lv_musics.setAdapter(musicsAdapter);

        btn_serach=(Button)findViewById(R.id.btn_serach);
        et_music_name=(EditText)findViewById(R.id.et_music_name);

        btn_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=et_music_name.getText().toString().trim();

                if (TextUtils.isEmpty(key)){
                    Toast.makeText(MusicActivity.this, "请输入音乐名称!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //根据关键字查询歌曲信息
                queryMapListBykey(key);
            }
        });
    }

    //get请求
    public void queryMapListBykey(String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://s.music.163.com/")
                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
                .build();
        MusicNetApi api = retrofit.create(MusicNetApi.class);
        Call<Musics> call = api.queryMp3List("1", key,"20");
        call.enqueue(new Callback<Musics>() {
            @Override
            public void onResponse(Call<Musics> call, Response<Musics> response) {
                Musics musics=response.body();
                data=musics.getResult().getSongs();
                musicsAdapter.refreshData(data);
            }
            @Override
            public void onFailure(Call<Musics> call, Throwable t) {
                Toast.makeText(MusicActivity.this, "网络异常,"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
