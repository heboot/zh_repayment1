package com.zonghong.repayment.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.zonghong.repayment.MAPP;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityChannelBinding;

public class ChannelActivity extends BaseActivity<ActivityChannelBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("还款渠道");
        binding.qlytService.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
    }

    @Override
    public void initData() {
        binding.tvBank.setText((String) MAPP.mapp.getDataMap().get("bank_name"));
        binding.tvName.setText((String) MAPP.mapp.getDataMap().get("user_name"));
        binding.tvNum.setText((String) MAPP.mapp.getDataMap().get("bank_card"));



        SpannableString spannableString = new SpannableString("打开微信>收付款>转账到银行卡备注姓名+ 手机号后四位，支付完成后请截图给下方在线客服，为您办理销账， 还款后有短信通知。");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 33,spannableString.length()-18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvTip.append(spannableString);

    }

    @Override
    public void initListener() {
        binding.qlytService.setOnClickListener((v) -> {
            Intent intent = new Intent(this, HTMLActivity.class);
            startActivity(intent);
        });
        binding.tvCopy.setOnClickListener((v) -> {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", (String) MAPP.mapp.getDataMap().get("bank_card"));
// 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toast.makeText(ChannelActivity.this, "已复制到粘贴板", Toast.LENGTH_LONG).show();
        });
    }
}
