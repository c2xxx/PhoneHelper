package cn.broadin.libutils.canary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.MyDebugConfig;
import io.reactivex.functions.Consumer;

/**
 * Created by ChenHui on 2017/8/21.
 */

public class BlockCanaryInit {
    public void init(Activity activity) {
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplicationContext();
        //权限判断
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Logger.d("否有权限1：" + aBoolean);
                        if (true == aBoolean) {
                            if (true || MyDebugConfig.isDebug()) {
                                BlockCanary.install(context, new AppBlockCanaryContext()).start();
                            }
                        }
                    }
                });
    }
}
