package com.yuxie.baselib.webView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.tencent.smtt.export.external.extension.proxy.ProxyWebChromeClientExtension;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.yuxie.baselib.base.BaseActivity;
import com.yuxie.baselib.utils.DownloadUtils;
import com.yuxie.baselib.R;
import com.yuxie.baselib.qrCode.CaptureActivity;

import java.io.File;


/**
 * h5入口
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    private BridgeWebView webView;
    private ProgressBar progressBar;
    private RelativeLayout errorView;
    private LinearLayout ivBack;
    private LinearLayout ivRefresh;
    private TextView tvTitle;
    private RelativeLayout rlTitle;

    private String url;

    //错误页面处理
    private boolean isError = false;
    private String errorTitle = "";

    //是否第一次
    private boolean isFirst;
    //记录是否需要拦截文件选择
    private Uri interceptUri;

    public Context mContext;

    private FrameLayout mLayout;    // 用来显示视频的布局
    private View mCustomView;    //用于全屏渲染视频的View
    private IX5WebChromeClient.CustomViewCallback mCustomViewCallback;


    public static void open(Context mContext, String url) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }

    private final Handler handler = new MyHandler();

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //全面屏，全屏代码
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }
//        getWindow().setAttributes(lp);
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//        );
        setContentView(R.layout.webview_activity_main_h5);

        WebViewCommonUtils.setStatusBarColor(this, Color.parseColor("#ffffff"));
        BarUtils.setStatusBarLightMode(this, true);

        mContext = this;
        isError = false;
        isFirst = true;

        //解决部分手机 会弹出输入法问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        handler.postDelayed(() -> {
            try {
                //解决h5输入法遮挡问题
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            } catch (Exception ignored) {
            }
        }, 1000);

        initView();

        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort("请先初始化!");
            finish();
            return;
        }
        initData(url);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    public void initView() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        errorView = findViewById(R.id.error_page);
        errorView.setOnClickListener(v -> webView.reload());

        ivBack = findViewById(R.id.ll_left);
        ivRefresh = findViewById(R.id.ll_right);
        tvTitle = findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title);

        ivBack.setOnClickListener(v -> {

            String url = webView.getUrl();
            //拦截首页的页面,以免返回到登录页面


            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                //返回到无法返回提示
                finish();
            }
        });

        ivRefresh.setOnClickListener(v -> webView.reload());

        hideErrorPage();

        mLayout = findViewById(R.id.fl_video);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        if (isError) {
            webView.reload();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onBackPressed() {
        String url = webView.getUrl();
        //拦截首页的页面,以免返回到登录页面

        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        webView.removeAllViews();
        webView.destroy();
        //异常所以的信息
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化数据
     */
    private void initData(String url) {

        webView.clearCache(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);//屏蔽提示密码保存框
        // 把内部私有缓存目录'/data/data/包名/cache/'作为WebView的AppCache的存储路径
        String cachePath = getApplicationContext().getCacheDir().getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(50 * 1024 * 1024);
        webSettings.setDisplayZoomControls(true);
        webView.setHorizontalScrollBarEnabled(true);//滚动条水平是否显示
        webView.setVerticalScrollBarEnabled(true); //滚动条垂直是否显示

//        String ua = webView.getSettings().getUserAgentString();
//        webSettings.setUserAgentString(ua + "; AndroidApp");

        if (url.contains("douyin.com")) {
            webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0");
        }


        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
//                MyLogUtils.log("MainH5Activity_onShowFileChooser_类型:" + Arrays.toString(fileChooserParams.getAcceptTypes()));
                if (interceptUri != null) {
                    //拦截第三方应用分享文件
                    valueCallback.onReceiveValue(new Uri[]{interceptUri});
                    interceptUri = null;
                    return true;
                }

                String[] acceptTypes = fileChooserParams.getAcceptTypes();
                if (acceptTypes != null && acceptTypes.length > 0) {
                    if ("image/*".equals(acceptTypes[0])) {
                        openChooserDialog(valueCallback, acceptTypes);
                    } else {
                        openChooser(valueCallback, acceptTypes);
                    }
                    return true;
                }
                acceptTypes = new String[]{"*/*"};
                openChooser(valueCallback, acceptTypes);
                return true;
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                if (!TextUtils.isEmpty(title) && title.startsWith("500")) {
                    Log.i(TAG, "MainH5Activity_onReceivedTitle_标题:" + title);
                    showErrorPage();
                    ToastUtils.showShort("服务器异常,请稍后再试,错误码:500");
                    errorTitle = title;
                } else {
                    errorTitle = "";
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i(TAG, "consoleMessage:" + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                Log.i(TAG, "onShowCustomView()");
                //如果view 已经存在，则隐藏
                if (mCustomView != null) {
                    callback.onCustomViewHidden();
                    return;
                }

                mCustomView = view;
                mCustomView.setVisibility(View.VISIBLE);
                mCustomViewCallback = callback;
                mLayout.addView(mCustomView);
                mLayout.setVisibility(View.VISIBLE);
                mLayout.bringToFront();

                //设置横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                Log.i(TAG, "onHideCustomView()");

                if (mCustomView == null) {
                    return;
                }
                mCustomView.setVisibility(View.GONE);
                mLayout.removeView(mCustomView);
                mCustomView = null;
                mLayout.setVisibility(View.GONE);
                try {
                    mCustomViewCallback.onCustomViewHidden();
                } catch (Exception ignored) {
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

            }
        });
        //1.注意需要设置BridgeWebViewClient不然js交互无效，
        //2.自定义WebViewClient 通过webView打开链接，不调用系统浏览器
        webView.setWebViewClient(new BridgeWebViewClient(webView) {

            @Override
            protected boolean onCustomShouldOverrideUrlLoading(String url) {
                //拦截url
                if (interceptUrl(url)) {
                    ((Activity) mContext).finish();
                    return true;
                }

                //处理通用跳转
                if (WebViewUtils.handleCommonLink(url)) {
                    return true;
                }
                //处理三方app跳转
                if (WebViewUtils.handleThereAppLink(url)) {
                    return true;
                }
                return super.onCustomShouldOverrideUrlLoading(url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "MainH5Activity_onPageStarted_打开网页url:" + url);
                isError = false;
            }

            @Override
            public void onCustomPageFinished(WebView view, String url) {
                super.onCustomPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                if (isError || !TextUtils.isEmpty(errorTitle)) {
                    Log.i(TAG, "MainH5Activity_onCustomPageFinished_打开网页完成_错误:url:" + url);
                    showErrorPage();
                } else {
                    handler.postDelayed(() -> {
                        hideErrorPage();
                    }, 200);
                    handler.postDelayed(() -> {
                        if (isFirst) {
                            getH5Version();
                            isFirst = false;
                        }
                    }, 1500);
                }
                view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                Log.i(TAG, "MainH5Activity_onReceivedError_打开网页错误:url:" + webView.getUrl()
                        + ",errorCode:" + webResourceError.getErrorCode() + ",description:" + webResourceError.getDescription());
                Log.i(TAG, "MainH5Activity_onReceivedError_打开网页错误:request_url:" + webResourceRequest.getUrl().toString()
                        + ",errorCode:" + webResourceError.getErrorCode() + ",description:" + webResourceError.getDescription());
                if (webResourceRequest.isForMainFrame()) {
                    //标记上次打开错误页面 下次进入页面刷新一次
                    showErrorPage();
                }
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String url) {
                super.onReceivedError(webView, errorCode, description, url);
                if (android.os.Build.VERSION.SDK_INT < 23 && url.startsWith("http")) {
                    //API 23 6.0 以下
                    Log.i(TAG, "MainH5Activity_onReceivedError_6.0以下_打开网页错误:errorCode:" + errorCode
                            + ",description:" + description + ",url:" + url);
                    showErrorPage();
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                String requestUrl = webResourceRequest.getUrl().toString();
//                Log.i("TAG", "webResourceRequest:" + requestUrl);
                //视频下载
                if (requestUrl.contains("video/") || requestUrl.contains(".mp4")) {
                    String type = DownloadUtils.getContentType(requestUrl);
                    if (type.startsWith("video")) {
                        Log.i("TAG", "webResourceRequest_video:" + requestUrl);
                        String shortUrl = "";
                        if (url.contains("douyin.com") || url.contains("ixigua.com")) {
                            shortUrl = url;
                        }
                        String finalShortUrl = shortUrl;
                        runOnUiThread(() -> DownloadUtils.downloadDialog(mContext, requestUrl, finalShortUrl));
                    }
                }
                return super.shouldInterceptRequest(webView, webResourceRequest);
            }
        });

        //重写密码保存页面,需要返回true屏蔽弹窗
        webView.setWebChromeClientExtension(new ProxyWebChromeClientExtension() {
            @Override
            public boolean onSavePassword(
                    android.webkit.ValueCallback<String> callback,
                    String schemePlusHost,
                    String username,
                    String password,
                    String nameElement,
                    String passwordElement,
                    boolean isReplace) {
                return true;
            }
        });

        //跳转系统浏览器下载文件
        WebViewUtils.setDownloadListener(mContext, webView);

        //h5交互
        webView.registerHandler("writeLocalStorage", (data, function) -> {
        });


        //扫一扫
        webView.registerHandler("scanCodeFn", (data, function) -> {
            function.onCallBack("调用扫一扫!");
            Log.i(TAG, "MainH5Activity_scanCodeFn_调用扫一扫");
            if (WebViewCommonUtils.isDoubleClick()) {
                return;
            }
            scanCode();
        });


        //h5获取原生版本号
        webView.registerHandler("getAppVersion", (data, function) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("version", AppUtils.getAppVersionName());
            function.onCallBack(jsonObject.toJSONString());
        });

    }

    /**
     * 拦截url关闭
     */
    public boolean interceptUrl(String url) {
        String urlSdk = "Inter";
        Log.i(TAG, "MainH5Activity_interceptUrl_url:" + url
                + ",KEY_INTERCEPT_URL_SDK:" + urlSdk);
        if (url.toLowerCase().startsWith(urlSdk)) {
            return true;
        }
        return false;
    }


    /**
     * 扫码登录
     */
    private void scanCode() {
        PermissionUtils.permissionGroup(PermissionConstants.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        CaptureActivity.open(mContext);
                    }

                    @Override
                    public void onDenied() {
                        WebViewCommonUtils.showAlertDialog(mContext, getString(R.string.web_view_tip_camera_permission));
                    }
                }).request();
    }

    //----调用系统文件开始----

    private final int CHOOSE_PIC_REQUEST_CODE = 1000;
    private final int CHOOSE_CAMERA_REQUEST_CODE = 1001;
    private ValueCallback<Uri[]> mValueCallback;
    private Uri imageUri;

    /**
     * 选择相机,系统文件弹窗
     */
    private void openChooserDialog(ValueCallback<Uri[]> valueCallback, String[] acceptTypes) {
        final String[] items = {"图片", "拍照"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择方式");
        builder.setItems(items, (dialog, which) -> {
            if (which == 0) {
                openChooser(valueCallback, acceptTypes);
            } else if (which == 1) {
                openCameraChooser(valueCallback);
            }
            dialog.dismiss();
        });
        builder.show();
    }

    /**
     * 打开系统文件选择
     */
    private void openChooser(ValueCallback<Uri[]> valueCallback, String[] acceptTypes) {
        Log.i(TAG, "MainH5Activity_openChooser_打开系统选择文件");
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        mValueCallback = valueCallback;
                        Intent intent = new Intent();
                        intent.setType(WebViewFileChooseUtils.getType(acceptTypes));
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, WebViewFileChooseUtils.getExtra(acceptTypes));
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, CHOOSE_PIC_REQUEST_CODE);
                    }

                    @Override
                    public void onDenied() {
                        WebViewCommonUtils.showAlertDialog(mContext, getString(R.string.web_view_tip_file_permission));
                    }
                }).request();
    }

    /**
     * 打开相机
     */
    private void openCameraChooser(ValueCallback<Uri[]> valueCallback) {
        Log.i(TAG, "MainH5Activity_openCameraChooser_打开相机");
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE, PermissionConstants.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        mValueCallback = valueCallback;
                        File output = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DCIM).getAbsolutePath(), "tempImage.jpg");
                        FileUtils.delete(output);
                        FileUtils.createOrExistsFile(output);
                        imageUri = UriUtils.file2Uri(output);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CHOOSE_CAMERA_REQUEST_CODE);
                    }

                    @Override
                    public void onDenied() {
                        WebViewCommonUtils.showAlertDialog(mContext, getString(R.string.web_view_tip_camera_storage_permission));
                    }
                }).request();


    }

    /**
     * 选择文件回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (mValueCallback == null) {
                return;
            }
            switch (requestCode) {
                case CHOOSE_PIC_REQUEST_CODE:
                    //以下选择图片后的回调
                    if (resultCode == Activity.RESULT_OK && intent != null) {
                        Uri result = intent.getData();

                        if (result != null
                                && !TextUtils.isEmpty(result.getPath())
                                && result.getPath().contains("/")) {
                            String path = result.getPath();
                            path = path.substring(path.lastIndexOf("/"));
                            if (!path.contains(".")) {
                                //解决部分机型获取不到文件名问题
                                File file = WebViewMyUriUtils.uri2File(result);
                                result = UriUtils.file2Uri(file);
                            }
                        }
                        Log.i(TAG, "MainH5Activity_onActivityResult_选择文件:" + result);
                        mValueCallback.onReceiveValue(new Uri[]{result});
                    } else {
                        mValueCallback.onReceiveValue(null);
                    }
                    break;
                case CHOOSE_CAMERA_REQUEST_CODE:
                    //以下选择相机后的回调
                    if (resultCode == Activity.RESULT_OK) {
                        Log.i(TAG, "MainH5Activity_onActivityResult_相机:" + imageUri);
                        mValueCallback.onReceiveValue(new Uri[]{imageUri});
                    } else {
                        mValueCallback.onReceiveValue(null);
                    }
                    break;
            }
        } catch (Exception e) {
            Log.i(TAG, "MainH5Activity_onActivityResult_Exception:选择文件发送异常:" + e.getMessage());
            try {
                mValueCallback.onReceiveValue(null);
            } catch (Exception ignored) {
            }
        }
        mValueCallback = null;

    }

    //----调用系统文件结束----

    /**
     * 显示错误
     */
    private void showErrorPage() {
        isError = true;
        webView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        rlTitle.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏错误
     */
    private void hideErrorPage() {
        webView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        rlTitle.setVisibility(View.GONE);
    }

    /**
     * 获取h5版本
     */
    private void getH5Version() {
        webView.callHandler("getH5Version", "", data -> {
            String version = "";
            try {
                JSONObject jsonObject = JSONObject.parseObject(data);
                version = jsonObject.getString("version");
            } catch (Exception ignored) {
            }
            Log.i(TAG, "h5_version:" + version);
        });
    }

    /**
     * 横竖屏切换监听
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

}