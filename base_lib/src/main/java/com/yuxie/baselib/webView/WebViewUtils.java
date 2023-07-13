package com.yuxie.baselib.webView;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.yuxie.baselib.utils.DownloadUtils;

public class WebViewUtils {

    /**
     * SMS scheme
     */
    public static final String SCHEME_SMS = "sms:";

    /**
     * 处理链接(电话,短信,邮件,以及跳转)
     *
     * @param url url
     * @return 结果
     */
    public static boolean handleCommonLink(String url) {
        if (url.startsWith(WebView.SCHEME_TEL)
                || url.startsWith(SCHEME_SMS)
                || url.startsWith(WebView.SCHEME_MAILTO)
                || url.startsWith(WebView.SCHEME_GEO)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                ActivityUtils.startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
            }
            return true;
        }
        return false;
    }

    /**
     * 处理三方app跳转
     *
     * @param url url
     * @return 结果
     */
    public static boolean handleThereAppLink(String url) {
        try {
            if (url.startsWith("weixin://")
                    || url.startsWith("alipays://")
                    || url.startsWith("mqqapi://")) {
                //打开本地App进行支付
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                ActivityUtils.startActivity(intent);
                return true;
            }
        } catch (Exception ignored) {
        }
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            //拦截不支持的跳转方式
            return true;
        }
        return false;
    }

    /**
     * 跳转系统浏览器下载文件
     */
    public static void setDownloadListener(Context mContext, BridgeWebView webView) {
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
//            //其它浏览器下载
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setMessage("是否跳转下载？");
//            builder.setPositiveButton("确定", (dialogInterface, i) -> {
//                //处理下载事件
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setData(Uri.parse(url));
//                ActivityUtils.startActivity(intent);
//            });
//            builder.setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss());
//            builder.show();
            //内部下载
            DownloadUtils.downloadDialog(mContext, url, "");
        });
    }


}
