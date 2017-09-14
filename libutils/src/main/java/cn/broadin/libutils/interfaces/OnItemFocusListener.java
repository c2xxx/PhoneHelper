package cn.broadin.libutils.interfaces;

import android.view.View;

/**
 * Created by ChenHui on 2016/4/26.
 */
public interface OnItemFocusListener {
    /**
     * @param position 点击的位置
     */
    void onFocusChange(View view, boolean hasFocus, int position);

}
