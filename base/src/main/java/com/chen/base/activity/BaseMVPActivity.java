package com.chen.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chen.base.presenter.IBasePresenter;


/**
 * Created by ChenHui on 2016/12/26.
 */

public abstract class BaseMVPActivity<P extends IBasePresenter> extends BaseActivity {

    protected P mPresenter;

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createActivityPresenter();
        super.onCreate(savedInstanceState);
        if (getPresenter() != null)
            getPresenter().onCreate();
    }

    protected abstract P createActivityPresenter();

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null)
            getPresenter().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPresenter() != null)
            getPresenter().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getPresenter() != null)
            getPresenter().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getPresenter() != null)
            getPresenter().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null)
            getPresenter().onDestroy();
    }
}
