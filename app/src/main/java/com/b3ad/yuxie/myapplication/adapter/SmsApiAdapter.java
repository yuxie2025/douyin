package com.b3ad.yuxie.myapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.entity.SmsApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
