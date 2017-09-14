package com.chen.phonehelper.ui;

import com.chen.base.bean.ContentBean;
import com.chen.base.ui.IBaseView;

import java.util.List;

/**
 * Created by ChenHui on 2017/6/27.
 */

public interface IShowRunningTimeDetailView extends IBaseView {
    void displayList(List<ContentBean> list);
}
