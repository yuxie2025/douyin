package com.b3ad.yuxie.myapplication.listandrecycler.adapter.rv;

import android.content.Context;

import com.b3ad.yuxie.myapplication.commonadapter.recyclerview.MultiItemTypeAdapter;
import com.b3ad.yuxie.myapplication.listandrecycler.bean.ChatMessage;

import java.util.List;


public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>
{
    public ChatAdapterForRv(Context context, List<ChatMessage> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }
}
