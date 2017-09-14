package com.chen.phonehelper.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class RunAlways extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
//                    Logger.d("运行着呢");
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
        Logger.d("unRegister");
    }

    private void register() {
//        IntentFilter mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction(cn.broadin.musicplayer.util.Constants.BROADCAST_ACTION_PLAY_SONG_SUCCESS);
//        mIntentFilter.addAction(cn.broadin.musicplayer.util.Constants.BROADCAST_ACTION_CURRENT_PLAY_LIST_CHANGED);
//        receiver = new MyReceiver();
//        LocalBroadcastManager.getInstance(MyApplication.getContext()).registerReceiver(receiver, mIntentFilter);
    }

    private void unRegister() {
//        if (receiver != null) {
//            LocalBroadcastManager.getInstance(MyApplication.getContext()).unregisterReceiver(receiver);
//            receiver = null;
//        }
    }

    MyReceiver receiver = null;

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = "" + intent.getAction();

            switch (action) {
//                //播放新的歌单
//                case cn.broadin.musicplayer.util.Constants.BROADCAST_ACTION_CURRENT_PLAY_LIST_CHANGED:
//                    break;
//                //播放了新的歌曲
//                case cn.broadin.musicplayer.util.Constants.BROADCAST_ACTION_PLAY_SONG_SUCCESS:
//                    Logger.d("播放了新的歌曲!");
//                    savePlayHistory();
//                    PlayColumnManager.getInstance().onPlayNewTrack();
//                    break;
            }
        }

        private void savePlayHistory() {
//            CommonTrackInfo song = PlayListManager.getInstance().getCurrentTrack();
//            MyPlayHistoryManager.getInstance().save(song);
        }
    }

    public static void init() {
//        Intent intent = new Intent(MyApplication.getContext(), PlayStatusChangerService.class);
//        MyApplication.getContext().startService(intent);
    }
}
