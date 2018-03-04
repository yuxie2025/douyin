package com.baselib.uitls;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.baselib.basebean.StudentEvent;
import com.baselib.baserx.RxBus;
import com.baselib.enums.StudentEnum;

/**
 * Created by luo on 2018/2/7.
 */

public class AlipayUtils {

    /**
     * 支付宝支付
     *
     * @param activity
     * @param payInfo
     */
    public static void alipayPay(final Activity activity, final String payInfo) {

        //防双击
        if (CommonUtils.isDoubleClick(200)) return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                if (!TextUtils.isEmpty(result) && result.contains("out_trade_no\":\"")) {
                    String str1 = result.substring(result.indexOf("out_trade_no\":\"") + 15);
                    String outTradeNo = str1.substring(0, str1.indexOf("\"}"));
                    RxBus.getInstance().post(new StudentEvent(StudentEnum.ALIPAYPAY, "支付成功!"));
                } else {
                    RxBus.getInstance().post(new StudentEvent(StudentEnum.ALIPAYPAY, "操作已取消"));
                }

            }
        }).start();
    }
}
