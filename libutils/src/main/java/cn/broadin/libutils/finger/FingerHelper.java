package cn.broadin.libutils.finger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.ToastUtil;

/**
 * 指纹识别,只支持了>=23的，也就是android6.0
 * 参考文献：http://blog.csdn.net/createchance/article/details/51991764
 * Created by ChenHui on 2017/9/12.
 */

public class FingerHelper {
    private FingerprintManager manager;
    Activity context;
    private CancellationSignal cancel;

    public FingerHelper(Activity context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        }
    }

    /**
     * 是否支持指纹识别
     *
     * @return
     */
    public boolean isSupport() {
        if (manager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Logger.d("没有权限");
                return false;
            }
            return manager.isHardwareDetected();
        } else {
            return false;
        }
    }

    public void cancel() {
        if (cancel != null) {
            cancel.cancel();
            cancel = null;
        }

        if (uiHelper != null) {
            uiHelper.dismiss();
            uiHelper = null;
        }
    }

    public void check() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            doFailed(-1, "没有权限");
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            doFailed(-1, "版本太低");
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (!manager.hasEnrolledFingerprints()) {
            doFailed(-1, "没有录入指纹");
            return;
        }
        FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                cancel();
                Logger.d("errorCode=" + errorCode + " " + errString);
                doFailed(errorCode, "" + errString);
            }

            /**
             * 该方法是出现了可以回复的异常才会调用的。
             * 什么是可以恢复的异常呢？
             * 一个常见的例子就是：手指移动太快，当我们把手指放到传感器上的时候，
             * 如果我们很快地将手指移走的话，那么指纹传感器可能只采集了部分的信息，
             * 因此认证会失败。但是这个错误是可以恢复的，因此只要提示用户再次按下指纹，
             * 并且不要太快移走就可以解决
             * @param helpCode
             * @param helpString
             */
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                showMessage(helpString, true);
                Logger.d("onAuthenticationHelp");
            }

            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                showMessage("验证成功", false);
            }

            public void onAuthenticationFailed() {
                showMessage("验证失败，请重试", false);
            }

        };
        cancel = new CancellationSignal();
        cancel.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Logger.d("取消");
            }
        });
        showMessage("请验证已有指纹", false);
        manager.authenticate(null, cancel, 0, callback, null);
    }

    FingerUIHelper uiHelper;

    private void showMessage(CharSequence msg, boolean isErr) {
        Logger.d("状态：" + msg);
        if ("验证成功".equals(msg)) {
            cancel();
            doSuccess();
            return;
        }
        if (uiHelper == null) {
            uiHelper = new FingerUIHelper() {
                @Override
                protected void onCanceled(String reason) {
                    ToastUtil.show(reason);
                    cancel();
                }
            };
        }
        uiHelper.showMessage(context, msg, isErr);
    }

    protected void doSuccess() {
        ToastUtil.show("验证成功");
    }

    protected void doFailed(int errorCode, String msg) {
//        errorCode == 7 次数过多
//        errorCode == 5 取消
        if (errorCode == 5) {//主动取消
            return;
        }
        if (errorCode == -1 || errorCode == 7) {
            ToastUtil.show(msg);
        } else {
            ToastUtil.show(msg + "(code:" + errorCode + ")");
        }
    }
}
