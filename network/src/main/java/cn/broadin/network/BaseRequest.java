package cn.broadin.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.MyDebugConfig;
import cn.broadin.network.cache.RequestCacheManager;
import cn.broadin.network.cache.RequestCacheStrategy;
import okhttp3.Call;
import okhttp3.OkHttpClient;


/**
 * 网络请求基类
 * Created by ChenHui on 2016/6/27.
 */
public class BaseRequest {

    {
        trustCertificate();
    }

    private BaseRequest() {
    }

    private static BaseRequest mInstance = new BaseRequest();

    public static BaseRequest getInstance() {
        return mInstance;
    }

    public void doGet(String url, Map<String, String> args, NetCallback callback) {
        List<NetCallback> callbacks = new ArrayList<>();
        if (callback != null) {
            callbacks.add(callback);
        }
        doGet(url, args, callbacks);
    }

    public void doPost(String url, Map<String, String> args, NetCallback callback) {
        List<NetCallback> callbacks = new ArrayList<>();
        if (callback != null) {
            callbacks.add(callback);
        }
        doPost(url, args, callbacks);
    }

    /**
     * 处理所有的get请求
     *
     * @param url
     * @param args
     * @param callbacks
     */
    public void doGet(final String url, final Map<String, String> args, final List<NetCallback> callbacks) {
        final boolean isPrintLog = (callbacks != null && callbacks.size() == 1 && callbacks.get(0).isPrintLog());
        if (isPrintLog) {
            Logger.object("GET request\nurl=" + url + "\nparams=", args);
        }
        if (args != null && MyDebugConfig.isDebug()) {
            StringBuilder sb = new StringBuilder();
            for (String key : args.keySet()) {
                sb.append("&" + key + "=" + args.get(key));
            }
            if (isPrintLog) {
                Logger.d("完整参数：" + url + sb.toString().replaceFirst("&", "?"));
            }
        }
        GetBuilder builder = OkHttpUtils.get().params(args).url(url);
        try {
            builder.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if (isPrintLog) {
                        Logger.object("response\nurl=" + url + "\nparams=", args);
                        Logger.e("url:" + url);
                        Logger.e(e);
                        if (MyDebugConfig.isDebug()) {
//                            Logger.trackInfo();
                        }
                    }
                    for (NetCallback callback : callbacks) {
                        try {
                            callback.onError(e);
                        } catch (Exception e2) {
                            Logger.e(e2);
                        }
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    response = ("" + response).trim();
                    if (isPrintLog) {
                        Logger.object("response\nurl=" + url + "\nparams=", args);
                        Logger.json(response);
                    }
                    for (NetCallback callback : callbacks) {
                        try {
                            if (callback != null)
                                callback.onResponse(response);
                        } catch (Exception e) {
                            Logger.e(e);
                        }
                    }
                }
            });
        } catch (Exception e) {
            Logger.e(e);
            for (NetCallback callback : callbacks) {
                try {
                    callback.onError(e);
                } catch (Exception e2) {
                    Logger.e(e2);
                }
            }
        }
    }

    /**
     * 处理所有的post请求
     *
     * @param url
     * @param args
     * @param callbacks
     */
    public void doPost(final String url, final Map<String, String> args, final List<NetCallback> callbacks) {
        Logger.object("POST request\nurl=" + url + "\nparams=", args);
        if (args != null && MyDebugConfig.isDebug()) {
            StringBuilder sb = new StringBuilder();
            for (String key : args.keySet()) {
                sb.append("&" + key + "=" + args.get(key));
            }
            Logger.d("参数：" + sb.toString().replaceFirst("&", "?"));
        }
        if (args == null || args.size() <= 0) {
            //当作get请求
            doGet(url, null, callbacks);
        } else {
            PostFormBuilder builder = OkHttpUtils.post().url(url);
            builder.params(args);
            try {
                builder.build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.object("response\nurl=" + url + "\nparams=", args);
                        Logger.e(e);
                        if (callbacks != null)
                            for (NetCallback callback : callbacks) {
                                try {
                                    callback.onError(e);
                                } catch (Exception e2) {
                                    Logger.e(e2);
                                }
                            }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = ("" + response).trim();
                        Logger.object("response\nurl=" + url + "\nparams=", args);
                        Logger.json(response);
                        if (callbacks != null)
                            for (NetCallback callback : callbacks) {
                                try {
                                    if (callback != null)
                                        callback.onResponse(response);
                                } catch (Exception e) {
                                    Logger.e(e);
                                }
                            }
                    }
                });
            } catch (Exception e) {
                Logger.e(e);
            }
        }
    }

    /**
     * 用来做网络测试
     *
     * @param callback
     */
    public void doNetGetTest(final NetCallback callback) {
        final String url = "http://baidu.com";
        long connTimeout = 3000;
        GetBuilder builder = OkHttpUtils.get().url(url);
        builder.build().connTimeOut(connTimeout).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                if (callback != null) {
                    callback.onResponse(response);
                }
            }
        });
    }

    /**
     * 信任所有的证书
     */
    public void trustCertificate() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public void download(final String url, String dir, String fileName, final NetFileCallBack callBack) {
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .readTimeOut(30 * 60 * 1000)
                .writeTimeOut(30 * 60 * 1000)
                .execute(new FileCallBack(dir, fileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.d("下载失败（url=" + url + "）" + e.getMessage());
                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Logger.d("下载完成（url=" + url + "）" + response.getAbsolutePath());
                        callBack.onResponse(response);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        callBack.inProgress(progress, total);
                    }
                });
    }


    /**
     * @param url
     * @param params
     * @param callback
     * @param cacheStrategy 传递一些缓存策略
     */
    public void doGet(final String url, final Map<String, String> params, final NetCallback callback, final RequestCacheStrategy cacheStrategy) {
        //读写文件应该在子线程
        if (cacheStrategy == null
                || !cacheStrategy.isCacheAble()
                || TextUtils.isEmpty(cacheStrategy.getCacheFileName())) {
            BaseRequest.getInstance().doGet(url, params, callback);
        } else {
            new AsyncTask<Void, Void, String>() {//在子线程中保存
                String fileName;
                boolean needRefresh = true;

                @Override
                protected String doInBackground(Void... params) {
                    fileName = cacheStrategy.getRealFileName();
                    //先从缓存中读取
                    String responseCache = RequestCacheManager.doRead(fileName);
                    if (!TextUtils.isEmpty(responseCache)) {
                        //一分钟内的记录不刷新
                        needRefresh = RequestCacheManager.isNeedRefresh(fileName, cacheStrategy.getCacheReloadMillions());
                        if (!needRefresh) {
                            Logger.d("url=" + url);
                            Logger.object("params=", params);
                        }
                    }
                    return responseCache;
                }

                @Override
                protected void onPostExecute(String responseCache) {
                    boolean hasCacheContent = false;//是否读取了缓存内容
                    try {
                        if (!TextUtils.isEmpty(responseCache)) {
                            callback.onResponse(responseCache);
                            hasCacheContent = true;
                        }
                    } catch (Exception e) {
                        Logger.e(e);
                        hasCacheContent = false;
                    }
                    if (!hasCacheContent || needRefresh) {//没有缓存内容或者需要刷新
                        doGetFromNet(url, params, callback, cacheStrategy, fileName, responseCache, hasCacheContent);
                    }
                }
            }.execute();
        }
    }


    /**
     * 从网络获取可缓存的数据
     *
     * @param url
     * @param params
     * @param callback
     * @param cacheStrategy
     * @param fileName
     * @param responseCache   现有的缓存的response
     * @param hasCacheContent
     */
    private void doGetFromNet(String url, Map<String, String> params, final NetCallback callback, final RequestCacheStrategy cacheStrategy, final String fileName, final String responseCache, boolean hasCacheContent) {
        final boolean hasReadCacheContent = hasCacheContent;
        NetCallback callbackSave = new NetCallback() {
            @Override
            public void onResponse(String response) {
                if (!hasReadCacheContent) {
                    callback.onResponse(response);
                }
                if (!TextUtils.equals(response, responseCache)) {//不一样才保存
                    if (hasReadCacheContent) {
                        cacheStrategy.notifyCacheOutOfDate(response);//通知缓存超时
                    }
                    boolean needSave = cacheStrategy.getChecker() ==
                            null ? true : cacheStrategy.getChecker().responseOk(response);
                    if (needSave) {
                        new AsyncTask<String, Void, Void>() {//在子线程中保存
                            @Override
                            protected Void doInBackground(String... params) {
                                String response = params[0];
                                RequestCacheManager.doSave(fileName, response);
                                return null;
                            }
                        }.execute(response);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!hasReadCacheContent) {
                    callback.onError(e);
                }
            }
        };
        BaseRequest.getInstance().doGet(url, params, callbackSave);
    }
}
