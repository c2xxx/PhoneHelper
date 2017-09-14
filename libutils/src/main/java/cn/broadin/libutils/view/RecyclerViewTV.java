package cn.broadin.libutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import cn.broadin.libutils.Logger;

/**
 * Created by ChenHui on 2017/6/29.
 */

public class RecyclerViewTV extends RecyclerView {

    private int mSelectedPosition = 0;

    public RecyclerViewTV(Context context) {
        super(context);
        init();
    }

    public RecyclerViewTV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewTV(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //启用子视图排序功能
        setChildrenDrawingOrderEnabled(true);
        setClipChildren(false);
        setClipChildren(false);
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition=getChildLayoutPosition(getFocusedChild());
        super.onDraw(c);
    }

    /**在开始纠正{@link #mSelectedPosition}的值，
     * {@link #getChildLayoutPosition(View child)}这个方法在有item移除屏幕时，得出的值是加上移除屏幕的item个数的，与childCount不对应
     *
     * @param childCount
     * @param i
     * @return
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        View v=getFocusedChild();
        if(null!=v) {
            if(v==getChildAt(i)){
                mSelectedPosition=i;
            }
        }
        int position = mSelectedPosition;
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {
                return childCount - 1;
            }
        }
        return i;
    }

}
