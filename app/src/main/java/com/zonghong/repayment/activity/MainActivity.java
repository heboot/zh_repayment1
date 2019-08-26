package com.zonghong.repayment.activity;


import android.content.Intent;
import android.widget.Toast;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.repayment.MAPP;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityMainBinding;
import com.zonghong.repayment.http.HttpObserver;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        binding.qlytInfo.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytOrder.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytService.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x10)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
    }

    @Override
    public void initData() {
        binding.tvMobile.setText(PreferencesUtils.getString(MAPP.mapp,MKey.PHONE));
        getIndexData();
    }

    @Override
    public void initListener() {
        binding.mrv.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                getIndexData();
            }
        });
        binding.qlytOrder.setOnClickListener((v)->{
            if(MAPP.mapp.getDataMap() == null){
                Toast.makeText(MainActivity.this,"获取数据失败，请稍后重试",Toast.LENGTH_SHORT).show();
                getIndexData();
                return;
            }
            Intent intent = new Intent(this,OrderActivity.class);
            startActivity(intent);
        });
        binding.qlytInfo.setOnClickListener((v)->{

            Intent intent = new Intent(this,InfoActivity.class);
            startActivity(intent);
        });
        binding.qlytService.setOnClickListener((v)->{
            Intent intent = new Intent(this,HTMLActivity.class);
            startActivity(intent);
        });
    }


    private void getIndexData() {

        HttpClient.Builder.getServer().index().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {
                binding.mrv.finishRefresh();
                MAPP.mapp.setDataMap(baseBean.getData());
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                binding.mrv.finishRefresh();
                tipDialog = DialogUtils.getFailDialog(MainActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }
}
