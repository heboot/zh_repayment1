package com.zonghong.repayment.activity;

import android.content.Intent;
import android.graphics.Color;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ObserableUtils;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.repayment.MAPP;
import com.zonghong.repayment.R;
import com.zonghong.repayment.base.BaseActivity;
import com.zonghong.repayment.databinding.ActivityLoginBinding;
import com.zonghong.repayment.http.HttpObserver;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private String code;

    private Observer countDownObserver;

    private Observable observable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        binding.qlytMobile.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytVcode.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytSendcode.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytSendcode.setShadowColor(Color.parseColor("#94cbcbcb"));

        binding.qlytNext.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x18)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytNext.setShadowColor(Color.parseColor("#94cbcbcb"));
    }

    @Override
    public void initData() {
        countDownObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer <= 0) {
                    binding.tvSendcode.setEnabled(true);
                    binding.tvSendcode.setText("获取验证码");
                } else {
                    binding.tvSendcode.setText(integer + "");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void initListener() {
        binding.tvSendcode.setOnClickListener((v) -> {

            sendCode();
        });
        binding.qlytNext.setOnClickListener((v) -> {
            checkCode();
        });
        binding.tvLogin.setOnClickListener((v) -> {
            checkCode();
        });
    }


    private void sendCode() {
        if (StringUtils.isEmpty(binding.etMobile.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }

        binding.tvSendcode.setEnabled(false);

        params.put(MKey.PHONE, binding.etMobile.getText());

        HttpClient.Builder.getServer().getcode(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {

                code = baseBean.getData();

                observable = ObserableUtils.countdown(60);

                observable.subscribe(countDownObserver);
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                binding.tvSendcode.setEnabled(true);
                tipDialog = DialogUtils.getFailDialog(LoginActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void checkCode() {
        if (StringUtils.isEmpty(binding.etMobile.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }

        if (StringUtils.isEmpty(binding.etVcode.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入验证码", true);
            tipDialog.show();
            return;
        }
        params.put(MKey.PHONE, binding.etMobile.getText());
        params.put(MKey.CODE, binding.etVcode.getText());
        HttpClient.Builder.getServer().checkVerify(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                PreferencesUtils.putString(MAPP.mapp, MKey.PHONE, binding.etMobile.getText().toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                tipDialog = DialogUtils.getFailDialog(LoginActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
