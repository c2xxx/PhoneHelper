package com.chen.phonehelper.activity;

import com.chen.base.activity.BaseMVPActivity;
import com.chen.phonehelper.R;
import com.chen.phonehelper.presenter.IShowRunningTimePresenter;
import com.chen.phonehelper.presenter.impl.ShowRunningTimePresenter;
import com.chen.phonehelper.ui.IShowRunningTimeView;

public class ShowRunningTimeActivity
        extends BaseMVPActivity<IShowRunningTimePresenter>
        implements IShowRunningTimeView {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_show_running_time;
    }

    @Override
    protected void initView() {
        setTitle("显示运行时间");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected IShowRunningTimePresenter createActivityPresenter() {
        return new ShowRunningTimePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
