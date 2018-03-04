package com.yuxie.myapp.listandrecycler.adapter.rv;

import android.content.Context;

import com.yuxie.myapp.commonadapter.recyclerview.MultiItemTypeAdapter;
import com.yuxie.myapp.listandrecycler.bean.ChatMessage;

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
