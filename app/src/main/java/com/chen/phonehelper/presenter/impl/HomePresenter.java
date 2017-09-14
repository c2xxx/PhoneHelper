package com.chen.phonehelper.presenter.impl;


import com.chen.base.activity.BaseActivity;
import com.chen.base.presenter.impl.BasePresenter;
import com.chen.phonehelper.presenter.IHomePresenter;
import com.chen.phonehelper.ui.IHomeView;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class HomePresenter
        extends BasePresenter<IHomeView, BaseActivity>
        implements IHomePresenter {
    public HomePresenter(IHomeView view) {
        super(view);
    }
}
