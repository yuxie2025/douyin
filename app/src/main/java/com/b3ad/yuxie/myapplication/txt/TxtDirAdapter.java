package com.b3ad.yuxie.myapplication.txt;

import android.content.Context;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.base.CommonAdapter;
import com.b3ad.yuxie.myapplication.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/08/27.
 */

public class TxtDirAdapter extends CommonAdapter<TxtDir>{
    public TxtDirAdapter(Context context, List<TxtDir> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, TxtDir txtDir, List<TxtDir> mDatas, int position) {

        holder.setText(R.id.tv_chapter_name,txtDir.getTitle());

    }
}
