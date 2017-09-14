package cn.broadin.libutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.broadin.libutils.R;


/**
 * Created by ChenHui on 2017/3/10.
 */
//不会出莫名其妙的错，但是有锯齿
public class RoundLayout extends RelativeLayout {
    private float roundLayoutRadius;
    private Path roundPath;
    private RectF rectF;

    public RoundLayout(Context context) {
        super(context);
    }

    public RoundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (attrs != null) {
//            float radius = context.getResources().getDimension(R.dimen.dimen_35dp);
//            float radius = 35;

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
            roundLayoutRadius = ta.getDimension(R.styleable.RoundRelativeLayout_radius, 0);
            ta.recycle();
        }
    }

    private void init() {
        setWillNotDraw(false);//如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        roundPath = new Path();
        rectF = new RectF();
    }

    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        roundPath.addRoundRect(rectF, roundLayoutRadius, roundLayoutRadius, Path.Direction.CW);
    }


    public void setRoundLayoutRadius(float roundLayoutRadius) {
        this.roundLayoutRadius = roundLayoutRadius;
        setRoundPath();
        postInvalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
    }

    @Override
    public void draw(Canvas canvas) {
        if (roundLayoutRadius > 0f) {
            canvas.clipPath(roundPath);
        }
        super.draw(canvas);
    }
}
