package com.yuxie.myapp.txt;

import android.content.Context;

import com.yuxie.myapp.R;
import com.yuxie.myapp.base.CommonAdapter;
import com.yuxie.myapp.base.ViewHolder;

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
