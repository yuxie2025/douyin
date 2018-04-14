package com.xmhw.library_alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Created by luo on 2018/3/9.
 */

public class PayDemo {

    private static final int SDK_PAY_FLAG = 1;

//    public void aliPayV2(Activity activity, String orderInfo) {
//        Runnable payRunnable = () -> {
//            PayTask alipay = new PayTask(PayOrderActivity.this);
//            Map<String, String> result = alipay.payV2(orderInfo, true);
//            Log.e("msp", result.toString());
//
//            mHandler.obtainMessage(SDK_PAY_FLAG, result).sendToTarget();
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                    if ("6001".equals(resultStatus)) {
//                        Toast.makeText(context, payResult.getMemo(), Toast.LENGTH_SHORT).show();
//                    }
//                    //TODO 需要调用后台接口查看支付状态
//                    //getPresenter().completePay();
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };

}
