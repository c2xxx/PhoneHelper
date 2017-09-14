package com.chen.phonehelper.bean;

import com.chen.phonehelper.util.Utils;

import java.text.SimpleDateFormat;

/**
 * Created by ChenHui on 2017/9/12.
 */

public class RunningTime {
    private int id;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTimeNow() {
        SimpleDateFormat sdf = Utils.getDefaultFenZhongDateFormat();
        time = sdf.format(System.currentTimeMillis());
    }
}
