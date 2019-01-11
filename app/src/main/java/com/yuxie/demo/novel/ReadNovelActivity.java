package com.yuxie.demo.novel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;
import com.yuxie.demo.txt.ReadTxtActivity;

import butterknife.BindView;

public class ReadNovelActivity extends BaseActivity {

    @BindView(R.id.tv_txt_show)
    TextView tvTxtShow;

    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "txtContent";

    public static void start(Activity activity, String title, String txtContent) {
        Intent intent = new Intent(activity, ReadNovelActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_CONTENT, txtContent);
        activity.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_novel;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String title = getIntent().getStringExtra(KEY_TITLE);
        setTitle(title);

        String content = getIntent().getStringExtra(KEY_CONTENT);
        tvTxtShow.setText(content);
    }
}
