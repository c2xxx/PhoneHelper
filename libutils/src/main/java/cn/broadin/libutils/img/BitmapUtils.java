package cn.broadin.libutils.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.io.InputStream;

import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2017/7/20.
 */

public class BitmapUtils {

    /**
     * 设置
     *
     * @param context
     * @param view
     * @param drawableId
     */
    public static void setBackgroundResource(Context context, View view, int drawableId) {
        if (view == null) {
            return;
        }
        view.setBackgroundResource(drawableId);
//        Bitmap bitmap = readBitMap(context, drawableId);
//        view.setBackground(new BitmapDrawable(bitmap));
    }

    /**
     * 以最省内存的方式读取本地资源的图片(貌似原理是：底层调用jni，不占用java部分内存)
     *
     * @param drawableId
     * @return
     */

    public static Bitmap readBitMap(Context context, int drawableId) {
//        if (true) {
//            return null;
//        }
        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;

        opt.inPurgeable = true;// 允许可清除

        opt.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果

        // 获取资源图片

        try {
            InputStream is = context.getResources().openRawResource(drawableId);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (Exception e) {
            Logger.e("drawableId=" + drawableId);
            Logger.e(e);
            return null;
        } catch (Error e) {
            Logger.e("drawableId=" + drawableId);
            Logger.e(e);
            return null;
        }
    }
}
