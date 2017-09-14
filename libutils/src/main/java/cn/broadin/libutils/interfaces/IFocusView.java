package cn.broadin.libutils.interfaces;

import android.view.View;

/**
 * Created by ChenHui on 2017/7/27.
 */

public interface IFocusView {
    enum MOVE_TYPE {
        STYLE_MOVE,//风格：从A移动到B,默认，原始风格
        STYLE_NEW,//风格：从A直接到B,跟随B变化大小
        STYLE_SCALE,//风格：缩放
        DEFAULT,//默认，用于设置而已。。。。
    }

    /**
     * 设置焦点框
     *
     * @param focusScale   负数表示不变
     * @param resFocusView 负数表示不变
     * @param movingTime   负数表示不变
     * @param moveType     非指定类型表示不变
     */
    void onFocusViewChangedSetting(float focusScale, int resFocusView, int movingTime, String moveType);

    /**
     * 处理焦点移动事件，全部参数，同个界面用到多种类型的焦点框时应该用这个方法
     *
     * @param v
     * @param hasFocus
     * @param focusScale
     * @param resFocusView
     * @param movingTime   移动时间，-1表示不变
     * @param moveType     移动的类型,为空表示不改变
     */
    void onFocusViewChanged(View v, boolean hasFocus, float focusScale, int resFocusView, int movingTime, String moveType);

    /**
     * 处理焦点移动事件，简化参数，需要至少一次设置过参数
     * 焦点变化
     *
     * @param v        焦点控件
     * @param hasFocus 是否有焦点
     */
    void onFocusViewChanged(View v, boolean hasFocus);

    /**
     * 聚焦到最后一个控件，相当于刷新
     */
    void focusLastView();
}
