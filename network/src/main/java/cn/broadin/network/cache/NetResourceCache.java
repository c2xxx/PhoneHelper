package cn.broadin.network.cache;

import java.io.File;

import cn.broadin.libutils.FileHelper;
import cn.broadin.libutils.Logger;
import cn.broadin.network.BaseRequest;
import cn.broadin.network.NetFileCallBack;

/**
 * 缓存网络资源（下载，而不是网络请求）
 * Created by ChenHui on 2017/1/19.
 */

public class NetResourceCache {
    /**
     * 返回本地地址,不存在则下载
     *
     * @param url
     * @return
     */
    public static String cacheResource(String url,final NetFileCallBack fileCallBack) {
        String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_DOWNLOAD);
        String targetName = FileHelper.getInstance().getSimpleFileName(url);
        String tempName = targetName + ".download";
        final String targetFileName = pathName + targetName;
        File targetFile = new File(targetFileName);
        if (targetFile.exists()) {
            if (fileCallBack != null) {
                fileCallBack.onResponse(targetFile);
                targetFile.setLastModified(System.currentTimeMillis());
            }
        } else if (!targetFile.exists()) {//目标文件不存在
            NetFileCallBack callback = new NetFileCallBack() {
                @Override
                public void onResponse(File file) {
                    File newFile = new File(targetFileName);
                    if (file.renameTo(newFile)) {
                        file = newFile;
                    }
                    Logger.d("download success" + file.getAbsolutePath());
                    if (fileCallBack != null) {
                        fileCallBack.onResponse(file);
                    }
                }

                @Override
                public void onError(Exception e) {
                    super.onError(e);
                    if (fileCallBack != null) {
                        fileCallBack.onError(e);
                    }
                    Logger.d("download onError" + targetFileName + "(" + e.getMessage() + ")");
                }
            };
            BaseRequest.getInstance().download(url, pathName, tempName, callback);
        }
        return targetName;
    }


    /**
     * 删除长时间没用的图片
     */
    public static void deleteOutTimeCacheResource() {
        String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_DOWNLOAD);
        FileHelper.getInstance().clearFileOutTime(pathName, 50);
    }


//    /**
//     * 清理一个月以上的缓存
//     */
//    public static void clearCacheOneMonthAgo(String pathName) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Logger.d("清理缓存的数据");
//                //30天前的时间：天数*小时*分钟*秒钟*毫秒,注意要用long类型，int溢出
//                long timeOneMonthAgo = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L;
////                String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_CACHE);
//                clearCache(timeOneMonthAgo, new File(pathName));
//            }
//
//            private void clearCache(long timeOneMonthAgo, File dir) {
//                if (dir.isDirectory()) {
//                    File[] files = dir.listFiles();
//                    for (File file : files) {
////                        Logger.d("遍历了" + file);
//                        if (file.isDirectory()) {
//                            clearCache(timeOneMonthAgo, file);
//                        } else if (file.lastModified() < timeOneMonthAgo) {
//                            file.delete();
//                        }
//                    }
//                }
//            }
//        }).start();
//
//    }
}
