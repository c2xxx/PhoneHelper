package com.chen.phonehelper.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chen.base.activity.BaseMVPActivity;
import com.chen.base.bean.ContentBean;
import com.chen.phonehelper.R;
import com.chen.phonehelper.adapter.RunningTimeListAdapter;
import com.chen.phonehelper.presenter.IShowRunningTimeDetailPresenter;
import com.chen.phonehelper.presenter.impl.ShowRunningTimeDetailPresenter;
import com.chen.phonehelper.ui.IShowRunningTimeDetailView;
import com.chen.phonehelper.view.dialog.PickerDialog;
import com.chen.phonehelper.view.dialog.timePpicker.MyDateTimePickDialogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.broadin.libutils.ToastUtil;
import cn.broadin.libutils.interfaces.OnItemClickListener;

public class ShowRunningTimeDetailActivity
        extends BaseMVPActivity<IShowRunningTimeDetailPresenter>
        implements IShowRunningTimeDetailView {

    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private long selectedTime;

    @Override
    protected IShowRunningTimeDetailPresenter createActivityPresenter() {
        return new ShowRunningTimeDetailPresenter(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_show_running_time_detail;
    }

    @Override
    protected void initView() {
        setTitle("显示运行时间");
    }

    @Override
    protected void initData() {
        long mMin = 60 * 1000;//每分钟毫秒数
        long mHour = 60 * mMin;//一小时毫秒数
        long end = System.currentTimeMillis() + mMin;//当前时间
        long begin = end - 10 * mHour;//减去小时数
        mPresenter.loadDataList(begin, end);
    }

    @Override
    public void displayList(List<ContentBean> list) {
        if (list == null || list.isEmpty()) {
            ToastUtil.show("结果为空！");
            return;
        }
        String startTime = list.get(0).getText();
        String endTime = list.get(list.size() - 1).getText();
        tvDescribe.setText(String.format("开始时间：%s\n结束时间：%s", startTime, endTime));
        RunningTimeListAdapter adapter = new RunningTimeListAdapter(this, list);
        adapter.setOnItemClick(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show("位置：" + position);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);
    }

    @OnClick(R.id.btn_change_time)
    public void changeTime() {
        long end = System.currentTimeMillis();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String initTime = sdf.format(end); // 初始化时间
        MyDateTimePickDialogUtil dateTimePicKDialog = new MyDateTimePickDialogUtil(this, initTime) {
            /**
             * 点击确定后得到的时间
             *
             * @param dateTime
             */
            protected void onSubmitTime(String dateTime) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                try {
                    Date time = sdf.parse(dateTime);
                    doSelectHourCount(time.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ToastUtil.show("时间：" + dateTime);
            }
        };
        dateTimePicKDialog.show();
    }

    /**
     * 选择几个小时
     */
    private void doSelectHourCount(long startTime) {
        selectedTime = startTime;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add("" + (i + 1));
        }
        PickerDialog dialog = new PickerDialog(this) {
            @Override
            protected void onSelectedValue(String value, int position) {
                int count = Integer.parseInt(value);
                mPresenter.loadDataList(selectedTime - count * 60 * 60 * 1000, selectedTime);
            }
        };
        dialog.show("请选择往前几个小时", 4, list);
    }
}
