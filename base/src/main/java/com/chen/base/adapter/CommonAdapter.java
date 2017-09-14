package com.chen.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.base.R;

import java.util.ArrayList;
import java.util.List;

import cn.broadin.libutils.interfaces.OnItemClickListener;
import cn.broadin.libutils.interfaces.OnItemFocusListener;

/**
 * Created by ChenHui on 2017/9/14.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {

    protected List<OnItemFocusListener> listItemFocus = new ArrayList<>();
    protected OnItemClickListener onItemClick;
    protected Context mContext;
    protected List<T> mList;

    public CommonAdapter(Context mContext, List<T> mList) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getContentLayout(viewType), parent, false);
        return new ViewHolder(view);
    }

    public int getContentLayout(int viewType) {
        return R.layout.item_simple_common;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void notifyFocus(View view, boolean hasFocus, int position) {
        if (listItemFocus != null) {
            for (OnItemFocusListener listener : listItemFocus) {
                listener.onFocusChange(view, hasFocus, position);
            }
        }
    }

    /**
     * 添加焦点监听（注意：可以设置多个，不会清除之前设置的）
     *
     * @param onItemFocus
     */
    public void setOnItemFocus(OnItemFocusListener onItemFocus) {
        listItemFocus.remove(onItemFocus);
        listItemFocus.add(onItemFocus);
    }

    public void removeOnItemFocus(OnItemFocusListener onItemFocus) {
        listItemFocus.remove(onItemFocus);
    }

    public OnItemClickListener getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }


    public void notifyClick(View view, int position) {
        if (onItemClick != null) {
            onItemClick.onItemClick(view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View border;
        public ImageView img;
        public TextView text;
        public TextView subText;

        public ViewHolder(View itemView) {
            super(itemView);
            border = itemView.findViewById(R.id.ll_item_border);
            img = (ImageView) itemView.findViewById(R.id.iv_item_img);
            text = (TextView) itemView.findViewById(R.id.tv_item_text);
            subText = (TextView) itemView.findViewById(R.id.tv_item_text_sub);
        }
    }
}
