package com.chen.base.presenter;

/**
 * Created by ChenHui on 2017/6/27.
 */

public interface IBasePresenter {
    boolean isAttachView();

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
