package cn.broadin.libutils.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Erone on 2017/06/12 0012.
 */

public class LevelImageView extends android.support.v7.widget.AppCompatImageView {
    public LevelImageView(Context context) {
        super(context);
    }

    public LevelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int imageLevel = 0;
    public void setImageLevel(int level) {
        if (this.imageLevel == level)
            return;
        super.setImageLevel(level);
        this.imageLevel = level;
    }
    public int getImageLevel() {
        return imageLevel;
    }
    // 下一level接口。
    public void nextLevel() {
        setImageLevel(imageLevel++ % maxLevel);
    }
    private int maxLevel = 10;

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
