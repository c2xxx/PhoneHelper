package com.chen.phonehelper.view.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.chen.phonehelper.view.PickerView;

import java.util.List;

import cn.broadin.libutils.ToastUtil;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class PickerDialog {

    Activity activity;

    public PickerDialog(Activity activity) {
        this.activity = activity;
    }

    public void show(List<String> list) {
        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle("请选择");
        PickerView picker = new PickerView(activity);
        picker.setData(list);
        build.setView(picker);
        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = picker.getCurrentValue();
                onSelectedValue(value, i);
            }
        });
        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.show();

    }

    public void show(String title, int position, List<String> list) {
        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle(title);
        PickerView picker = new PickerView(activity);
        picker.setData(list);
        picker.setSelected(position);
        build.setView(picker);
        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = picker.getCurrentValue();
                int position = picker.getCurrentPosition();
                onSelectedValue(value, position);
            }
        });
        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.show();

    }

    protected void onSelectedValue(String value, int position) {
        ToastUtil.show(value + position);
    }
}
