package com.chen.phonehelper.domain;

import android.content.Intent;

import com.chen.phonehelper.MyApplication;
import com.chen.phonehelper.bean.RunningTime;
import com.chen.phonehelper.db.RunningTimeDao;
import com.chen.phonehelper.service.RunAlways;

import java.text.SimpleDateFormat;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.task.TimingTask;
import cn.broadin.libutils.task.TimingTaskManager;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by ChenHui on 2017/9/12.
 */

public class AddTask {
    public void init() {
        markRunningTime();
//        markCurrentApp();

        Intent intent = new Intent(MyApplication.getContext(), RunAlways.class);
        MyApplication.getContext().startService(intent);
    }

    private void markCurrentApp() {
        TimingTask task = new TimingTask(TimingTask.TYPE_INTERVAL, 60 * 1000, 0) {
            @Override
            public void doTask() {
                Logger.d("当前前台的APP:");
            }
        };
        task.setName("markRunningTime");
        TimingTaskManager.getInstance().addTask(task);
    }

    /**
     * 记录APP运行时间
     */
    private void markRunningTime() {
        TimingTask task = new TimingTask(TimingTask.TYPE_INTERVAL, 60 * 1000, 0) {
            @Override
            public void doTask() {
//                Logger.d("A");
                Observable.just("").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
//                        Logger.d("B");
                        RunningTimeDao dao = new RunningTimeDao(MyApplication.getContext());
                        RunningTime time = new RunningTime();
                        time.setTimeNow();
                        dao.insert(time);
//                        Logger.d("C");
                    }
                });
            }
        };
        task.setName("markRunningTime");
        TimingTaskManager.getInstance().addTask(task);
    }
}
