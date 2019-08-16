package com.zonghong.repayment.base;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.waw.hr.mutils.MStatusBarUtils;
import com.waw.hr.mutils.rxbus.RxBus;
import com.zonghong.repayment.R;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by heboot on 2018/1/17.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends FragmentActivity implements BaseUIInterface {

    protected String TAG = this.getClass().getName();

    protected T binding;

    private CompositeDisposable compositeDisposable;

    protected Map<String, Object> params;

    protected Observable<Object> rxObservable;

    public int pageSize = 15, sp = 1, total;


    protected QMUITipDialog tipDialog;

    protected QMUITipDialog loadingDialog;

    protected View vBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = new HashMap<>();
        QMUIStatusBarHelper.translucent(this);
        MStatusBarUtils.setActivityLightMode(this);
        initContentView();
        initUI();
        initData();
        rxBusObservers();
        initListener();
    }

    protected void initContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        vBack = findViewById(R.id.v_back);
        if(vBack != null){
            vBack.setOnClickListener((v)->{
                if(vBack.getVisibility() == View.VISIBLE){
                    finish();
                }
            });
        }
    }

    protected void setBackVisibility(int v){
        if(vBack != null){
            vBack.setVisibility(v);
        }
    }


    @LayoutRes
    protected abstract int getLayoutId();

    public void addDisposable(Disposable disposable) {
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
        this.compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {


        if (this.compositeDisposable != null && !compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    private void rxBusObservers() {
        rxObservable = RxBus.getInstance().toObserverable();
        rxObservable.subscribe(new Observer<Object>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
                this.disposable = d;
            }

            @Override
            public void onNext(Object o) {
                if(o.equals("logout")){
                    finish();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
