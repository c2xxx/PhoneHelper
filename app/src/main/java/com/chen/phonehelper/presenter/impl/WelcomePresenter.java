package com.chen.phonehelper.presenter.impl;


import com.chen.base.activity.BaseActivity;
import com.chen.base.presenter.impl.BasePresenter;
import com.chen.phonehelper.presenter.IWelcomePresenter;
import com.chen.phonehelper.ui.IWelcomeView;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class WelcomePresenter
        extends BasePresenter<IWelcomeView, BaseActivity>
        implements IWelcomePresenter {
    public WelcomePresenter(IWelcomeView view) {
        super(view);
    }
}
