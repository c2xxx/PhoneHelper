package com.chen.phonehelper;

import android.app.Application;
import android.content.Context;

import com.chen.phonehelper.domain.AddTask;

import cn.broadin.libutils.AppContext;
import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2017/9/12.
 */

public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        AppContext.setContext(this.getApplicationContext());
        Logger.d("App onCreate");

        new AddTask().init();
    }
}
