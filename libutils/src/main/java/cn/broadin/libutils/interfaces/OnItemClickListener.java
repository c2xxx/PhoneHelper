package cn.broadin.libutils.interfaces;

import android.view.View;

/**
 * Created by ChenHui on 2016/4/26.
 */
public interface OnItemClickListener {
    /**
     * @param view     点击的控件
     * @param position 点击的位置
     */
    void onItemClick(View view, int position);
}
