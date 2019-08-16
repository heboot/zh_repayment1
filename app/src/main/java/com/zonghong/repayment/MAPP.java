package com.zonghong.repayment;

import android.app.Application;

import java.util.Map;

public class MAPP extends Application {
    
    public static MAPP mapp;

    private Map dataMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mapp = this;
    }

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }
}
