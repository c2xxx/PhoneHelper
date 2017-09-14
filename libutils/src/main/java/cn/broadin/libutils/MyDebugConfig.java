package cn.broadin.libutils;

import android.content.pm.ApplicationInfo;

import java.io.File;
import java.io.IOException;

/**
 * 控制（Release版本）程序是否打印LOG
 * Created by ChenHui on 2017/5/25.
 */

public class MyDebugConfig {
    private static boolean isOpenDebug = false;
    private static String _isDebug = FileHelper.getInstance().getCacheDir() + File.separator + "_isDebug";

    static {
        File f = new File(_isDebug);
        isOpenDebug = f.exists();
    }

    public static boolean isRelease() {
        return !getIsDebug();
    }

    public static boolean isDebug() {
        return getIsDebug() || isOpenDebug;
    }

    private static boolean getIsDebug() {
        try {
            ApplicationInfo info = AppContext.getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void openDebug() {
        isOpenDebug = true;
        Logger.initDebug();
        Logger.d("openDebug");
//        MyApplication.initDebug();

        File f = new File(_isDebug);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                Logger.e(e);
            }
        }
    }

    public static void closeDebug() {
        Logger.d("closeDebug");
        isOpenDebug = false;
        Logger.initDebug();
//        MyApplication.initDebug();
        File f = new File(_isDebug);
        f.delete();
    }
}
