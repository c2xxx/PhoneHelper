package com.chen.base.presenter.impl;

import android.support.annotation.NonNull;

import com.chen.base.activity.BaseActivity;
import com.chen.base.bean.ContentBean;
import com.chen.base.presenter.IBasePresenter;
import com.chen.base.ui.IBaseView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenHui on 2017/9/12.
 */

public class BasePresenter<V extends IBaseView, A extends BaseActivity> implements IBasePresenter {
    public WeakReference<V> mWefView;

    public BasePresenter(V view) {
        mWefView = new WeakReference<>(view);
    }

    public V getView() {
        return (V) mWefView.get();
    }

    public boolean isAttachView() {
        return getView() != null;
    }


    public void onCreate() {

    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {

    }


    /**
     * @return 测试数据
     */
    @NonNull
    public static List<ContentBean> testData(int... count) {
        return testData("标题", count);
    }

    /**
     * @return 测试数据
     */
    @NonNull
    public static List<ContentBean> testData(String title, int... count) {
        int count1 = 10;
        if (count != null && count.length > 0) {
            count1 = count[0];
        }
        count1 = Math.max(0, count1);
        List<ContentBean> titles = new ArrayList<>();
        String[] imgList = {
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610643967&di=7f6be55601a4043d486f347f30620d01&imgtype=0&src=http%3A%2F%2Fwww.1tong.com%2Fuploads%2Fwallpaper%2Fstars%2F41-4-1366x768.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610754346&di=8759af06a75f3e8a63204f74f2d70596&imgtype=0&src=http%3A%2F%2Fdesk.fd.zol-img.com.cn%2Ft_s960x600c5%2Fg5%2FM00%2F00%2F03%2FChMkJlebIV-IOKuEAIHHMRI6N24AAT9_QAqtGEAgcdJ624.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610644396&di=3908f8589b824371a64a14195f30274f&imgtype=0&src=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F150827%2F140-150RG50Z7.jpg",
                "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=60aeee5da74bd11310c0bf7132c6ce7a/72f082025aafa40fe3c0c4f3a164034f78f0199d.jpg",
                "http://pic35.nipic.com/20131112/2531170_204256005000_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610644401&di=3d2f8e1f0e63e477d1fe5d9da9986781&imgtype=0&src=http%3A%2F%2Fwww.1tong.com%2Fuploads%2Fwallpaper%2Fstars%2F41-3-1600x900.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610644397&di=347da78be51079c3859471420fe6dbf9&imgtype=0&src=http%3A%2F%2Fd.5857.com%2Fhunanxiaohua_141128%2F004.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490610644397&di=22d8d92466e352f4188fa22364f84adb&imgtype=0&src=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F150325%2F139-150325144108.jpg",
        };
        for (int i = 0; i < count1; i++) {
            ContentBean item = new ContentBean();
            item.setText(title + i);
            item.setSubText("副标题" + i);
            item.setImgUrl(imgList[i % imgList.length]);
            titles.add(item);
        }
        return titles;
    }

//
//    protected void onLoadDataStart() {
//        if (getView() == null) {
//            return;
//        }
//        getView().onLoadDataStart();
//    }
//
//    protected void onLoadDataFinish() {
//        if (getView() == null) {
//            return;
//        }
//        getView().onLoadDataFinish();
//    }
}
