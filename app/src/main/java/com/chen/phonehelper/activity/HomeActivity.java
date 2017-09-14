package com.chen.phonehelper.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.chen.base.activity.BaseMVPActivity;
import com.chen.phonehelper.R;
import com.chen.phonehelper.presenter.IHomePresenter;
import com.chen.phonehelper.presenter.impl.HomePresenter;
import com.chen.phonehelper.ui.IHomeView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.broadin.libutils.ToastUtil;
import cn.broadin.libutils.finger.FingerHelper;

public class HomeActivity
        extends BaseMVPActivity<IHomePresenter>

        implements IHomeView {

    @BindView(R.id.iv_welcome_main)
    ImageView ivWelcomeMain;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        ivWelcomeMain.setImageResource(R.drawable.default_poster_v);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected IHomePresenter createActivityPresenter() {
        return new HomePresenter(this);
    }

    /**
     * 开启服务
     */
    @OnClick(R.id.btn_01)
    public void showSunningTime() {
        Intent intent = new Intent(this, ShowRunningTimeDetailActivity.class);
        startActivity(intent);
    }

    /**
     * 开启服务
     */
    @OnClick(R.id.btn_02)
    public void test() {
        Intent intent = new Intent(this, PickerActivity.class);
        startActivity(intent);
    }
    /**
     * 开启服务
     */
    @OnClick(R.id.btn_03)
    public void openService() {
    }

    /**
     * 指纹验证
     */
    @OnClick(R.id.btn_04)
    public void checkZhiWen() {
        FingerHelper fh = new FingerHelper(this){
            @Override
            protected void doSuccess() {
                ToastUtil.show("验证成功");
            }
        };
        if (fh.isSupport()) {
            fh.check();
        } else {
            ToastUtil.show("不支持哦");
        }
    }


}
