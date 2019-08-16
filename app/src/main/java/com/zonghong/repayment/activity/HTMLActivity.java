package com.zonghong.repayment.activity;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityHhBinding;

public class HTMLActivity extends BaseActivity<ActivityHhBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_hh;
    }

    @Override
    public void initUI() {

        binding.includeToolbar.tvTitle.setText("客服");

        binding.wv.getSettings().setJavaScriptEnabled(true);
        binding.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.wv.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.wv.loadUrl("https://dwz.cn/IkBojfH8");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
