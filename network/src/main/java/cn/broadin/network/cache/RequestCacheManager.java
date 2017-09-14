package cn.broadin.network.cache;

import android.text.TextUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;

import cn.broadin.libutils.FileHelper;
import cn.broadin.libutils.Logger;
import cn.broadin.libutils.tools.MD5Util;

/**
 * 缓存请求数据
 * Created by ChenHui on 2016/6/2.
 */
public class RequestCacheManager {

    /**
     * 生成描述，每个访问返回一个对应的描述
     *
     * @param url
     * @param params
     * @return
     */
    public static String getDefaultUrlDescribeMD5(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                String value = params.get(key);
                sb.append(String.format("key=%s;value=%s", key, value));
            }
        }
        return MD5Util.MD5(sb.toString());
    }


    /**
     * 将结果保存到本地
     *
     * @param requestContent
     */
    public static void doSave(String fileName, String requestContent) {
        if (TextUtils.isEmpty(requestContent)) {
            return;
        }
        FileHelper.getInstance().doSave(FileHelper.FILE_TYPE_REQUEST_CACHE, fileName, requestContent);
    }

    /**
     * 从缓存中读取结果
     *
     * @param fileName
     * @return
     */
    public static String doRead(String fileName) {
        FileHelper fh = FileHelper.getInstance();
        return fh.doRead(
                fh.getFileAbsulteName(
                        FileHelper.FILE_TYPE_REQUEST_CACHE, fileName));
    }

    /**
     * 在一天之内修改过
     *
     * @param fileName          文件名称
     * @param refreshTimeMillis 是否在指定时间内刷新过，比如一天内
     * @return
     */
    public static boolean isNeedRefresh(String fileName, long refreshTimeMillis) {
        long timeAgo = System.currentTimeMillis() - refreshTimeMillis;
        String absulteName = FileHelper.getInstance().getFileAbsulteName(FileHelper.FILE_TYPE_REQUEST_CACHE, fileName);
        File file = new File(absulteName);
        if (file.exists() && file.lastModified() > timeAgo) {
            return false;
        }
        return true;
    }

    /**
     * 清理一个月以上的缓存
     */
    public static void clearCacheOneMonthAgo() {
        String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_CACHE);
        FileHelper.getInstance().clearFileOutTime(pathName, 30);
    }

    /**
     * 清理子文件夹下的页面缓存
     */
    public static void clearCacheByDir(String cacheDirName) {
        if (TextUtils.isEmpty(cacheDirName)) {
            return;
        }
        String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_CACHE);
        File dir = new File(pathName + File.separator + cacheDirName);
        FileHelper.getInstance().deleteAllFilesOfDir(dir);
    }

    /**
     * 清理全部缓存
     */
    public static void clearCacheAll() {
        Logger.d("清理全部缓存的数据");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_CACHE);
                File dir = new File(pathName);
                FileHelper.getInstance().deleteAllFilesOfDir(dir);
            }
        }).start();
    }
}
