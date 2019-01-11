package com.yuxie.demo.novel;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuxie.demo.R;
import com.yuxie.demo.txt.TxtDir;

import java.util.List;

/**
 * Created by Lankun on 2019/01/11
 */
public class NovelDirAdapter extends BaseQuickAdapter<TxtDir, BaseViewHolder> {
    public NovelDirAdapter() {
        super(R.layout.item_txt_dir);
    }

    @Override
    protected void convert(BaseViewHolder helper, TxtDir item) {

        helper.setText(R.id.tv_chapter_name, item.getTitle());

    }
}
