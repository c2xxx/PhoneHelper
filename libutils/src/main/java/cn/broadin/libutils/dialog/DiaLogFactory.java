package cn.broadin.libutils.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cn.broadin.libutils.R;
import cn.broadin.libutils.view.LevelImageView;


/**
 * Created by ChenHui on 2016/4/21.
 */
public class DiaLogFactory {

    private static Dialog dialog;


    public static void showLoadingDialog(Context context) {
        if (null != dialog) {
            dismissDialog();
        }
        dialog = new Dialog(context, R.style.CustomDialog);
        //将自定义的布局设置给AlertDialog
        View view = View.inflate(context, R.layout.widget_loading, null);
        dialog.setContentView(view);

        LevelImageView imageView = (LevelImageView) view.findViewById(R.id.loading_iv);

        //新建动画.属性值从1-10的变化
        ObjectAnimator headerAnimator = ObjectAnimator.ofInt(imageView, "imageLevel", 0, 24);
        //设置动画的播放数量为一直播放.
        headerAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        //设置一个速度加速器.让动画看起来可以更贴近现实效果.
        headerAnimator.setInterpolator(new LinearInterpolator());
        headerAnimator.setRepeatMode(ObjectAnimator.RESTART);
        headerAnimator.setDuration(1200);
        headerAnimator.start();
        imageView.setTag(headerAnimator);
        dialog.show();
    }

    public static void dismissDialog() {
        if (null != dialog) {
            dialog.dismiss();
            try {
                LevelImageView imageView = (LevelImageView) dialog.findViewById(R.id.loading_iv);
                if (imageView != null) {
                    ObjectAnimator headerAnimator = (ObjectAnimator) imageView.getTag();
                    if (headerAnimator != null) {
                        headerAnimator.cancel();
                        imageView.setTag(null);
                    }
                }
            } catch (Exception e) {
            }
        }
        dialog = null;
    }
}
