package com.b3ad.yuxie.myapplication.txt;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.base.CommonAdapter;
import com.b3ad.yuxie.myapplication.base.ViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/08/24.
 */

public class TxtListAdapter extends CommonAdapter<Txt> {

    public TxtListAdapter(Context context, List<Txt> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final Txt txt, List<Txt> mDatas, int position) {

        holder.setText(R.id.tv_txt_name, txt.getTxtName());
        holder.setText(R.id.tv_artists_name, txt.getAuthorName());
        holder.setText(R.id.tv_latest_title, txt.getLatestTitle());

        Button btn_download =holder.getView(R.id.btn_open_dir);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开小说目录
                Intent intent = new Intent(mContext, TxtDirActivity.class);
                intent.putExtra("TxtDirUrl", txt.getDirUrl());
                intent.putExtra("TAG",txt.getTag());
                intent.putExtra("TextName",txt.getTxtName());
                mContext.startActivity(intent);
            }
        });
        ImageView iv_photo =holder.getView(R.id.iv_photo);
        if (!TextUtils.isEmpty(txt.getPhoto())){
            Glide.with(mContext)
                    .load(txt.getPhoto())//图片的下载地址
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//不同尺寸的图片,各下载(缓存)一份
                    .into(iv_photo);//下载的图标加载到对应的ImageView
        }else{
            iv_photo.setImageResource(R.drawable.no_photo);
        }
    }
}
