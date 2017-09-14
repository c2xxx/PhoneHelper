package cn.broadin.libutils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by ChenHui on 2017/9/6.
 */

public class LeakUtils {
    public static void doDestroy(Activity activity) {
        try {
            //处理内存泄漏
            fixInputMethodManagerLeak(activity);
            new Handler().removeCallbacksAndMessages(null);
        } catch (Exception e) {
        } catch (Error e) {
        }
    }

    /**
     * 输入法
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
//        if (SDK_INT < KITKAT || SDK_INT > 22) {
//            return;
//        }
        if (destContext == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f == null) {
                    return;
                }
                f.setAccessible(true);
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
//                        Logger.e("置空，破坏掉path to gc节点");
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
