package com.chen.phonehelper.presenter.impl;


import com.chen.base.activity.BaseActivity;
import com.chen.base.presenter.impl.BasePresenter;
import com.chen.phonehelper.presenter.IShowRunningTimePresenter;
import com.chen.phonehelper.ui.IShowRunningTimeView;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class ShowRunningTimePresenter
        extends BasePresenter<IShowRunningTimeView, BaseActivity>
        implements IShowRunningTimePresenter {
    public ShowRunningTimePresenter(IShowRunningTimeView view) {
        super(view);
    }
}
