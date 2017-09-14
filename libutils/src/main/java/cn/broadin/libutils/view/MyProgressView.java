package cn.broadin.libutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import cn.broadin.libutils.R;

/**
 * Created by ChenHui on 2017/7/26.
 */

public class MyProgressView extends RelativeLayout {

    private TextView tvVideoProgressbarCurr;
    private TextView tvVideoProgressbarTotal;
    private SpringProgressView spv_progress;

    private void assignViews(View view) {
        tvVideoProgressbarCurr = (TextView) view.findViewById(R.id.progress_text_left);
        tvVideoProgressbarTotal = (TextView) view.findViewById(R.id.progress_text_right);
        spv_progress = (SpringProgressView) view.findViewById(R.id.spv_progress);
    }

    public MyProgressView(Context context) {
        this(context, null, 0);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_my_progress, null);
        assignViews(view);
        addView(view);
        spv_progress.setMaxCount(100);
        spv_progress.setTwoCount(0, 0);
        tvVideoProgressbarCurr.setText("00:00");
        tvVideoProgressbarTotal.setText("00:00");
    }


    /**
     * @param duration 总时长
     * @param currTime 当前播放
     */
    public void setProgress(long duration, long currTime) {
        String strProgress = millisToString(currTime, false);
        String strDuration = millisToString(duration, false);
        tvVideoProgressbarCurr.setText(strProgress);
        tvVideoProgressbarTotal.setText(strDuration);
        double percent = Math.ceil(currTime * 100.0 / duration);
        percent = Math.max(percent, 0);
        percent = Math.min(percent, 100);
//        Logger.d("进度百分比：" + percent);
        spv_progress.setCurrentCount((float) percent);
    }

    /**
     * @param percent 缓冲进度百分比
     */
    public void setProgressBuffer(int percent) {
        percent = Math.min(percent, 100);
        percent = Math.max(percent, 0);
        spv_progress.setSecondCount(percent);
    }

    static String millisToString(long millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + "h" + format.format(min) + "min";
            else if (min > 0)
                time = (negative ? "-" : "") + min + "min";
            else
                time = (negative ? "-" : "") + sec + "s";
        } else {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + ":" + format.format(min) + ":" + format.format(sec);
            else
                time = (negative ? "-" : "") + min + ":" + format.format(sec);
        }
        return time;
    }
}
