package cn.broadin.libutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;


/**
 * Created by ChenHui on 2016/10/20.
 */

public class GlideImage {
    public static void show(Context context, String url, ImageView imageView) {
        if (contextErr(context)) return;
        com.bumptech.glide.Glide.with(context)
                .load(getCorrectUrl(url))
                .asBitmap()
                .placeholder(R.drawable.le_default_poster)
                .error(R.drawable.le_default_poster)
                .into(imageView);
    }

    /**
     * context无效的情况
     *
     * @param context
     * @return
     */
    private static boolean contextErr(Context context) {
        return Utils.checkContextError(context);
    }

    /**
     * 显示资源图片
     *
     * @param context
     * @param resImg
     * @param imageView
     */
    public static void show(Context context, int resImg, ImageView imageView) {
        if (contextErr(context)) return;
        com.bumptech.glide.Glide.with(context)
                .load(resImg)
                .asBitmap()
                .dontAnimate()
                .into(imageView);
    }

    public static void show(Context context, String url, ImageView imageView, int defaultImg) {
        if (contextErr(context)) return;
        com.bumptech.glide.Glide.with(context)
                .load(getCorrectUrl(url))
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(imageView);
    }


    public static String getCorrectUrl(String url) {
        return Uri.decode(url);
    }

    public static void download(Context context, String url, BitmapTarget target) {
        if (contextErr(context)) return;
        com.bumptech.glide.Glide.with(context)
                .load(getCorrectUrl(url))
                .asBitmap()
                .into(target);
    }

    public abstract static class BitmapTarget extends BaseTarget<Bitmap> {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            onSuccess(resource);
        }

        protected abstract void onSuccess(Bitmap bitmap);
    }

}
