package cn.broadin.network;


/**
 * Created by chenhui on 2016/4/15.
 * 网络层的回调
 */
public abstract class NetCallback {

    public void onError(Throwable e) {

    }

    public abstract void onResponse(String response);

    /**
     * 缓存超时的回调
     * 如果从缓存读取了过期内容时会触发
     *
     * @param newResponse
     */
    public void notifyCacheOutOfDate(String newResponse) {

    }

    /**
     * 是否打印LOG
     *
     * @return
     */
    public boolean isPrintLog() {
        return true;
    }
}
