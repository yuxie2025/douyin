package com.yuxie.myapp.listandrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.yuxie.myapp.R;
import com.yuxie.myapp.commonadapter.listview.CommonAdapter;
import com.yuxie.myapp.listandrecycler.adapter.lv.ChatAdapter;
import com.yuxie.myapp.listandrecycler.bean.ChatMessage;


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
