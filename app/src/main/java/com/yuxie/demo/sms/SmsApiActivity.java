package com.yuxie.demo.sms;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuxie.demo.R;
import com.yuxie.demo.adapter.SmsApiAdapter;
import com.yuxie.demo.api.ServerApiService;
import com.yuxie.demo.entity.SmsApi;
import com.yuxie.demo.greendao.SmsApiDao;
import com.yuxie.demo.utils.CRequest;
import com.yuxie.demo.utils.db.EntityManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SmsApiActivity extends BaseActivity {

    @BindView(R.id.phone_number)
    AppCompatEditText phoneNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SmsApiDao smsApiDao;

    SmsApiAdapter adapter;

    int successTotol = 0;

    boolean isStop = false;
    @BindView(R.id.title)
    TextView title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms_api;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        title.setText("短信");

        smsApiDao = EntityManager.getInstance().getSmsApiDao();

        List<SmsApi> smsApiList = smsApiDao.queryBuilder().orderDesc().list();
        if (smsApiList.size() == 0) {
            List<SmsApi> addData = addData();
            smsApiDao.insertOrReplaceInTx(addData);
        }

        List<SmsApi> data = smsApiDao.loadAll();

        adapter = new SmsApiAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                if (view.getId() == R.id.btn_update) {
                    UpdateSmsApiActivity.start(mContext, adapter.getItem(position));
                }
                if (view.getId() == R.id.btn_delete) {
                    smsApiDao.delete(adapter.getData().get(position));
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<SmsApi> data = smsApiDao.queryBuilder().orderDesc().list();
        adapter.setNewData(data);
    }

    @OnClick({R.id.start, R.id.stop, R.id.add_sms_api, R.id.rl_left})
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
                isStop = true;
                break;
            case R.id.add_sms_api:
                startActivity(UpdateSmsApiActivity.class);
                break;
            case R.id.rl_left:
                finish();
                break;
        }
    }

    private void fire(final String phoneNumber) {
        successTotol = 0;

        isStop = false;
        ToastUtils.showShort("开火...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SmsApi> list = adapter.getData();
                for (SmsApi smsApi : list) {

                    if (isStop) {
                        ToastUtils.showShort("已经停火!");
                        return;
                    }

                    fire(phoneNumber, smsApi);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private void fire(String phoneNumber, final SmsApi smsApi) {

        URL urlHost = null;
        String host = "http://www.baidu.com";
        String path = "";
        try {
            urlHost = new URL(smsApi.getUrl());
            host = "http://" + urlHost.getHost();
            path = urlHost.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        ServerApiService service = retrofit.create(ServerApiService.class);
        String body = smsApi.getParameterBefore() + phoneNumber + smsApi.getParameterAfter();
        String url = smsApi.getUrl();

        if (TextUtils.isEmpty(body)) {
            return;
        }

        Map<String, String> options = CRequest.URLRequestParameter(body);
        if ("post".equals(smsApi.getType())) {
            service.getSmsApi(url, options).subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Result<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.d("string--onError()---" + e.toString());
                        }

                        @Override
                        public void onNext(Result<String> stringResult) {
                            String body = stringResult.response().body();
                            if (!TextUtils.isEmpty(body) && body.contains(smsApi.getResultOk())) {
                                LogUtils.d("发送成功!");
                                ++successTotol;
                                ToastUtils.showShort("成功:" + successTotol);
                            } else {
                                LogUtils.d("发送失败!");
                            }
                        }
                    });
        } else {

            if (!TextUtils.isEmpty(path)) {
                path = path.substring(1);
            }

            String pathUrl = path + "?" + body;

            service.getSmsApi(pathUrl).subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Result<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtils.d("string--onError()---" + e.toString());
                        }

                        @Override
                        public void onNext(Result<String> stringResult) {

                            String body = stringResult.response().body();

                            if (!TextUtils.isEmpty(body) && body.contains(smsApi.getResultOk())) {
                                LogUtils.d("发送成功!");
                                ++successTotol;
                                ToastUtils.showShort("成功:" + successTotol);
                            } else {
                                LogUtils.d("发送失败!");
                            }
                        }
                    });
        }
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
        smsApi = new SmsApi();
        smsApi.setId(26L);
        smsApi.setType("post");
        smsApi.setUrl("https://api2.quhepai.com/user/getsmscode");
        smsApi.setParameterBefore("os=Android&model=SM-G930F&area_id=110000&rctk=&imei=865821079483270&hpid=&ver=1.6.9&long=106.67963615746643&build=1712211344&token_temp=&netk=&nonce=1515146882410&token=&sa=SvWDOSgflCTktWN2gZJeA26s%2FKsJfvkYUf3h8KsEJ%2FxBUE3KOW%2BlQWy3g9bqRlNZ0EYGzUKwOb%2F0%0AYgOREoKTl3mmqa2pcsblrYUDbkDcT8dQiWJ9VxTaKqeIEbVl38VE4rXB1avYbPR2zOkD28c7%2Fas%2F%0Ap0R38lq3nziMVHRap34%3D&company=samsung&logined=0&api=39&verCode=446&user_id=&ch=hepai_s_018&lat=26.636185489185326&type=1&phone=86");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("请求成功");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(27L);
        smsApi.setType("post");
        smsApi.setUrl("https://www.1yuanxing.com/apiAngular/getSMS.jsp");
        smsApi.setParameterBefore("username=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":200");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(28L);
        smsApi.setType("post");
        smsApi.setUrl("http://api-cc.babybus.org/User/VerificationCode");
        smsApi.setParameterBefore("al=403&ost=1&type=1&channel=A002&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("获取验证码成功");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(29L);
        smsApi.setType("post");
        smsApi.setUrl("https://api2.drcuiyutao.com/v55/user/sendVerificationCode");
        smsApi.setParameterBefore("dialCode=86&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\": 1");
        data.add(smsApi);
        smsApi = new SmsApi();
        smsApi.setId(30L);
        smsApi.setType("get");
        smsApi.setUrl("http://msg.106117.com/submit_ajax.ashx");
        smsApi.setParameterBefore("callback=jQuery11120836245647952194_1519923914449&action=getValidate&_=1519923914450&username=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("发送验证码成功");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(31L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.csti.cn/uc/index/index.do");
        smsApi.setParameterBefore("method=checkPhone&login_phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("true");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(32L);
        smsApi.setType("post");
        smsApi.setUrl("https://reguser.sdo.com/user/register/confirm-needed-mobile.jsonp?callback=jQuery18208562389784247074_152504");
        smsApi.setParameterBefore("8246832&sessionKey=QIEZ3hVn6uvgTdmN&appId=201&areaId=-1&password=&realname=&idCard=&backUrl=http%3A%2F%2Fwww.sdo.com&_=1525048260204&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("success");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(33L);
        smsApi.setType("get");
        smsApi.setUrl("http://fof.simuwang.com/index.php");
        smsApi.setParameterBefore("c=login&a=getRegisterPhoneCode&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("发送成功");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(34L);
        smsApi.setType("post");
        smsApi.setUrl("http://app.syxwnet.com/?app=member&controller=index&action=sendMobileMessage");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("true");
        data.add(smsApi);

//        http://youshi.changzhengedu.com/mobileCode/send/15622145862
//        smsApi = new SmsApi();
//        smsApi.setId(35L);
//        smsApi.setType("get");
//        smsApi.setUrl("http://youshi.changzhengedu.com/mobileCode/send/");
//        smsApi.setParameterBefore("");
//        smsApi.setParameterAfter("");
//        smsApi.setResultOk("ok");
//        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(36L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.cacp.org.cn/zgxt/conmon/verification.htm");
        smsApi.setParameterBefore("type=1&key=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"recode\":\"1\"");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(37L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.jlhuafei.cn/movecar/client/code/setCheckCode.action");
        smsApi.setParameterBefore("tel=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("true");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(38L);
        smsApi.setType("post");
        smsApi.setUrl("https://memberprod.alipay.com/account/reg/section/reSendVerifyCode.json");
        smsApi.setParameterBefore("scene=mobileReg&json_ua=null&json_tk=fa4b8346c23891d3998c41868c7263c622fd9c086aaa44d19a206213db0ee611GZ00&_input_charset=utf-8&ctoken=wqsEenTc5I0ZF8k7&mobile=86-");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("发送验证码成功");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(39L);
        smsApi.setType("post");
        smsApi.setUrl("http://bx.egretloan.com/safe/app/sendcode");
        smsApi.setParameterBefore("phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":200");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(40L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.ci4a.cn/index.php?m=member&c=content&a=mobile_code");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("Success");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(41L);
        smsApi.setType("post");
        smsApi.setUrl("http://account.limsam.cn/ucenter/mobilebind/ajaxgetcode.aspx");
        smsApi.setParameterBefore("acc=15622145861&type=6&rnd=0.44647784629374976&tel=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("手机验证码发送成功！");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(42L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.ahhbzyz.com/program/include/component/cvmp/get_platform_code.aspx");
        smsApi.setParameterBefore("telphone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("1");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(43L);
        smsApi.setType("get");
        smsApi.setUrl("http://www.aipai.com/app/www/apps/ums.php");
        smsApi.setParameterBefore("step=ums&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":0");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(44L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.jylearning.com/getRegCode");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("已发送");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(45L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.fengtao365.com/index.php");
        smsApi.setParameterBefore("ctrl=member&action=regesterphone&random=527&_=1525052587490&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("180");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(46L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.ad1024.com/site/send-register-verify-code");
        smsApi.setParameterBefore("email=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":200");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(47L);
        smsApi.setType("post");
        smsApi.setUrl("http://member.21-sun.com/tools/ajax.jsp");
        smsApi.setParameterBefore("flag=createVeriCode&_=1525053040106&tel=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("success");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(48L);
        smsApi.setType("post");
        smsApi.setUrl("http://ggfw.wuxi.gov.cn/wx_portal/user/service/User.checkMobile.json");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("&MOBILENUM=15622145861");
        smsApi.setResultOk("本次请求成功");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(49L);
        smsApi.setType("get");
        smsApi.setUrl("http://www.ejtong.cn/cop5/phone.asp");
        smsApi.setParameterBefore("t=1&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(50L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.lequ.com/Public/sendsms");
        smsApi.setParameterBefore("formhash=972eee2ac80f258a25384bc2e9733c96&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"status\":1");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(51L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.yifatong.com/Customers/getsms");
        smsApi.setParameterBefore("rnd=1525054197.628&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("success");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(52L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.12123.com/api/login/send.json");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("true");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(53L);
        smsApi.setType("post");
        smsApi.setUrl("http://i.xafc.com/home/register/sendMsgCode");
        smsApi.setParameterBefore("from=pc_bindphone&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("\"code\":\"1\"");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(54L);
        smsApi.setType("post");
        smsApi.setUrl("http://pcmall.chinayanghe.com/send_mcode.shtml");
        smsApi.setParameterBefore("type=register&verify_mobile_code=2l1II48iO9&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("短信发送成功");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(55L);
        smsApi.setType("post");
        smsApi.setUrl("http://oa.lehome114.com/action/sendcode");
        smsApi.setParameterBefore("phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("1");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(56L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.gaokaoer.cn/Index/Sms.html");
        smsApi.setParameterBefore("loginname=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(57L);
        smsApi.setType("post");
        smsApi.setUrl("http://reg.ztgame.com/common/sendmpcode");
        smsApi.setParameterBefore("source=&nonce=&type=verifycode&token=&refurl=https://www.baidu.com/link?url=Qvgq2JleTH1zgtvO3GZX2Eis0xN7O7Y8uK-BoAXggN_&wd=&eqid=b87d3edb0000102f000000065ae68226&cururl=http://reg.ztgame.com/&mpcode=&pwd=&tname=&idcard=&phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("success");
        data.add(smsApi);

//        smsApi = new SmsApi();
//        smsApi.setId(58L);
//        smsApi.setType("post");
//        smsApi.setUrl("https://appapi.dmall.com/app/passport/validCode");
//        smsApi.setParameterBefore("param={\"graphCode\":\"\",\"phone\":\"");
//        smsApi.setParameterAfter("\",\"type\":\"register\"}");
//        smsApi.setResultOk("成功！");
//        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(59L);
        smsApi.setType("post");
        smsApi.setUrl("https://mapi.ffan.com/ffan/v1/member/verifycodes");
        smsApi.setParameterBefore("type=1&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("200");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(60L);
        smsApi.setType("get");
        smsApi.setUrl("https://uac.10010.com/portal/Service/SendMSG");
        smsApi.setParameterBefore("callback=jQuery172009245047252625227_1450060042&_=1450060042&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("200");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(61L);
        smsApi.setType("get");
        smsApi.setUrl("http://m.cmvideo.cn/sendMiguMsgCode.msp");
        smsApi.setParameterBefore("isH5=1&businessid=2&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("result");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(62L);
        smsApi.setType("get");
        smsApi.setUrl("http://uniportal.huawei.com/accounts/sendsms");
        smsApi.setParameterBefore("countryCode=%2B86&id=1465355400790&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("1");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(63L);
        smsApi.setType("get");
        smsApi.setUrl("https://passport.ceair.com/cesso/mobile!sendDynamicPassword.shtml");
        smsApi.setParameterBefore("rand=&mobileNo=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("");
        data.add(smsApi);

//        smsApi = new SmsApi();
//        smsApi.setId(64L);
//        smsApi.setType("get");
//        smsApi.setUrl("http://passenger.01zhuanche.com/car-rest/webservice/getVal/phoneNumber=15622145861");
//        smsApi.setParameterBefore(");
//        smsApi.setParameterAfter("");
//        smsApi.setResultOk("1");
//        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(65L);
        smsApi.setType("post");
        smsApi.setUrl("http://www.zhibo.tv/app/user/GetNumForRegis");
        smsApi.setParameterBefore("phone=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("0");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(66L);
        smsApi.setType("post");
        smsApi.setUrl("http://xhssdpt.com:8096/party-api/m/sendSms");
        smsApi.setParameterBefore("msg=短信验证码&mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("0000");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(67L);
        smsApi.setType("post");
        smsApi.setUrl("http://m.judazhe.com/user/send_mobile_share");
        smsApi.setParameterBefore("mobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("true");
        data.add(smsApi);


        smsApi = new SmsApi();
        smsApi.setId(68L);
        smsApi.setType("get");
        smsApi.setUrl("https://passport.ceair.com/cesso/mobile!sendDynamicPassword.shtml");
        smsApi.setParameterBefore("rand=&mobileNo=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("");
        data.add(smsApi);

        smsApi = new SmsApi();
        smsApi.setId(69L);
        smsApi.setType("post");
        smsApi.setUrl("http://service.api.atxiaoge.com/v2d0/sms/sendVerifi.json");
        smsApi.setParameterBefore("category=USERmobile=");
        smsApi.setParameterAfter("");
        smsApi.setResultOk("发送成功");
        data.add(smsApi);

        //以上是成功数据-------------------------

        return data;

    }

}
