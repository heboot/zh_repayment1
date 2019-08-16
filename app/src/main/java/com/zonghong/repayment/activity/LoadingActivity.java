package com.zonghong.repayment.activity;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.StringUtils;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityLoadingBinding;

public class LoadingActivity extends BaseActivity<ActivityLoadingBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        Intent intent;
        if (StringUtils.isEmpty(PreferencesUtils.getString(this, MKey.PHONE))) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void initListener() {

    }
}
