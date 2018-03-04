package com.b3ad.yuxie.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.adapter.SmsApiAdapter;
import com.b3ad.yuxie.myapplication.api.ApiService;
import com.b3ad.yuxie.myapplication.entity.SmsApi;
import com.b3ad.yuxie.myapplication.utils.db.EntityManager;
import com.baselib.base.BaseActivity;
import com.baselib.uitls.CRequest;
import com.baselib.utilcode.util.LogUtils;
import com.baselib.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.anonymous.greendao.SmsApiDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmsApiActivity extends BaseActivity {

    @Bind(R.id.phone_number)
    AppCompatEditText phoneNumber;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    SmsApiDao smsApiDao;

    SmsApiAdapter adapter;

    int successTotol = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms_api;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        smsApiDao = EntityManager.getInstance().getSmsApiDao();

        List<SmsApi> smsApiList = smsApiDao.loadAll();
        if (smsApiList.size() == 0) {
            List<SmsApi> addData = addData();
            smsApiDao.insertOrReplaceInTx(addData);
        }

        List<SmsApi> data = smsApiDao.loadAll();

        adapter = new SmsApiAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                if (view.getId() == R.id.btn_update) {
                    ToastUtils.showShort("修改");
                }
                if (view.getId() == R.id.tv_name) {
                    ToastUtils.showShort(adapter.getData().get(position).getUrl());
                }
                if (view.getId() == R.id.btn_delete) {
                    smsApiDao.delete(adapter.getData().get(position));
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @OnClick({R.id.start, R.id.stop, R.id.add_sms_api})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                String phone = phoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    ToastUtils.showShort("请输入目标手机号");
                    return;
                }
                fire(phone);
                break;
            case R.id.stop:
                break;
            case R.id.add_sms_api:
                break;
        }
    }

    private void fire(String phoneNumber) {
        successTotol = 0;
        List<SmsApi> list = adapter.getData();
        for (SmsApi smsApi : list) {
            fire(phoneNumber, smsApi);
        }

    }

    private void fire(String phoneNumber, final SmsApi smsApi) {

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        ApiService service = retrofit.create(ApiService.class);
        String body = smsApi.getParameterBefore() + phoneNumber + smsApi.getParameterAfter();
        String url = smsApi.getUrl();

        if (!TextUtils.isEmpty(body) && !body.contains("=")) {
            service.getBlogs(url, body).subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Result<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.e("string--onError()---" + e.toString());
                        }

                        @Override
                        public void onNext(Result<String> stringResult) {
                            if (!TextUtils.isEmpty(stringResult.response().body()) && stringResult.response().body().contains(smsApi.getResultOk())) {
                                ++successTotol;
                                ToastUtils.showShort("成功:" + successTotol);
                                LogUtils.e("发送成功!");
                            } else {
                                LogUtils.e("发送失败!");
                            }
                        }
                    });
            return;
        }

        Map<String, String> options = CRequest.URLRequestParameter(body);
        service.getBlogs(url, options).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("string--onError()---" + e.toString());
                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        if (!TextUtils.isEmpty(stringResult.response().body()) && stringResult.response().body().contains(smsApi.getResultOk())) {
                            LogUtils.e("发送成功!");
                            ++successTotol;
                            ToastUtils.showShort("成功:" + successTotol);
                        } else {
                            LogUtils.e("发送失败!");
                        }
                    }
                });


    }

    private List<SmsApi> addData() {

        List<SmsApi> data = new ArrayList<>();

        SmsApi smsApi = new SmsApi();
        smsApi.setId(1L);
        smsApi.setType("post");
        smsApi.setUrl("http://api06aa7y.zhuishushenqi.com/sms/sendSms");
        smsApi.setParameterBefore("type=login&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"ok\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(2L);
        smsApi.setType("post");
        smsApi.setUrl("http://reg.myimpos.com/zdbepinit-server//eqinit/code/send");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("操作成功");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(3L);
        smsApi.setType("get");
        smsApi.setUrl("http://api.michun.fallchat.com/api/code/get");
        smsApi.setParameterBefore("reg=true&sign=15FF6700DF7230AD2496A130AFA4ADC7&time=1519746140276&randStr=a0afad0c7ea54d12838f4d736333801e&packId=18&ccode=86&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"ok\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(4L);
        smsApi.setType("post");
        smsApi.setUrl("http://app.yuehui.163.com/app/mobisendcode.do");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"value\":\"0\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(5L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.51yueban.cn/deep/API/User/checkMobileExist.do");
        smsApi.setParameterBefore("myuid=0&reqTime=20160101010101&signature=23358b760933d571efd25c2f4be10556&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":\"0\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(6L);
        smsApi.setType("post");
        smsApi.setUrl("http://app.gluue.net/rest/user/send_code");
        smsApi.setParameterBefore("type=0&phone=0086");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"success\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(7L);
        smsApi.setType("post");
        smsApi.setUrl("http://xbweb.gowildweb.com:6130/web/rest/account/getCaptcha");
        smsApi.setParameterBefore("type=REGISTER&username=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"success\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(8L);
        smsApi.setType("post");
        smsApi.setUrl("http://app.easywed.cn/member/sendCode");
        smsApi.setParameterBefore("density=2.75&display=1080*1920&softType=1&platformType=3&channel=mi&verName=2.2.0&type=register&deviceId=861414039745626&verCode=31&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":200");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(9L);
        smsApi.setType("post");
        smsApi.setUrl("http://api.rrhn.com/index.php/v1/member/code");
        smsApi.setParameterBefore("phoneid=861414039745626&from=android&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"message\":\"success\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(10L);
        smsApi.setType("post");
        smsApi.setUrl("http://a.lc1001.com/sms/m");
        smsApi.setParameterBefore("act=reg&pNo=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"state\":\"available\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(11L);
        smsApi.setType("post");
        smsApi.setUrl("http://app.shubl.com/signup/send_verify_code");
        smsApi.setParameterBefore("verify_type=4&hashvalue=4046b565723c564acf2f36a3f265136a&app_version=1.3.5&app_majia=gay_novel&timestamp=1519826697640&username=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("mNgGaiRTIZwktH3RJciK2u3zkN+BOMcMW9leyJLYJf7ZEbFDdzjHuNeiDfH81vhwR0XSdE4snENvYP/1qZS09k6pjvYAseLdes5c5EAXayc=");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(12L);
        smsApi.setType("post");
        smsApi.setUrl("http://suoping.7toutiao.com/user/identifyingCode");
        smsApi.setParameterBefore("imei=861414074254526&token=123456&phonenum=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":1");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(13L);//1
        smsApi.setType("post");
        smsApi.setUrl("http://cjzww.cjszyun.cn/v2/api/mobile/validCode/sendValidCode");
        smsApi.setParameterBefore("type=changePhone&token_type=android&member_token=Token_585d6f7b98f913f7dbf5c05f73fbec18&client_type=DZ&account=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("短信发送成功");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(14L);
        smsApi.setType("post");
        smsApi.setUrl("http://120.27.199.125:8079/manmao-admin/api/user_rephone_update");
        smsApi.setParameterBefore("token=&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("成功发送手机验证码");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(15L);
        smsApi.setType("post");
        smsApi.setUrl("http://api.taihuoniao.com/auth/verify_code");
        smsApi.setParameterBefore("channel=19&client_id=1415289600&time=60000&uuid=00000000-26fb-062b-4966-f814337c8d5f&sign=394d997502f8c9b7e2f3ef0e50a3777b&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"success\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(16L);
        smsApi.setType("post");
        smsApi.setUrl("http://wiibao.cn/appservice/distributioninterface/getcode");
        smsApi.setParameterBefore("type=0&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":2");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(17L);
        smsApi.setType("post");
        smsApi.setUrl("http://112.74.199.197/api/member/getcode");
        smsApi.setParameterBefore("type=1&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"status\": 1");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(18L);
        smsApi.setType("post");
        smsApi.setUrl("http://zx-store.com/index.php/userinfo/regverify");
        smsApi.setParameterBefore("timeline=201803010022&sign=d1a860f680ec1e307e206f225552a8d8&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"return_code\":0");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(19L);
        smsApi.setType("post");
        smsApi.setUrl("http://wap.baima.com/index.php?d=api200&c=user&m=checkPoneIsRegister");
        smsApi.setParameterBefore("phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"status\":\"200\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(20L);
        smsApi.setType("post");
        smsApi.setUrl("http://m.ehsy.com/uc/message/sendSmsByMobile.action");
        smsApi.setParameterBefore("message_type=1&token=&cityId=321&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"success\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(21L);
        smsApi.setType("post");
        smsApi.setUrl("http://reg.myimpos.com/zdbepinit-server//eqinit/code/send");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"retCode\":\"SUCCESS\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(22L);
        smsApi.setType("post");
        smsApi.setUrl("http://b2b2c.imall.com.cn//app/register/sendSmsValidateCode.json");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"success\":true");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(23L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.hanbangwang.com/appmobile/index.php?act=login&op=send_acode_for_mobile_register");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"state\":\"2\"");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(24L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.acggou.com/memberapi/getValidCode");
        smsApi.setParameterBefore("codeType=2&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"result\":1");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(25L);
        smsApi.setType("post");
        smsApi.setUrl("http://api.caomei.91xunai.com/api/strawberry/phone/getCheckCode");
        smsApi.setParameterBefore("reg=true&sign=C469AA620EDDA75C5176040E790B6036&time=1519924156374&randStr=cf38a3a7d3ff4577b6f315a467bf458f&ccode=86&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"ok\":true");
        data.add(smsApi);

        //以上是成功数据-------------------------

        return data;

    }


}
