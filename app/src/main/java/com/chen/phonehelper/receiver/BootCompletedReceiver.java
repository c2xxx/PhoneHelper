package com.chen.phonehelper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.broadin.libutils.Logger;


/**
 * 开机启动
 * Created by ChenHui on 2016/8/15.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (!"cn.broadin.leme.WAKE_UP".equals(intent.getAction())) {
                Logger.d(intent.getAction());
            }
        }
    }
}