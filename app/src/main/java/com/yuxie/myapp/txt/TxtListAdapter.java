package com.yuxie.myapp.txt;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yuxie.myapp.R;
import com.yuxie.myapp.base.CommonAdapter;
import com.yuxie.myapp.base.ViewHolder;
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

        Button btn_open_dir = holder.getView(R.id.btn_open_dir);
        btn_open_dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开小说目录
                TxtDirActivity.start(mContext, txt.getTxtName(), txt.getDirUrl(), txt.getTag());
            }
        });
        ImageView iv_photo = holder.getView(R.id.iv_photo);
        Glide.with(mContext)
                .load(txt.getPhoto()).dontAnimate().placeholder(R.drawable.no_photo)//图片的下载地址
                .into(iv_photo);//下载的图标加载到对应的ImageView
    }
}
