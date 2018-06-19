package com.yuxie.demo.txt;

import android.content.Context;

import com.yuxie.demo.R;
import com.yuxie.demo.base.CommonAdapter;
import com.yuxie.demo.base.ViewHolder;

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
