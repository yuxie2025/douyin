package com.yuxie.demo.sms;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuxie.demo.R;
import com.yuxie.demo.entity.SmsApi;

import java.util.List;

/**
 * Created by luo on 2018/3/3.
 */

public class SmsApiAdapter extends BaseQuickAdapter<SmsApi, BaseViewHolder> {


    public SmsApiAdapter(@Nullable List<SmsApi> data) {
        super(R.layout.item_sms_api_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmsApi item) {

        helper.setText(R.id.tv_name, item.getUrl());
        helper.addOnClickListener(R.id.btn_update).addOnClickListener(R.id.btn_delete).addOnClickListener(R.id.tv_name);

    }
}
