package cn.broadin.libutils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hui on 2015/11/20.
 */
public class Logger {


    private static final String TAG = "LoggerAPP";
    private static final String TAG_NET = "LoggerNET";
    private static final boolean DETAIL_ENABLE = true;
    private static boolean isDebug = MyDebugConfig.isDebug();

    public static void initDebug() {
        isDebug = MyDebugConfig.isDebug();
    }

//    static {
//        com.orhanobut.logger.Logger.init(TAG);
//    }

    /**
     * 打印程序调用堆栈信息
     */
    public static void trackInfo() {
        if (DETAIL_ENABLE) {
            StackTraceElement[] tracks = Thread.currentThread().getStackTrace();
            Log.i(TAG, "tracks:-----------start---------------");
            for (StackTraceElement track : tracks) {
                StringBuilder buffer = new StringBuilder();
                buffer.append(track.getClassName() + ":" + track.getMethodName());
                buffer.append("(");
                buffer.append(track.getFileName());
                buffer.append(":");
                buffer.append(track.getLineNumber());
                buffer.append(")");
                Log.i(TAG, buffer.toString());
            }
            Log.i(TAG, "tracks:-----------end---------------");
        }
    }

    public static void d(String tag, String msg) {
        if (!isDebug) {
            return;
        }

        Log.d(TAG, tag + "\n" + msg);
    }

    public static void d(String msg) {
        if (!isDebug) {
            return;
        }
        if (msg != null && msg.length() < 100) {
            msg = msg + " " + getPosition();
        } else {
            msg = getPosition() + "\n" + msg;
        }
        Log.d(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.e(TAG, tag + "\n" + msg);
    }

    public static void e(String msg) {
        if (!isDebug) {
            return;
        }

        if (msg != null && msg.length() < 100) {
            msg = msg + " " + getPosition();
        } else {
            msg = getPosition() + "\n" + msg;
        }
        Log.e(TAG, msg);
    }

    public static void printWholeMsg(String msg) {
        if (!isDebug) {
            return;
        }
        d(msg);
//        com.orhanobut.logger.Logger.d(msg);
    }
//    /**
//     * 打印完整的信息
//     */
//    public static void printWholeMsg(String msg) {
//        if (isDebug) {
//            if (msg == null) {
//                return;
//            }
//            int len = msg.length();
//            int maxLogSize = 300;//每次打印字符
//            double count = Math.ceil(len * 1.0 / maxLogSize);//打印次数
//
//            Log.d(TAG, "================START==================");
//            for (int i = 0; i <= count; i++) {
//
//                int start = i * maxLogSize;
//
//                int end = (i + 1) * maxLogSize;
//
//                end = end > len ? len : end;
//
//                Log.d(TAG, String.format("Part%s    %s", i + 1, msg.substring(start, end)));
//
//            }
//            Log.d(TAG, "================END==================");
//        }
//    }

    /**
     * 打印异常信息
     *
     * @param e
     */
    public static void e(Throwable e) {
        e(Log.getStackTraceString(e));
        String msg = e.getMessage();
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Logger.e("e.getMessage=" + msg + " className=" + e.getClass().getSimpleName());
        if (msg.indexOf("TimeoutException") != -1) {
            //超时不上报
            return;
        }
        if (msg.indexOf("UnknownHostException") != -1) {
            //超时不上报
            return;
        }
        //上传报错！！！
//        MobclickAgent.reportError(MyApplication.getContext(), e);
    }

    /**
     * 要排除，不打印的位置
     */
    private static final Set<String> noPrintClass = new HashSet<>();

    /**
     * 获取调用位置
     *
     * @return
     */
    private static String getPosition() {
        if (DETAIL_ENABLE) {
            StringBuilder buffer = new StringBuilder();

            int printTrackIndex = 4;
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[printTrackIndex];

            //要排除，不打印的位置
            if (noPrintClass.isEmpty()) {
                noPrintClass.add("Logger.java");
                noPrintClass.add("BaseRequest.java");
                noPrintClass.add("Api.java");
//                noPrintClass.add("BaseObserver.java");
            }

            int maxSize = Thread.currentThread().getStackTrace().length;
            while (noPrintClass.contains(stackTraceElement.getFileName())
                    && printTrackIndex < maxSize) {
                printTrackIndex++;
                StackTraceElement newElement = Thread.currentThread().getStackTrace()[printTrackIndex];
                if ("OkHttpUtils.java".equals(newElement.getFileName())
                        || "Method.java".equals(newElement.getFileName())) {
//                    trackInfo();
                    break;
                }
                stackTraceElement = newElement;
            }
            buffer.append("(");
            buffer.append(stackTraceElement.getFileName());
            buffer.append(":");
            buffer.append(stackTraceElement.getLineNumber());
            buffer.append(")");
            return buffer.toString();
        }
        return null;

    }

    public static void json(String s) {
        if (!isDebug) {
            return;
        }
//        msg = getPosition() + "\n" + msg;
        Log.d(TAG, s);
//        com.orhanobut.logger.Logger.json(s);
    }

//    /**
//     * 打印可以转换成json字符串的对象
//     *
//     * @param str
//     * @param object
//     */
//    public static void object(String str, Object object) {
//        if (!isDebug) {
//            return;
//        }
//        try {
//            Logger.d(str + JSON.toJSONString(object));
//        } catch (Exception e) {
//        }
//    }

    private static long timeStart;

    public static void time1() {
        timeStart = System.currentTimeMillis();
    }


    public static long time2(String... str) {
        String s = "";
        if (str != null && str.length > 0) {
            s = str[0] + "  ";
        }
        long cosTime = (System.currentTimeMillis() - timeStart);
        Logger.d(s + "Cost Time=" + cosTime);
        return cosTime;
    }

    public static void object(String str, Object object) {
        if (!isDebug) {
            return;
        }
        try {
            Logger.d(str + JSON.toJSONString(object));
        } catch (Exception e) {
        }
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    public static void netLog(String msg) {
        if (!isDebug) {
            return;
        }
        if (msg != null && msg.length() < 100) {
            msg = msg + " " + getPosition();
        } else {
            msg = getPosition() + "\n" + msg;
        }
        Log.d(TAG_NET, msg);
    }

    /**
     * 网络数据
     *
     * @param msg
     */
    public static void netLogErr(String msg) {
        if (!isDebug) {
            return;
        }
        if (msg != null && msg.length() < 100) {
            msg = msg + " " + getPosition();
        } else {
            msg = getPosition() + "\n" + msg;
        }
        Log.e(TAG_NET, msg);
    }
}
