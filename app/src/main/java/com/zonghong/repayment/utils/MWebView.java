package com.zonghong.repayment.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MWebView extends WebView {

    private MChromeClient mChromeClient;

    public MWebView(Context context) {
        super(context);
    }

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initWebView() {
        mChromeClient = new MChromeClient();
        setWebChromeClient(mChromeClient);

        WebSettings webviewSettings = getSettings();
        // 不支持缩放
        webviewSettings.setSupportZoom(false);
        // 自适应屏幕大小
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + "cache/";
        webviewSettings.setAppCachePath(cacheDirPath);
        webviewSettings.setAppCacheEnabled(true);
        webviewSettings.setJavaScriptEnabled(true);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setAllowFileAccess(true);
        webviewSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }


}
