package com.b3ad.yuxie.myapplication.music;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.base.CommonAdapter;
import com.b3ad.yuxie.myapplication.base.ViewHolder;
import com.b3ad.yuxie.myapplication.entity.MusicInfo;
import com.b3ad.yuxie.myapplication.entity.Musics;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/08/23.
 */

public class MusicListAdapter extends CommonAdapter<Musics.ResultBean.SongsBean> {


    public MusicListAdapter(Context context, List<Musics.ResultBean.SongsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final Musics.ResultBean.SongsBean resultBean, List<Musics.ResultBean.SongsBean> mDatas, int position) {

        ImageView iv_photo = (ImageView) holder.getView(R.id.iv_photo);
        Glide.with(mContext)
                .load(resultBean.getAlbum().getPicUrl())//图片的下载地址
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//不同尺寸的图片,各下载(缓存)一份
                .into(iv_photo);//下载的图标加载到对应的ImageView

        holder.setText(R.id.tv_mustic_name, resultBean.getAlbum().getName());
        holder.setText(R.id.tv_artists_name, resultBean.getArtists().get(0).getName());

        Button btn_download = (Button) holder.getView(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = resultBean.getId();
                getMusicUrl(id + "", resultBean.getAlbum().getName());
            }
        });

    }

    public void getMusicUrl(String id, final String musicName) {
        String params = AES.getParams(id);
        String encSecKey = AES.get_encSecKey();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://music.163.com/")
                .addConverterFactory(GsonConverterFactory.create())//声明需要返回对象
                .build();
        MusicNetApi api = retrofit.create(MusicNetApi.class);
        Call<MusicInfo> call = api.queryMusicInfo(params, encSecKey);
        call.enqueue(new Callback<MusicInfo>() {
            @Override
            public void onResponse(Call<MusicInfo> call, Response<MusicInfo> response) {
                MusicInfo info = response.body();
                String downloadUrl = info.getData().get(0).getUrl();
                Log.i("TAG", "downloadUrl:" + downloadUrl);
                download(downloadUrl, musicName);
            }

            @Override
            public void onFailure(Call<MusicInfo> call, Throwable t) {
                Toast.makeText(mContext, "网络异常,"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void download(String url, final String musicName) {
//        DownloadFileInfo fileInfo = new DownloadFileInfo(url, musicName, new ProgressCallback() {
//            @Override
//            public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                Log.i("TAG", percent + "%");
//            }
//            @Override
//            public void onResponseMain(String filePath, HttpInfo info) {
//                if (info.isSuccessful()) {
//                    Toast.makeText(mContext, musicName + ",下载成功!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(mContext, "下载失败,"+info.getRetDetail(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfo).build();
//        OkHttpUtil.Builder().setReadTimeout(120).build(this).doDownloadFileAsync(info);
    }
}
