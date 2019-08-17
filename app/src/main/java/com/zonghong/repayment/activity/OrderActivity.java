package com.zonghong.repayment.activity;

import android.content.Intent;
import android.widget.Toast;

import com.zonghong.repayment.MAPP;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityOrderBinding;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("我的订单");
    }

    @Override
    public void initData() {
        binding.tvMoney.setText((String)MAPP.mapp.getDataMap().get("money"));
    }

    @Override
    public void initListener() {
        binding.tvLogin.setOnClickListener((v)->{

            Intent intent = new Intent(this,ChannelActivity.class);
            startActivity(intent);
        });
    }
}
