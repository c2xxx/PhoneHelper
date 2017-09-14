package com.chen.apptest.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.chen.apptest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenHui on 2017/9/7.
 */

public class ObjectAnimationTestActivity extends Activity {
    @BindView(R.id.btn_test_01)
    Button btnTest01;
    @BindView(R.id.btn_test_02)
    Button btnTest02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test_01)
    public void onClick01() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(btnTest02, "rotation", 0f, 360f);
        LinearInterpolator lin = new LinearInterpolator();
        anim.setInterpolator(lin);
        anim.setDuration(10000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
    }
}
