package cn.broadin.libutils.finger;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import cn.broadin.libutils.Logger;
import cn.broadin.libutils.R;

/**
 * Created by ChenHui on 2017/9/13.
 */

public class FingerUIHelper {
    AlertDialog dialog;

    public void showMessage(Context context, CharSequence msg, boolean isErr) {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(msg);
            ImageView iv = new ImageView(context);
            iv.setImageResource(R.drawable.zhiwen);
            builder.setView(iv);
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onCanceled("取消验证");
                }
            });
            builder.setNegativeButton("密码登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onCanceled("密码登录");
                    Logger.d("密码登录");
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    onCanceled("取消验证");
                }
            });
            dialog = builder.create();
        } else {
            dialog.setTitle(msg);
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    protected void onCanceled(String reason) {

    }
}
