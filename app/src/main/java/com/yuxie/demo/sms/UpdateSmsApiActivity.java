package com.yuxie.demo.sms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.blankj.utilcode.util.ToastUtils;
import com.yuxie.demo.R;
import com.yuxie.demo.base.MyBaseActivity;
import com.yuxie.demo.entity.SmsApi;
import com.yuxie.demo.greendao.SmsApiDao;
import com.yuxie.demo.utils.db.EntityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateSmsApiActivity extends MyBaseActivity {

    public static final String SMS_API = "smsApi";
    @BindView(R.id.type)
    AppCompatEditText type;
    @BindView(R.id.url)
    AppCompatEditText url;
    @BindView(R.id.befor)
    AppCompatEditText befor;
    @BindView(R.id.after)
    AppCompatEditText after;
    @BindView(R.id.result)
    AppCompatEditText result;
    @BindView(R.id.title)
    TextView title;

    public static void start(Context context, SmsApi smsApi) {
        Intent intent = new Intent(context, UpdateSmsApiActivity.class);
        intent.putExtra(SMS_API, smsApi);
        context.startActivity(intent);
    }

    SmsApiDao smsApiDao;
    SmsApi smsApi;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_sms_api;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("短信接口");
        smsApiDao = EntityManager.getInstance().getSmsApiDao();
        smsApi = (SmsApi) getIntent().getSerializableExtra(SMS_API);

        if (smsApi != null) {
            type.setText(smsApi.getType());
            url.setText(smsApi.getUrl());
            befor.setText(smsApi.getParameterBefore());
            after.setText(smsApi.getParameterAfter());
            result.setText(smsApi.getResultOk());
        }

    }

    @OnClick(R.id.update)
    public void onViewClicked() {

        String typeStr = type.getText().toString().trim();
        String urlStr = url.getText().toString().trim();
        String beforStr = befor.getText().toString().trim();
        String afterStr = after.getText().toString().trim();
        String resultStr = result.getText().toString().trim();

        if (TextUtils.isEmpty(urlStr) || TextUtils.isEmpty(beforStr)) {
            ToastUtils.showShort("请输入url和参数");
            return;
        }

        if (smsApi == null) {
            smsApi = new SmsApi(null, typeStr, urlStr, beforStr, afterStr, resultStr);
        } else {
            smsApi.setType(typeStr);
            smsApi.setUrl(urlStr);
            smsApi.setParameterBefore(beforStr);
            smsApi.setParameterAfter(afterStr);
            smsApi.setResultOk(resultStr);
        }
        smsApiDao.insertOrReplace(smsApi);
        ToastUtils.showShort("成功!");
        finish();

    }

    @OnClick(R.id.rl_left)
    public void back() {
        finish();
    }
}
