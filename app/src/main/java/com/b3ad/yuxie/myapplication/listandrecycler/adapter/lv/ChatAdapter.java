package com.b3ad.yuxie.myapplication.listandrecycler.adapter.lv;

import android.content.Context;


import com.b3ad.yuxie.myapplication.commonadapter.listview.MultiItemTypeAdapter;
import com.b3ad.yuxie.myapplication.listandrecycler.bean.ChatMessage;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapter extends MultiItemTypeAdapter<ChatMessage>
{
    public ChatAdapter(Context context, List<ChatMessage> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }

}
