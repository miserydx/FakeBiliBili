package com.bilibili.widget.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Android_ZzT on 17/5/15.
 */

public class IndicatorView extends View {

    public static final int DEFAULT_ITEM_COUNT = 0;

    public static final int DEFAULT_COLOR = Color.WHITE;

    public static final int DEFAULT_INDICATOR_COLOR = Color.GRAY;

    private int mItemCount = DEFAULT_ITEM_COUNT;

    private float mRadius;

    private float mIndicatorRadius;

    private int mColor = DEFAULT_COLOR;

    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;

    private float mPadding;

    private Paint mPaint;

    private Paint mIndicatorPaint;

    private float mIndicatorOffset;

    private int mPosition;

    public IndicatorView(Context context) {
        this(context, null, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mColor);

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mIndicatorPaint.setColor(mIndicatorColor);

        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        mIndicatorRadius = mRadius;
        mPadding = mRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(
                (int) ((mPadding + mRadius * 2) * mItemCount + mPadding),
                (int) ((mPadding + mRadius) * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mItemCount; i++) {
            canvas.drawCircle(
                    (mPadding + mRadius * 2) * i + mPadding + mRadius
                    , mPadding + mRadius
                    , mRadius,
                    mPaint);
        }

        canvas.drawCircle(mPadding + mRadius + (mPadding + mRadius * 2) * (mPosition + mIndicatorOffset)
                , mPadding + mRadius
                , mIndicatorRadius
                , mIndicatorPaint);

    }

    public void setPosition(int position) {
        mPosition = position % mItemCount;
        invalidate();
    }

    public void setPositionAndOffset(int position, float positionOffset) {
        mPosition = position % mItemCount;
        mIndicatorOffset = positionOffset;
        if (mPosition == mItemCount - 1) {
            mIndicatorOffset = 0;
        }
        invalidate();
    }

    public void setItemCount(int itemCount) {
        mItemCount = itemCount;
        invalidate();
    }

    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }

    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        mIndicatorPaint.setColor(color);
        invalidate();
    }

    public void setPadding(float padding) {
        mPadding = padding;
        invalidate();
    }

    public void setRadius(int dp) {
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        mIndicatorRadius = mRadius;
        invalidate();
    }
}
