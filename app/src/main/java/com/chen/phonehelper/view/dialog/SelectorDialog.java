package com.chen.phonehelper.view.dialog;

import android.app.Activity;

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
