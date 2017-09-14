package com.chen.phonehelper.activity;

import com.chen.base.activity.BaseActivity;
import com.chen.phonehelper.R;
import com.chen.phonehelper.view.PickerView;

import java.util.ArrayList;
import java.util.List;

import cn.broadin.libutils.ToastUtil;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class PickerActivity extends BaseActivity {
    PickerView minute_pv;
    PickerView second_pv;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_picker;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        second_pv = (PickerView) findViewById(R.id.second_pv);
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            data.add("0" + i);
        }
        for (int i = 0; i < 60; i++) {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        minute_pv.setData(data);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                ToastUtil.show("选择了 " + text + " 分");
            }
        });
        second_pv.setData(seconds);
        second_pv.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                ToastUtil.show("选择了 " + text + " 秒");
            }
        });
    }
}
