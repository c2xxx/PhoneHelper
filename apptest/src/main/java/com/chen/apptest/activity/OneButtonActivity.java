package com.chen.apptest.activity;

import android.widget.ImageView;

import com.chen.apptest.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChenHui on 2017/9/6.
 */

public class OneButtonActivity extends BaseActivity {

    @BindView(R.id.activity_root_view)
    ImageView imageView;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_one_button;
    }

    @OnClick(R.id.btn_one_button_01)
    public void oneButtonClick() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
