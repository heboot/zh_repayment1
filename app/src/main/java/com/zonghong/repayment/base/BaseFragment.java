package com.zonghong.repayment.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.waw.hr.mutils.rxbus.RxBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by heboot on 2018/3/1.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment implements BaseUIInterface{

    protected String TAG = this.getClass().getName();

    protected T binding;

    private CompositeDisposable compositeDisposable;

    protected Observable<Object> rxObservable;

    protected Map<String, Object> params;

    protected int pageSize = 15, sp = 1, total;


    protected LayoutInflater inflater;


    protected FragmentActivity _mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        params = new HashMap<>();
        this.inflater = inflater;
        initContentView(inflater, container);
        initUI();
        initData();
        rxBusObservers();
        initListener();
        return binding.getRoot();
    }


    private void rxBusObservers() {
        rxObservable = RxBus.getInstance().toObserverable();
        rxObservable.subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(Object o) {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    protected void initContentView(LayoutInflater inflater, ViewGroup con) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), con, false);
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
    public void onDestroy() {
        if (this.compositeDisposable != null && !compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
        super.onDestroy();
    }

}
