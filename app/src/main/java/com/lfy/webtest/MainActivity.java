package com.lfy.webtest;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);

        init();
    }

    private void init(){
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);

        set.setAllowFileAccess(false);
        set.setAllowFileAccessFromFileURLs(false);
        set.setAllowUniversalAccessFromFileURLs(false);

        set.setLoadsImagesAutomatically(true);
        set.setBlockNetworkImage(false);//解决图片不显示
        //set.setJavaScriptCanOpenWindowsAutomatically(true);


        set.setDefaultTextEncodingName("UTF-8");
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);
        set.setDomStorageEnabled(true);
        set.setDatabaseEnabled(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.loadUrl("https://iq.uniqlo.cn/");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
            webView.pauseTimers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.resumeTimers();
            webView.onResume();
        }
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient{

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            handler.proceed();
        }


    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

            Log.d("liufuyi", consoleMessage.message() + " -- From line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());

            return super.onConsoleMessage(consoleMessage);
        }
    }

}
