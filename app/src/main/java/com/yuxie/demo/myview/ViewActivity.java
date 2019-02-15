package com.yuxie.demo.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuxie.demo.R;
import com.yuxie.demo.base.MyBaseActivity;

/**
 * 自定义view
 */
public class ViewActivity extends MyBaseActivity {

//    private MyView myView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        myView=(MyView) findViewById(R.id.my_view);
//
//        new Thread(myView).start();
    }
}
