package com.yuxie.wechat_pay_lib.utils;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author llk
 * @time 2018/5/7 11:48
 * @class describe
 */
public class WechatPayUtils {

    public static void wxPay(Context context, final WxBean wxBean) {

        IWXAPI api = WXAPIFactory.createWXAPI(context, wxBean.getAppid());

        PayReq req = new PayReq();

        req.appId = wxBean.getAppid();
        req.partnerId = wxBean.getPartnerid();
        req.prepayId = wxBean.getPrepayid();
        req.nonceStr = wxBean.getNoncestr();
        req.timeStamp = wxBean.getTimestamp();
        req.packageValue = wxBean.getPackageX();
        req.sign = wxBean.getSign();

        boolean result = api.sendReq(req);
        Log.d("TAG", "result:" + result);
    }
}
