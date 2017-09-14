package cn.broadin.libutils.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * ViewPager切换滑动速度修改
 * Created by ChenHui on 2016/4/27.
 */
public class FixedSpeedScroller extends Scroller {

//        try {//设置ViewPaper切换时长
//            Field field = ViewPager.class.getDeclaredField("mScroller");
//            field.setAccessible(true);
//            FixedSpeedScroller scroller = new FixedSpeedScroller(vp_basealbum.getContext(),
//                    new AccelerateInterpolator());
//            field.set(vp_basealbum, scroller);
//            scroller.setmDuration(2000);
//        } catch (Exception e) {
//            Logger.e(e);
//        }
    private int mDuration = 1500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }
}