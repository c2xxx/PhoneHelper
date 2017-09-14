package cn.broadin.libutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast提示
 * Created by ChenHui on 2017/5/31.
 */

public class ToastUtil {
    private static Toast toast;

//    public static void showTips(String msg) {
//        if (toast == null) {
//            toast = Toast.makeText(AppContext.getContext(), msg, Toast.LENGTH_LONG);
//        } else {
//            toast.setText(msg);
//        }
//        toast.show();
//    }

    /**
     * 显示对话信息
     *
     * @param msg
     */
    public static void show(String msg) {
        if (toast == null) {
            Context context = AppContext.getContext();
            toast = new Toast(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.yyt_widget_tips, null);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) toast.getView().findViewById(R.id.tips_content);
            textView.setText(msg);
            toast.show();
        } else {
            toast.cancel();
        }
    }


    public static void showNetErr() {
        show("抱歉，发生网络异常，请稍后再试！");
    }

    public static void showCheckNetwork() {
        show("网络不可用,请检查您的网络！");
    }
}
