package com.b3ad.yuxie.myapplication.listandrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.commonadapter.listview.CommonAdapter;
import com.b3ad.yuxie.myapplication.listandrecycler.adapter.lv.ChatAdapter;
import com.b3ad.yuxie.myapplication.listandrecycler.bean.ChatMessage;


public class MultiItemListViewActivity extends AppCompatActivity
{

    private ListView mListView;
    private CommonAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_and_recycler);

        mListView = (ListView) findViewById(R.id.id_listview_list);
        mListView.setDivider(null);
        mListView.setAdapter(new ChatAdapter(this, ChatMessage.MOCK_DATAS));

    }

}
