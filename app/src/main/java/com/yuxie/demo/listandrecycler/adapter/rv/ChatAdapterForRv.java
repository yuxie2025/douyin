package com.yuxie.demo.listandrecycler.adapter.rv;

import android.content.Context;

import com.yuxie.demo.commonadapter.recyclerview.MultiItemTypeAdapter;
import com.yuxie.demo.listandrecycler.bean.ChatMessage;

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
