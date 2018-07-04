package com.yuxie.demo.video;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yuxie.demo.R;


public class PlayActivity extends Activity {

    private WebView web;
    private ProgressBar pbLoadUrl;//进度条

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new
            FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private myWebClient client;
    private WebSettings settings;
    private String[] xllb = {
            "http://www.82190555.com/video.php?url=",
            "http://2gty.com/apiurl/yun.php?url=",
            "http://j.88gc.net/jx/?url=",
            "http://y.j1118.com/xnflv/index.php?url=",
    };
    private String url;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        web.loadUrl(msg.obj.toString());
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //初始化webView
        initWebView();

    }

    //初始化webView
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initWebView() {

        pbLoadUrl = (ProgressBar) findViewById(R.id.pb_load_url);
        // 绑定
        web = (WebView) findViewById(R.id.play_video);
        url = getIntent().getStringExtra("what_web");

        settings = web.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setPluginState(PluginState.ON);

        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);// 启用支持javascript
//        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
//        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        //设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);

        //设置User-Agent,欺骗服务器端
        if (Build.VERSION.SDK_INT >= 20) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 不跳转流浏览器
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                // 我们这个地方返回false, 并不处理它,把它交给webView自己处理.解决返回被重定向问题
                return false;
            }
        });
        // 自定义客户端
        client = new myWebClient();
        web.setWebChromeClient(client);

        isSetUserAgent(url);
        web.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web.setWebChromeClient(null);
        web.setWebViewClient(null);
        web = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void flush(View v) {
        web.reload();
    }

    // 开始播放
    public void play(View v) {
        String current_web = web.getUrl();
        if (!current_web.contains(xllb[suoyin_index])) {
            current_web = xllb[suoyin_index] + current_web;
        }
        isSetUserAgent(current_web);
        web.loadUrl(current_web);
    }

    // 前进
    public void fork(View v) {
        if (web.canGoForward()) {
            WebBackForwardList list = web.copyBackForwardList();
            isSetUserAgent(list.getItemAtIndex(list.getCurrentIndex() + 1).getUrl());
            web.goForward();
        } else {
            Toast.makeText(this, "小主，实在没有记录了....", Toast.LENGTH_SHORT).show();
        }

    }

    // 后退
    public void back(View v) {
        if (web.canGoBack()) {
            WebBackForwardList list = web.copyBackForwardList();
            isSetUserAgent(list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl());
            web.goBack();
        } else {
            tuichu(v);
        }
    }

    // 停止播放
    @Override
    protected void onStop() {
        super.onStop();
        web.onPause();
    }

    // 退出
    public void tuichu(View v) {
        finish();
    }

    // 监控返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();// 返回上一页面
                return true;
            } else {
                finish();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class myWebClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {//实现加载过程显示进度条
            if (newProgress == 100) {
                pbLoadUrl.setVisibility(View.INVISIBLE);
            } else {
                pbLoadUrl.setVisibility(View.VISIBLE);
                pbLoadUrl.setProgress(newProgress);
            }
        }

        /*** 视频播放相关的方法 **/
        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(PlayActivity.this);
            LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            frameLayout.setLayoutParams(p);
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            showCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            hideCustomView();
        }

        /**
         * 视频播放全屏
         **/
        private void showCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (customView != null) {
                callback.onCustomViewHidden();
                return;
            }

            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            fullscreenContainer = new FullscreenHolder(PlayActivity.this);
            fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
            decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
            customView = view;
            setStatusBarVisibility(false);
            customViewCallback = callback;
            PlayActivity.this
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        /**
         * 隐藏视频全屏
         */
        private void hideCustomView() {
            if (customView == null) {
                return;
            }

            setStatusBarVisibility(true);
            FrameLayout decor = (FrameLayout) getWindow().getDecorView();
            decor.removeView(fullscreenContainer);
            fullscreenContainer = null;
            customView = null;
            customViewCallback.onCustomViewHidden();
            web.setVisibility(View.VISIBLE);
            PlayActivity.this
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        /**
         * 全屏容器界面
         */
        class FullscreenHolder extends FrameLayout {

            public FullscreenHolder(Context ctx) {
                super(ctx);
                setBackgroundColor(ctx.getResources().getColor(
                        android.R.color.black));
            }

            @Override
            public boolean onTouchEvent(MotionEvent evt) {
                return true;
            }
        }

        private void setStatusBarVisibility(boolean visible) {
            int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setFlags(flag,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private int suoyin_index = 0;


    // 更换线路
    public void genghuan(View v) {
        String url = web.getUrl();
        url = url.substring(url.indexOf("=") + 1);
        if (suoyin_index == (xllb.length - 1)) {
            suoyin_index = 0;
        } else {
            suoyin_index += 1;
        }
        isSetUserAgent(xllb[suoyin_index] + url);
        web.loadUrl(xllb[suoyin_index] + url);
    }

    private void isSetUserAgent(String url) {

        for (String str : xllb) {
            if (url.contains(str)) {
                //设置User-Agent,欺骗服务器端
                settings.setUserAgentString("User-Agent: Mozilla/5.0 (Linux; MI 2 Build/LRX22G; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 MobileSafari/537.36");
                return;
            }
        }
        //设置User-Agent,欺骗服务器端
        settings.setUserAgentString("Dalvik/2.1.0 (Linux; U; Android 7.0; MI 5s Plus MIUI/V9.2.1.0.NBGCNEK)");

    }


}

