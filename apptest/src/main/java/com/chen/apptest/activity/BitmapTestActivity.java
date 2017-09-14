package com.chen.apptest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.chen.apptest.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.broadin.libutils.Logger;
import cn.broadin.libutils.Utils;
import cn.broadin.libutils.img.BitmapUtils;

/**
 * Created by ChenHui on 2017/9/6.
 */

public class BitmapTestActivity extends BaseActivity {
    @BindView(R.id.activity_root_view)
    ImageView imageView;
    static int indexA = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bitmap;
    }


    @Override
    protected void initView() {
        indexA++;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_bitmap_test_click_05)
    public void oneButtonClick05() {
        Utils.printMemory();
    }

    @OnClick(R.id.btn_bitmap_test_click_04)
    public void oneButtonClick04() {
        Utils.printMemory();
    }

    @OnClick(R.id.btn_bitmap_test_click_03)
    public void oneButtonClick03() {
        startActivity(new Intent(this, BitmapTestActivity.class));
    }

    @OnClick(R.id.btn_bitmap_test_click_02)
    public void oneButtonClick02() {
        imageView.setImageBitmap(null);
        imageView.setBackground(null);
        Utils.printMemory();
    }

    @OnClick(R.id.btn_bitmap_test_click_01)
    public void oneButtonClick01() {
        Utils.printMemory();
        Logger.time1();
//        Glide.with(this)
//                .load(R.drawable.le_bg_common)
//                .asBitmap()
//                .into(imageView);
        Logger.d("indexA=" + indexA);
        int res = indexA == 1 ? R.drawable.le_bg_common : R.drawable.le_bg_children;
//        imageView.setImageResource(res);
//        imageView.setBackgroundResource(res);
        int type = 1;
        if (type == 1) {
            imageView.setImageResource(res);
        }
        if (type == 2) {
            Bitmap bitmap1 = BitmapUtils.readBitMap(this, res);
            Logger.d("bitmap1 size=" + Utils.formatSize(bitmap1.getByteCount()));
            imageView.setBackground(new BitmapDrawable(bitmap1));
        }
        Logger.time2("set image ");
        Utils.printMemory();
    }

}
