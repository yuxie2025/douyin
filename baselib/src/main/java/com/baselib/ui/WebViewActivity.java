package com.baselib.ui;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.base.BaseActivity;

/**
 * WebView网页显示
 */
public class WebViewActivity extends BaseActivity {

    TextView tvTitle;
    WebView webView;

    public static final String TITLE = "title";
    public static final String URL = "url";

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        tvTitle = (TextView) findViewById(R.id.title);
        webView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.rl_left).setOnClickListener(v -> finish());


        tvTitle.setText(getIntent().getStringExtra(TITLE));
        String mUrl = getIntent().getStringExtra(URL);

        webView.getSettings().setDisplayZoomControls(false);// 设定缩放控件隐藏
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);// support zoom
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        //测试
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        DisplayMetrics metrics = new DisplayMetrics();
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if (mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            }
        }
        //HostType.HOST_TYPE_COMMON+
        if (URLUtil.isValidUrl(mUrl)) {
            webView.loadUrl(mUrl);
        } else {
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
            webView.loadDataWithBaseURL(null, mUrl, "text/html", "utf-8", null);
        }

        //wv.loadUrl("http://192.168.1.36:8080/iphoneProduct/queryProductDetail?proId=066");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //view.loadUrl(url);
                return true;
            }
        });
    }

}
