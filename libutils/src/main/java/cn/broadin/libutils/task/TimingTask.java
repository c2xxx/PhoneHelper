package cn.broadin.libutils.task;

/**
 * 定时任务
 * Created by ChenHui on 2016/6/2.
 */
public abstract class TimingTask {
    /**
     * 到指定时间会执行
     */
    public static final int TYPE_TIMING = 1;
    /**
     * 每间隔一定时间会执行
     */
    public static final int TYPE_INTERVAL = 2;
    public static final int TYPE_EVERYDAY_未实现 = 3;//每天的几点执行
    private int taskType = TYPE_INTERVAL;
    private long executeTime;//执行时间，超过这个时间会执行
    private long executeInterval = 5 * 60 * 1000;//执行间隔时间,最小1000毫秒
    private long preExecuteTime = System.currentTimeMillis();//上次执行时间
    private boolean destroyed = false;//这个任务是否已经销毁
    private String name = null;//名称
    private String tag;//标记
    private long firstTime = -1;//首次任务时间
    private int executeCount = -1;//执行次数

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @param taskType 类型
     * @param time     如果是定时任务，则传入定时时间，如果是轮询任务，则传入间隔，单位毫秒
     */
    public TimingTask(int taskType, long time) {
        this.taskType = taskType;
        switch (taskType) {
            case TYPE_TIMING:
                executeTime = time;
                break;
            case TYPE_INTERVAL:
                executeInterval = time;
                break;
        }
    }

    /**
     * @param taskType       类型
     * @param time           如果是定时任务，则传入定时时间，如果是轮询任务，则传入间隔，单位毫秒
     * @param firstTimeDelay 首次任务延时时间，对TYPE_INTERVAL类型有效
     */
    public TimingTask(int taskType, long time, long firstTimeDelay) {
        this(taskType, time, firstTimeDelay, -1);
    }

    /**
     * @param taskType       类型
     * @param time           如果是定时任务，则传入定时时间，如果是轮询任务，则传入间隔，单位毫秒
     * @param firstTimeDelay 首次任务延时时间，对TYPE_INTERVAL类型有效
     * @param executeCount   执行次数，对TYPE_INTERVAL类型有效
     */
    public TimingTask(int taskType, long time, long firstTimeDelay, int executeCount) {
        this.taskType = taskType;
        this.executeCount = executeCount;
        switch (taskType) {
            case TYPE_TIMING:
                executeTime = time;
                break;
            case TYPE_INTERVAL:
                executeInterval = time;
                break;
        }
        firstTime = System.currentTimeMillis() + firstTimeDelay;
    }

    /**
     * 当前时间是否需要执行
     *
     * @return
     */
    public boolean needExecuteNow() {
        if (destroyed) {
            return false;
        }
        long timeNow = System.currentTimeMillis();
        switch (taskType) {
            case TYPE_TIMING:
                return (executeTime - timeNow < 0);
            case TYPE_INTERVAL:
                if (firstTime >= 0) {//首次还没执行的，对比首次执行时间
                    if (timeNow >= firstTime) {
                        firstTime = -1;
                        return true;
                    }
                    return false;
                }
                //允许几毫秒的偏差
                return (timeNow - preExecuteTime + 10 >= executeInterval);
        }
        return false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * 执行
     */
    public final void execute() {
        preExecute();
        doTask();
    }

    public abstract void doTask();

    private void preExecute() {
        preExecuteTime = System.currentTimeMillis();
        switch (taskType) {
            case TYPE_TIMING:
                destroyed = true;
                break;
            case TYPE_INTERVAL:
                if (executeCount > 0) {
                    executeCount--;
                }
                if (executeCount == 0) {
                    destroyed = true;
                }
                break;
        }
    }

    //以下get方法只为方便打印log

    public int getTaskType() {
        return taskType;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public long getExecuteInterval() {
        return executeInterval;
    }

    public long getPreExecuteTime() {
        return preExecuteTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
