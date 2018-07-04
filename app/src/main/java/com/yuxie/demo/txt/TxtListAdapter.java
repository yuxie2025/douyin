package com.yuxie.demo.txt;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baselib.uitls.GlideUtil;
import com.yuxie.demo.R;
import com.yuxie.demo.base.CommonAdapter;
import com.yuxie.demo.base.ViewHolder;
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

        GlideUtil.showImageView(txt.getPhoto(), iv_photo);
    }
}
