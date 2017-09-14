package com.chen.apptest.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.chen.apptest.R;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.ToastUtil;

/**
 * Created by ChenHui on 2017/9/7.
 */

public class HandlerTestActivity extends OneButtonActivity {
    public static final int MSG_A = 1;
    public static final int MSG_B = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.d("msg.what=" + msg.what);
            Context context = imageView.getContext();
            boolean isFinishing = false;
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                isFinishing = activity.isFinishing();
                Logger.d("Context=" + activity.getClass().getSimpleName() + " finishing:" + isFinishing);
            } else {
                Logger.d("Context=" + context);
            }
            if (!isFinishing) {
                handler.sendEmptyMessageDelayed(2, 1000);
            }
        }
    };

    public void oneButtonClick() {
        ToastUtil.show("ABCD");
        int res = R.drawable.le_bg_children;
        imageView.setImageResource(res);
        handler.sendEmptyMessageDelayed(2, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
