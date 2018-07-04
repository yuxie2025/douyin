package com.yuxie.demo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuxie.demo.R;


public class WebViewJsActivity extends AppCompatActivity {

    WebView wvTestJs;
    ProgressBar pbLoadUrl;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_js);

        wvTestJs = (WebView) findViewById(R.id.wv_test_js);
        pbLoadUrl = (ProgressBar) findViewById(R.id.pb_load_url);

        //获得WebSetting对象,支持js脚本,可访问文件,支持缩放,以及编码方式
        WebSettings webSettings = wvTestJs.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持js脚本
        webSettings.setAllowFileAccess(true);//设置允许访问文件数据库
        webSettings.setBuiltInZoomControls(true);//设置支持缩放
        webSettings.setDefaultTextEncodingName("UTF-8");//设置默认编码

        wvTestJs.setWebViewClient(new WebViewClient());//处理网页加载
        wvTestJs.setWebChromeClient(new MyWebChromeClient());//处理js
        wvTestJs.setDownloadListener(new MyDowoloadListener());//处理下载监听
        wvTestJs.loadUrl("file:///android_asset/jsdemo.html");

    }

    public class MyDowoloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            //调用其它浏览器下载文件(如果没有设置默认浏览器,且安装了多个浏览器,会先弹出选择浏览器)
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public class MyWebViewClient extends WebViewClient {
        //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            view.loadUrl(url);
            return true;
        }
    }

    //这里需要自定义一个类实现WebChromeClient类,并重写三种不同对话框的处理方法
    //分别重写onJsAlert,onJsConfirm,onJsPrompt方法
    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {//实现加载过程显示进度条
            if (newProgress == 100) {
                pbLoadUrl.setVisibility(View.GONE);
            } else {
                pbLoadUrl.setVisibility(View.VISIBLE);
                pbLoadUrl.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // onJsAlert  :警告框(WebView上alert无效，需要定制WebChromeClient处理弹出)
            //创建一个Builder来显示网页中的对话框(仅有确定)
            new AlertDialog.Builder(mContext).setTitle("Alert对话框").setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    }).setCancelable(false).show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            //onJsConfirm : 确定框.(有取消和确定)
            new AlertDialog.Builder(mContext).setTitle("Confirm对话框").setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).setCancelable(false).show();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            //可以把输入的文本传回,js脚本 result.confirm(value);
            //onJsPrompt : 提示框.
            //①获得一个LayoutInflater对象factory,加载指定布局成相应对象
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            final View myview = inflater.inflate(R.layout.prompt_view, null);
            //设置TextView对应网页中的提示信息,edit设置来自于网页的默认文字
            ((TextView) myview.findViewById(R.id.text)).setText(message);
            ((EditText) myview.findViewById(R.id.edit)).setText(defaultValue);
            //定义对话框上的确定按钮
            new AlertDialog.Builder(mContext).setTitle("Prompt对话框").setView(myview)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //单机确定后取得输入的值,传给网页处理
                            String value = ((EditText) myview.findViewById(R.id.edit)).getText().toString();
                            result.confirm(value);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).show();
            return true;
        }
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //当webview不是处于第一页面时，返回上一个页面
            if (wvTestJs.canGoBack()) {
                wvTestJs.goBack();
                return true;
            }
//            else {
//                System.exit(0);//退出应用
//            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
