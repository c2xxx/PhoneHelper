package cn.broadin.libutils.task;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.MyDebugConfig;

/**
 * 定时任务管理器
 * Created by ChenHui on 2016/6/2.
 */
public class TimingTaskManager {
    private boolean isPrintLog = false;
    //单例
    private static TimingTaskManager instance = null;
    private List<TimingTask> tasks = new ArrayList<>();

    public static void init() {
        getInstance();
    }

    private TimingTaskManager() {
        doStartTimer();

        //使用demo
//        //日常任务
//        TimingTask task3 = new TimingTask(TimingTask.TYPE_INTERVAL, 60 * 1000, 100) {
//            @Override
//            public void doTask() {
////                Logger.d("发送唤醒广播");
//                Intent weakUp = new Intent("cn.broadin.leme.WAKE_UP");
//                weakUp.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                AppContext.getContext().sendBroadcast(weakUp);
//            }
//        };
//        task3.setName("日常任务");
//        addTask(task3);
    }

    private void doStartTimer() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                executeTasks();
            }
        }, 1000, 1000);// 设定指定的时间time,此处为1000毫秒
    }

    int index = 0;

    private void executeTasks() {
        synchronized (TimingTaskManager.class) {
            if (isPrintLog && MyDebugConfig.isDebug() && index++ % 30 == 0) {
                Logger.object("当前时间：" + System.currentTimeMillis() + "  任务栈：\n", tasks);
            }
            for (Iterator<TimingTask> iter = tasks.iterator(); iter.hasNext(); ) {
                TimingTask task = iter.next();
                if (task.needExecuteNow()) {
                    try {
                        task.execute();
                        if (task.isDestroyed()) {
                            iter.remove();
                        }
                    } catch (Exception e) {
                        Logger.e(e);
                    }
                }
            }
        }
    }

    public static TimingTaskManager getInstance() {
        if (instance == null) {
            synchronized (TimingTaskManager.class) {
                if (instance == null) {
                    instance = new TimingTaskManager();
                }
            }
        }
        return instance;
    }

    public void addTask(TimingTask task) {
        synchronized (TimingTaskManager.class) {
//            Logger.d("addTask " + task.getName());
            tasks.add(task);
        }
    }

    /**
     * 添加刷新蜻蜓FM token的任务，确保唯一
     *
     * @param task
     */
    public void addFmTokenTask(TimingTask task) {
        String uniqueTag = "FM_TOKEN";
        task.setTag(uniqueTag);
        removeTaskByTag(uniqueTag);
        addTask(task);
    }

    /**
     * 添加任务
     *
     * @param task
     */
    public void addSingleTask(TimingTask task, String uniqueTag) {
        task.setTag(uniqueTag);
        removeTaskByTag(uniqueTag);
        addTask(task);
    }

    private void removeTaskByTag(String tag) {
        tag = "" + tag;
        synchronized (TimingTaskManager.class) {
            for (Iterator<TimingTask> iter = tasks.iterator(); iter.hasNext(); ) {
                TimingTask task = iter.next();
                if (TextUtils.equals(tag, task.getTag())) {
                    iter.remove();
                }
            }
        }
    }
}
