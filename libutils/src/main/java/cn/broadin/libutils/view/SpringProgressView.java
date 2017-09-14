package cn.broadin.libutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/***
 * 自定义进度条
 *
 */
public class SpringProgressView extends View {

    /**
     * 分段颜色
     */
    private int[] section_colors = {0xFF139eff, 0xFF48d4e9};
    /**
     * 分段颜色(进度)48d4e9   20%
     */
    private int[] section_colors2 = {0x3348d4e9, 0x3348d4e9};
    /**
     * 背景颜色
     */
    private int[] section_background = {0x33FFFFFF, 0x33FFFFFF};

    /**
     * 进度条最大值
     */
    private float maxCount;
    /**
     * 进度条当前值
     */
    private float currentCount;
    /**
     * 第二度条当前值
     */
    private float secondCount;
    /**
     * 画笔
     */
    private Paint mPaint;
    private int mWidth, mHeight;

    /**
     * 设置起止颜色
     *
     * @param section_colors
     */
    public void setSection_colors(int[] section_colors) {
        this.section_colors = section_colors;
    }

    public SpringProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SpringProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SpringProgressView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawA(canvas, maxCount, section_background);
        if (secondCount > 0) {
            onDrawA(canvas, secondCount, section_colors2);
        }
        onDrawA(canvas, currentCount, section_colors);
    }

    private void onDrawA(Canvas canvas, float progress, int[] colors) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;

        float section = progress / maxCount;
        int border = 0;
        RectF rectProgressBg = new RectF(border, border, (mWidth - border) * section, mHeight - border);
        {
            int count = colors.length;
            float[] positions = new float[count];
            if (count == 2) {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            } else if (count == 3) {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / progress;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(3, 3, (mWidth - 3) * section, mHeight - 3, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    /***
     * 设置当前的进度值,一次设置两个值
     *
     * @param currentCount
     */
    public void setTwoCount(float currentCount, float secondCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        this.secondCount = secondCount > maxCount ? maxCount : secondCount;
//        Logger.d("currentCount=" + currentCount + "     secondCount=" + secondCount);
        invalidate();
    }


    public float getSecondCount() {
        return secondCount;
    }

    /***
     * 设置第二进度条的值
     *
     * @param secondCount
     */
    public void setSecondCount(float secondCount) {
        this.secondCount = secondCount > maxCount ? maxCount : secondCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
