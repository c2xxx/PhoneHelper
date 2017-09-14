package com.chen.phonehelper.util.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.chen.phonehelper.R;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class SelectorDialog {
    Activity activity;

    public SelectorDialog(Activity activity) {
        this.activity = activity;
    }

//    /**
//     * 弹出日期时间选择框方法
//     *
//     * @return
//     */
//    public AlertDialog show() {
//
////        ad = new AlertDialog.Builder(activity)
////                .setTitle()
////                .seT
////                .setView(dateTimeLayout)
////                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int whichButton) {
//////                        inputDate.setText(dateTime);
////                        onSubmitTime(dateTime);
////                    }
////                })
////                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int whichButton) {
//////                        inputDate.setText("");
////                    }
////                }).show();
////
////        onDateChanged(null, 0, 0, 0);
////        return ad;
//    }
}
