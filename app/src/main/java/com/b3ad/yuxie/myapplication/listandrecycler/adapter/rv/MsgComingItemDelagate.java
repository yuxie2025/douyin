package com.b3ad.yuxie.myapplication.listandrecycler.adapter.rv;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.commonadapter.recyclerview.ItemViewDelegate;
import com.b3ad.yuxie.myapplication.commonadapter.recyclerview.ViewHolder;
import com.b3ad.yuxie.myapplication.listandrecycler.bean.ChatMessage;

public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
    {
        holder.setText(R.id.chat_from_content, chatMessage.getContent());
        holder.setText(R.id.chat_from_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
    }
}
