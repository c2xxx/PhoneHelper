package com.chen.apptest;

import android.app.Application;

import cn.broadin.libutils.AppContext;

/**
 * Created by ChenHui on 2017/9/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.setContext(this);
    }
}
