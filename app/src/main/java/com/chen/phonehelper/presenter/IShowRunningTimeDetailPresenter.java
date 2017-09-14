package com.chen.phonehelper.presenter;

import com.chen.base.presenter.IBasePresenter;

/**
 * Created by ChenHui on 2017/6/27.
 */

public interface IShowRunningTimeDetailPresenter extends IBasePresenter {
    /**
     * @param begin 开头时间（含）
     * @param end   结尾时间（不含）
     */
    void loadDataList(long begin, long end);
}
