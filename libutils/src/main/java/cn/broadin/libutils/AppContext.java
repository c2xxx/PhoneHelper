package cn.broadin.libutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.broadin.libutils.canary.AppBlockCanaryContext;
import cn.broadin.libutils.task.TimingTaskManager;
import io.reactivex.functions.Consumer;

/**
 * 应用初始化的时候设置application的context
 * Created by ChenHui on 2017/6/27.
 */

public class AppContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * app初始化的时候调用
     *
     * @param context
     */
    public static void setContext(Context context) {
        AppContext.context = context;
        TimingTaskManager.init();//定时任务
    }

    /**
     * 有Manifest.permission.READ_PHONE_STATE权限再开启，不然Android6.0+会卡死
     */
    public static void initBlockCanary() {
//        if (true || MyDebugConfig.isDebug()) {
//            BlockCanary.install(context, new AppBlockCanaryContext()).start();
//        }

    }
}
