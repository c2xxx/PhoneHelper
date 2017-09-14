package com.chen.phonehelper.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.chen.base.activity.BaseMVPActivity;
import com.chen.phonehelper.R;
import com.chen.phonehelper.presenter.IWelcomePresenter;
import com.chen.phonehelper.presenter.impl.WelcomePresenter;
import com.chen.phonehelper.ui.IWelcomeView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Welcome
        extends BaseMVPActivity<IWelcomePresenter>
        implements IWelcomeView {

    @BindView(R.id.iv_welcome_main)
    ImageView ivWelcomeMain;
    private Disposable timerHolder;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        ivWelcomeMain.setImageResource(R.drawable.default_poster);
    }

    @Override
    protected void initData() {
        timerHolder = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Welcome.this.startActivity(new Intent(Welcome.this, HomeActivity.class));
                        Welcome.this.finish();
                    }
                });
    }

    @Override
    protected IWelcomePresenter createActivityPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHolder != null) {
            if (!timerHolder.isDisposed()) {
                timerHolder.dispose();
                timerHolder = null;
            }
        }
    }
}
