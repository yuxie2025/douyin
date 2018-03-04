package com.yuxie.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuxie.myapp.R;

public class ViewActivity extends AppCompatActivity {

//    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

//        myView=(MyView) findViewById(R.id.my_view);
//
//        new Thread(myView).start();
    }
}
