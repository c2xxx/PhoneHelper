package com.chen.base.bean;

import java.io.Serializable;

/**
 * 内容展示
 * Created by ChenHui on 2016/4/25.
 */
public class ContentBean<T> implements Serializable {
    private String text;//标题，不可空
    private String imgUrl;//远程图片路径
    private int imgResId;//本地图片资源
    private String subText;//子标题
    private String type;//类型
    private boolean isSelected;
    private T obj;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgResId() {
        return imgResId;
    }

    /**
     * @return 是否设置了本地图片资源
     */
    public boolean isImageLocalRes() {
        return imgResId != 0;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
