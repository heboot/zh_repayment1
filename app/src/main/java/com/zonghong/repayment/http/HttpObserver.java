package com.zonghong.repayment.http;


import com.alibaba.fastjson.JSON;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MCode;
import com.waw.hr.mutils.NetWorkUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.repayment.MAPP;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by heboot on 2018/1/17.
 */

public abstract class HttpObserver<T> implements Observer<BaseBean<T>> {

    private String TAG = this.getClass().getName();

    private Disposable disposable;


    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        if (!NetWorkUtils.isConnectedByState(MAPP.mapp)) {
            onComplete();
            this.disposable.dispose();
            return;
        }
//        httpCallBack.onSubscribe(d);
    }


    @Override
    public void onNext(BaseBean<T> baseBean) {
        LogUtil.e(TAG, JSON.toJSONString(baseBean));
        if (baseBean.getCode() != MCode.HTTP_CODE.SUCCESS || baseBean == null) {
            if (baseBean.getCode() == MCode.HTTP_CODE.TOKEN_ERROR) {
//                IntentUtils.doIntent(MAPP.mapp.getCurrentActivity(), LoginActivity.class);
            }
            this.disposable.dispose();
            try {
                onError(baseBean);
            } catch (Exception ex) {
                onError(ex);
            }
            return;
        }
//        UserService.getInstance().setSign_key(baseBean.getSign_key());
//        try {
//            if (!StringUtils.isEmpty(((BaseBean<BaseBeanEntity>) baseBean).getData().getToken())) {
//                UserService.getInstance().setToken(((BaseBean<BaseBeanEntity>) baseBean).getData().getToken());
//            }
//            if (((BaseBean<BaseBeanEntity>) baseBean).getData().getUser() != null) {
//                if (UserService.getInstance().getUser() != null) {
//                    if (((BaseBean<BaseBeanEntity>) baseBean).getData().getUser().getId().intValue() == UserService.getInstance().getUser().getId().intValue()) {
//                        UserService.getInstance().setUser(((BaseBean<BaseBeanEntity>) baseBean).getData().getUser());
//                    }
//                }
//            }
//        } catch (Exception e) {
////            onError(e);
//        }


        onSuccess(baseBean);
    }

    public abstract void onSuccess(BaseBean<T> baseBean);

    public abstract void onError(BaseBean<T> baseBean);


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
    }
}
