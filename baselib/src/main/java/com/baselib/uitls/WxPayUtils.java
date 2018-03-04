package com.baselib.uitls;

import android.app.Activity;
import android.text.TextUtils;

import com.baselib.R;
import com.baselib.baseapp.BaseApplication;
import com.baselib.basebean.WxBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by luo on 2018/2/7.
 */

public class WxPayUtils {

    /**
     * 微信支付
     *
     * @param activity
     * @param wxBean
     */
    private void wxPay(Activity activity, final WxBean wxBean) {

        //防双击
        if (CommonUtils.isDoubleClick(200)) return;

        if (wxBean == null) return;

        String appId = "";
        if (TextUtils.isEmpty(wxBean.getAppid())) {
            appId = BaseApplication.getAppResources().getString(R.string.wx_app_id);
        } else {
            appId = wxBean.getAppid();
        }

        IWXAPI api = WXAPIFactory.createWXAPI(activity, appId);

        PayReq req = new PayReq();

        req.appId = appId;
        req.partnerId = wxBean.getPartnerid();
        req.prepayId = wxBean.getPrepayid();
        req.nonceStr = wxBean.getNoncestr();
        req.timeStamp = wxBean.getTimestamp();
        req.packageValue = wxBean.getPackageX();
        req.sign = wxBean.getSign();

        api.sendReq(req);
    }


}
