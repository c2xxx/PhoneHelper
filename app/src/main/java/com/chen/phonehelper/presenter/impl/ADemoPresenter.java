package com.chen.phonehelper.presenter.impl;


import com.chen.base.activity.BaseActivity;
import com.chen.base.presenter.impl.BasePresenter;
import com.chen.phonehelper.presenter.IADemoPresenter;
import com.chen.phonehelper.ui.IADemoView;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class ADemoPresenter
        extends BasePresenter<IADemoView, BaseActivity>
        implements IADemoPresenter {
    public ADemoPresenter(IADemoView view) {
        super(view);
    }
}
