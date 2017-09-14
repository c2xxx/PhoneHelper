package com.chen.apptest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.chen.apptest.other.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.broadin.libutils.LeakUtils;
import cn.broadin.libutils.Logger;
import cn.broadin.libutils.dialog.DiaLogFactory;

/**
 * Created by ChenHui on 2017/9/4.
 */

public abstract class BaseActivity extends FragmentActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);  //注册
        initProperties();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        onLoadDataFinish();
        LeakUtils.doDestroy(this);
    }

    protected void initProperties() {
    }

    protected abstract int getContentLayout();

    protected abstract void initView();

    protected abstract void initData();

    //调试用
    protected boolean isPrintCurrentFocus = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isPrintCurrentFocus) {
            Logger.d("currentFocus=" + getCurrentFocus() + " " + this.getClass().getSimpleName());
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 响应事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(EventType event) {
    }

    /**
     * 主线程中响应事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType event) {
        if (event.getType() == EventType.TYPE.EVENT_APP_EXIT) {
            finish();
        }
    }

    public void onLoadDataStart() {
        DiaLogFactory.showLoadingDialog(this);
    }

    public void onLoadDataFinish() {
        DiaLogFactory.dismissDialog();
    }


}
