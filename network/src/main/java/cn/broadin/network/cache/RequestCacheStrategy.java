package cn.broadin.network.cache;

import android.text.TextUtils;

import java.io.File;
import java.util.Map;

import cn.broadin.network.NetCallback;


/**
 * 缓存策略
 * Created by ChenHui on 2016/6/29.
 */
public class RequestCacheStrategy {
    private boolean cacheAble = true;//是否可以缓存
    private String cacheFileName;//完整路径+参数的MD5，保存到本地的文件名，不能包含时间戳，变动的token等不断变化的信息
    private String cacheDirName;//同cacheFileName，只是不包含页码信息，使得同一url不同页面的内容在同一文件夹下
    private IResponseChecker checker;//检查内容是不是错误数据，错误数据不保存,可以为空
    private NetCallback netCallback;//回调
    private long cacheReloadMillions = 60 * 1000;//缓存超时时间，大于这个时间的才会再次加载

    /**
     * 页码保存在路径中的接口
     *
     * @param urlWithPage  真实的url
     * @param urlFirstPage 第一页的url
     * @param params       参数
     * @return
     */
    public static RequestCacheStrategy creater(String urlWithPage, String urlFirstPage, Map<String, String> params) {
        RequestCacheStrategy cacheStrategy = new RequestCacheStrategy();
        cacheStrategy.setCacheFileName(RequestCacheManager.getDefaultUrlDescribeMD5(urlWithPage, params));
        cacheStrategy.setCacheDirName(RequestCacheManager.getDefaultUrlDescribeMD5(urlFirstPage, params));
        return cacheStrategy;
    }


    /**
     * 页码不保存在路径中的，博广接口
     *
     * @param url
     * @param params 参数
     * @return
     */
    public static RequestCacheStrategy createrBoGuang(String url, Map<String, String> params) {
        RequestCacheStrategy cacheStrategy = new RequestCacheStrategy();
        cacheStrategy.setCacheFileName(RequestCacheManager.getDefaultUrlDescribeMD5(url, params));
        cacheStrategy.setCacheDirName(RequestCacheManager.getDefaultUrlDescribeMD5(url, null));
        return cacheStrategy;
    }

    public boolean isCacheAble() {
        return cacheAble;
    }

    public void setCacheAble(boolean cacheAble) {
        this.cacheAble = cacheAble;
    }

    public String getCacheFileName() {
        return cacheFileName;
    }

    public void setCacheFileName(String cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public String getCacheDirName() {
        return cacheDirName;
    }

    public void setCacheDirName(String cacheDirName) {
        this.cacheDirName = cacheDirName;
    }

    public IResponseChecker getChecker() {
        return checker;
    }

    public void setChecker(IResponseChecker checker) {
        this.checker = checker;
    }

    public NetCallback getNetCallback() {
        return netCallback;
    }

    public void setNetCallback(NetCallback netCallback) {
        this.netCallback = netCallback;
    }

    public long getCacheReloadMillions() {
        return cacheReloadMillions;
    }

    public void setCacheReloadMillions(long cacheReloadMillions) {
        this.cacheReloadMillions = cacheReloadMillions;
    }

    /**
     * @return 获取存储相对路径
     */
    public String getRealFileName() {
        if (TextUtils.isEmpty(cacheDirName)) {
            return cacheFileName;
        } else {
            return cacheDirName + File.separator + cacheFileName;
        }
    }

    //通知缓存超时
    public void notifyCacheOutOfDate(String newResponse) {
        if (TextUtils.isEmpty(cacheDirName) || TextUtils.equals(cacheDirName, cacheFileName)) {//是第一页或者不分页才触发缓存过期通知
            if (netCallback != null) {
                netCallback.notifyCacheOutOfDate(newResponse);
            }
        }
        if (TextUtils.isEmpty(cacheDirName)) {
            return;
        }
        RequestCacheManager.clearCacheByDir(cacheDirName);
    }
}
