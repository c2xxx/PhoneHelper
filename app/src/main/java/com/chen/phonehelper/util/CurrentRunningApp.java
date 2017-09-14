package com.chen.phonehelper.util;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.chen.phonehelper.MyApplication;

import java.util.List;

import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class CurrentRunningApp {
    Context context;

    public CurrentRunningApp() {
        context = MyApplication.getContext();
    }

    public String read() {
        String topApp = getTopActivity();
//        Logger.d("top running app is : " + topActivity);
        Logger.d("当前APP:" + topApp);
        return topApp;
    }

    /**
     * Android 5.0 以上
     *
     * @return
     */
    private String getTopActivity() {
        String topActivity = "unknown";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
//                Logger.d("Running app number in last 60 seconds : " + stats.size());
                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
//                        Logger.d(stats.get(i).getPackageName());
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }

                    topActivity = stats.get(j).getPackageName();
                }
            }else {
                Logger.d("????");
            }
        }
        return topActivity;
    }

    //检测用户是否对本app开启了“Apps with usage access”权限
    public boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public void doShowQuan() {
        //若用户未开启权限，则引导用户开启“Apps with usage access”权限
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
