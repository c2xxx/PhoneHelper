package com.chen.phonehelper.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.chen.base.adapter.CommonAdapter;
import com.chen.base.bean.ContentBean;
import com.chen.phonehelper.R;

import java.util.List;

import cn.broadin.libutils.GlideImage;

/**
 * Created by ChenHui on 2017/9/14.
 */

public class RunningTimeListAdapter extends CommonAdapter<ContentBean> {

    public RunningTimeListAdapter(Context mContext, List<ContentBean> mList) {
        super(mContext, mList);
    }

    @Override
    public int getContentLayout(int viewType) {
        return R.layout.item_show_time;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ContentBean item = mList.get(position);
        holder.text.setText(item.getText());
        if (holder.subText != null) {
            holder.subText.setText(item.getSubText());
        }
        holder.border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyClick(v, position);
            }
        });
        if (item.isSelected()) {
            holder.border.setBackgroundColor(Color.GREEN);
        } else {
            holder.border.setBackgroundColor(Color.WHITE);
        }
        if (item.getImgResId() != 0) {
            GlideImage.show(mContext, item.getImgResId(), holder.img);
        } else {
            GlideImage.show(mContext, item.getImgUrl(), holder.img);
        }
    }
}
