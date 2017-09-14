package cn.broadin.libutils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cn.broadin.libutils.tools.MD5Util;

import static android.R.attr.id;
import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by ChenHui on 2016/5/27.
 */
public class FileHelper {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * 请求缓存
     */
    public static final String FILE_TYPE_REQUEST_CACHE = "CACHE";
    /**
     * 歌单
     */
    public static final String FILE_TYPE_DATA = "DATA";
    /**
     * 歌单
     */
    public static final String FILE_TYPE_REQUEST_DOWNLOAD = "download";

    private String packageName;
    private String cacheDir;//缓存路径
    private String cacheTempDir;//临时文件缓存路径

    private ReadWriteLock rwl = new ReentrantReadWriteLock();//读写锁，可以同时读，写操作互斥

    private static FileHelper instance = null;

    private FileHelper() {
        init();
    }

    public static FileHelper getInstance() {
        if (instance == null) {
            synchronized (FileHelper.class) {
                if (instance == null) {
                    instance = new FileHelper();
                }
            }
        }
        return instance;
    }

    public void init() {
        try {
            packageName = AppContext.getContext().getPackageName();
            String path = Environment.getExternalStorageDirectory()
                    + File.separator + "Android"
                    + File.separator + "data"
                    + File.separator + packageName;
            new File(path + File.separator + "files").mkdirs();
            new File(path + File.separator + "cache").mkdirs();

            try {
                cacheDir = AppContext.getContext().getExternalFilesDir("myCache").getAbsolutePath();// 获取到缓存的目录地址
                cacheTempDir = AppContext.getContext().getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                cacheDir = AppContext.getContext().getFilesDir().getAbsolutePath();// 获取到缓存的目录地址
                cacheTempDir = AppContext.getContext().getCacheDir().getAbsolutePath();
            }
        } catch (Exception e) {
            packageName = AppContext.getContext().getPackageName();
            String strPath = null;
            final String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File sdFile = Environment.getExternalStorageDirectory();
                strPath = sdFile.getAbsolutePath();
                createDir(strPath);
            } else {
                String strCacheDir = AppContext.getContext().getFilesDir().getAbsolutePath();
                strPath = strCacheDir;
            }
            File file = new File(strPath);
            cacheDir = file.getAbsolutePath();
            cacheTempDir = cacheDir + File.separator + "temp";
        }
        Logger.d("缓存文件目录：" + cacheDir);
        Logger.d("临时文件目录：" + cacheTempDir);
    }

    public String getCacheTempDir() {
        return cacheTempDir;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system.
     *
     * @param context  Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否加载了SD卡
     *
     * @return
     */
    public boolean isSDcardMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建目录
     *
     * @param path
     */
    public void createDir(String path) {
        File dir = new File(path);
        if (dir.exists() && !dir.isDirectory()) {
            dir.delete();
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 保存文件
     *
     * @param type           类型
     * @param simpleFileName 文件名
     * @param content        内容
     */
    public void doSave(String type, String simpleFileName, String content) {
        doSave(getFileAbsulteName(type, simpleFileName), content);
    }

    /**
     * 保存内容
     *
     * @param absulteFileName 绝对路径名
     * @param content         内容
     */
    public void doSave(String absulteFileName, String content) {
        //重新写入文件
        try {
//            Logger.d("doSave " + absulteFileName);
            File file = new File(absulteFileName);
            //创建父目录
            createDir(file.getParent());
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!TextUtils.isEmpty(content)) {
                rwl.writeLock().lock();
                FileWriter writer = new FileWriter(file);
                writer.write(content);
                writer.close();
            }
        } catch (Exception e) {
            Logger.e(e);
        } finally {
            try {
                rwl.writeLock().unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取内容
     *
     * @param absulteFileName 绝对路径名
     */
    public String doRead(String absulteFileName) {
        return doRead(new File(absulteFileName));
    }

    /**
     * 读取内容
     *
     * @param file
     * @return
     */
    public String doRead(File file) {
        if (file != null && file.exists()) {
//            Logger.d("doRead " + file.getAbsolutePath());
            StringBuilder sb = new StringBuilder();
            try {
                if (file.isFile() && file.exists()) { //判断文件是否存在
                    rwl.readLock().lock();
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        sb.append(lineTxt + "\n");
                    }
                    read.close();
                    if (MyDebugConfig.isDebug()) {
                        Date data = new Date(file.lastModified());
                        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                        Logger.d("读取" + file + "内容,修改时间：" + sdf.format(data));
                    }
                } else {
                    Logger.e("找不到指定的文件 " + file.getName());
                }

            } catch (Exception e) {
                Logger.e("Thread " + Thread.currentThread().getName());
                Logger.e(e);
                e.printStackTrace();
            } finally {
                try {
                    rwl.readLock().unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
        return null;
    }


    /**
     * 根据文件名获取一个完整的文件路径
     *
     * @param type
     * @param simpleFileName
     * @return
     */
    public String getFileAbsulteName(String type, String simpleFileName) {
        return getPathByType(type) + simpleFileName;
    }

    /**
     * 根据文件名获取一个完整的文件路径
     *
     * @param type
     * @return
     */
    public String getPathByType(String type) {
        return cacheDir + File.separator + type + File.separator;
    }

    /**
     * 删除指定文件夹及其子文件夹内容
     *
     * @param path
     */
    public void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }


    /**
     * 读取Assert下的文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        if (null == context || TextUtils.isEmpty(fileName)) {
            return null;
        }

        AssetManager assetManager = context.getAssets();
        InputStream input = null;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            input = assetManager.open(fileName);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
        } catch (Exception e) {
        } finally {
            try {
                output.close();
            } catch (Exception e) {
            }
            try {
                input.close();
            } catch (Exception e) {
            }
        }
        return output.toString();
    }

    /**
     * 清理超时缓存
     *
     * @param pathName 路径
     * @param day      超时天数
     */
    public void clearFileOutTime(final String pathName, final int day) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.d("清理缓存的数据");
                //30天前的时间：天数*小时*分钟*秒钟*毫秒,注意要用long类型，int溢出
                long timeOneMonthAgo = System.currentTimeMillis() - day * 24 * 60 * 60 * 1000L;
//                String pathName = FileHelper.getInstance().getPathByType(FileHelper.FILE_TYPE_REQUEST_CACHE);
                clearCache(timeOneMonthAgo, new File(pathName));
            }

            private void clearCache(long timeOneMonthAgo, File dir) {
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
//                        Logger.d("遍历了" + file);
                        if (file.isDirectory()) {
                            clearCache(timeOneMonthAgo, file);
                        } else if (file.lastModified() < timeOneMonthAgo) {
                            file.delete();
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 获取本地文件名,带后缀
     *
     * @param url
     * @return
     */
    public String getSimpleFileName(String url) {
        return MD5Util.MD5(url) + "." + FileHelper.getInstance().getExtName(url);
    }


    /**
     * 获取后缀名，没有则返回空：“”
     *
     * @param s
     * @return
     */
    public String getExtName(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        int wenHao = s.lastIndexOf("?");
        if (wenHao != -1) {
            s = s.substring(0, wenHao);
        }
        int i = s.lastIndexOf(".");
        int leg = s.length();
        if (id > -1 && (i + 1) != leg) {
            return s.substring(i + 1, s.length());
        }
        return "";
    }
}
