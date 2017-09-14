package cn.broadin.network;

import java.io.File;

/**
 * Created by Administrator on 2016/9/28.
 */
public abstract class NetFileCallBack {
    public void onError(Exception e) {

    }

    public abstract void onResponse(File file);

    public void inProgress(double progress, long total) {
//        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0000");
//        Logger.d("下载进度：total=" + total + " progress=" + progress * total + " progress=" + df.format(progress));
    }
}
