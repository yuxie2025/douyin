package com.yuxie.demo.play;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.yuxie.demo.R;
import com.yuxie.demo.base.MyBaseActivity;

import butterknife.ButterKnife;

public class PalyActivity extends MyBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paly;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initPlay();
    }

    private void initPlay() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
