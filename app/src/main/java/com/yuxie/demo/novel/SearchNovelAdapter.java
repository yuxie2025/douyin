package com.yuxie.demo.novel;

import android.widget.ImageView;

import com.baselib.uitls.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuxie.demo.R;

/**
 * Created by Lankun on 2019/01/11
 */
public class SearchNovelAdapter extends BaseQuickAdapter<Txt, BaseViewHolder> {
    public SearchNovelAdapter() {
        super(R.layout.item_txt);
    }

    @Override
    protected void convert(BaseViewHolder helper, Txt item) {

        helper.setText(R.id.tv_txt_name, item.getTxtName());
        helper.setText(R.id.tv_artists_name, item.getAuthorName());
        helper.setText(R.id.tv_latest_title, item.getLatestTitle());

        helper.addOnClickListener(R.id.btn_open_dir);

        ImageView iv_photo = helper.getView(R.id.iv_photo);

        GlideUtil.showImageView(item.getPhoto(), iv_photo);

    }
}
