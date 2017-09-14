package com.chen.phonehelper.presenter.impl;


import com.chen.base.activity.BaseActivity;
import com.chen.base.bean.ContentBean;
import com.chen.base.presenter.impl.BasePresenter;
import com.chen.phonehelper.MyApplication;
import com.chen.phonehelper.bean.RunningTime;
import com.chen.phonehelper.bean.RunningTimeShow;
import com.chen.phonehelper.db.RunningTimeDao;
import com.chen.phonehelper.presenter.IShowRunningTimeDetailPresenter;
import com.chen.phonehelper.ui.IShowRunningTimeDetailView;
import com.chen.phonehelper.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.helper.ThreadHelper;

/**
 * Created by ChenHui on 2017/6/27.
 */

public class ShowRunningTimeDetailPresenter
        extends BasePresenter<IShowRunningTimeDetailView, BaseActivity>
        implements IShowRunningTimeDetailPresenter {
    public ShowRunningTimeDetailPresenter(IShowRunningTimeDetailView view) {
        super(view);
    }

    @Override
    public void loadDataList(long begin, long end) {
        List<RunningTimeShow> listAll = new ArrayList<>();
        long fengZhong = 60 * 1000;
        SimpleDateFormat sdf = Utils.getDefaultFenZhongDateFormat();
        if (end > begin)
            while (begin < end) {
                RunningTimeShow rts = new RunningTimeShow();
                rts.setTime(sdf.format(begin));
                listAll.add(rts);
                begin += fengZhong;
            }
        new ThreadHelper<List<RunningTimeShow>, List<ContentBean>>() {
            @Override
            protected List<ContentBean> runOnBackground(List<RunningTimeShow> show) {
                RunningTimeDao dao = new RunningTimeDao(MyApplication.getContext());
                List<RunningTime> items = dao.selectBetweenList(show.get(0).getTime(), show.get(show.size() - 1).getTime());
                Logger.d("items size：" + items.size());
                List<ContentBean> list = new ArrayList<>();
                if (items != null) {
                    Set<String> timeSet = new HashSet<>();
                    for (RunningTime item : items) {
                        String time = item.getTime();
//                        2017-09-12 16:48:12
                        if (time != null && time.length() > 16) {
                            time = time.substring(0, 16);
                        }
                        timeSet.add(time);
//                        Logger.d("运行时间：" + time);
                    }
                    for (RunningTimeShow item : show) {
                        ContentBean content = new ContentBean();
                        item.setRunning(timeSet.contains(item.getTime()));
                        content.setText(item.getTime());
                        content.setSelected(item.isRunning());
                        list.add(content);
                    }

                }
                return list;
            }

            @Override
            protected void onEnd(List<ContentBean> list) {
//                ToastUtil.show("list size =" + list.size());
                getView().displayList(list);
            }
        }.executeIO(listAll);
    }
}
