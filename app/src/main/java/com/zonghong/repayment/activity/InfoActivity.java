package com.zonghong.repayment.activity;

import android.content.Intent;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.rxbus.RxBus;
import com.zonghong.repayment.MAPP;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityInfoBinding;

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {

    private QMUIBottomSheet logoutSheet;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("用户信息");
    }

    @Override
    public void initData() {
        logoutSheet = DialogUtils.getLogoutBottomSheet(this, new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                if(position == 2){
                    logoutSheet.dismiss();
                }else{
                    logoutSheet.dismiss();
                    PreferencesUtils.putString(MAPP.mapp, MKey.PHONE,"");
                    RxBus.getInstance().post("logout");
                    Intent intent = new Intent(InfoActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void initListener() {
        binding.tvLogout.setOnClickListener((v) -> {
            logoutSheet.show();
        });
    }
}
