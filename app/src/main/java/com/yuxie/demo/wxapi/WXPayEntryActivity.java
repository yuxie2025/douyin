//package com.yuxie.demo.wxapi;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.baselib.basebean.StudentEvent;
//import com.baselib.baserx.RxBus;
//import com.baselib.enums.StudentEnum;
//import com.yuxie.demo.wxapi.constant.Constant;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
///**
// * 微信支付完成后回调onResp函数
// * 此类必须放在包名.wxapi里面。例：包名com.subzero.copass （com.subzero.copass .wxapi.WXPayEntryActivity）;
// */
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    private static final String TAG = "TAG";
//
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.pay_result);
//        //微信APP_ID
//        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//
//        String msg = "";
//        if (resp.errCode == 0) {
//            msg = "支付成功";
//        } else if (resp.errCode == -1) {
//            msg = "已取消支付";
//        } else if (resp.errCode == -2) {
//            msg = "支付失败";
//        }
//        RxBus.getInstance().post(new StudentEvent(StudentEnum.WXPAY, msg));
//        finish();
//    }
//}