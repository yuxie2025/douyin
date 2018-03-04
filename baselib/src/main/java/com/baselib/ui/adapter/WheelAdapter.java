package com.baselib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baselib.R;
import com.wx.wheelview.adapter.BaseWheelAdapter;

/**
 * 作者: liuhuaqian on 2017/9/9.
 */

public class WheelAdapter extends BaseWheelAdapter<String> {

    private Context mContext;

    public WheelAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }

}