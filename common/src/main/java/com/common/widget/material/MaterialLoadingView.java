package com.common.widget.material;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;

/**
 * Created by miserydx on 17/10/30.
 */

public class MaterialLoadingView extends AppCompatImageView {

    private MaterialProgressDrawable mProgress;

    public MaterialLoadingView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MaterialLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MaterialLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaterialLoadingView);
        int mStyle = ta.getInt(R.styleable.MaterialLoadingView_style, MaterialProgressDrawable.LARGE);
        int mColor = ta.getColor(R.styleable.MaterialLoadingView_ring_color,
                ContextCompat.getColor(context, android.R.color.black));
        ta.recycle();

        mProgress = new MaterialProgressDrawable(context, this);
        mProgress.updateSizes(mStyle);
        mProgress.setColorSchemeColors(mColor);
        setImageDrawable(mProgress);
        mProgress.setAlpha(255);
        mProgress.start();
    }

    /**
     * 设置圆圈大小
     */
    public void updateSizes(@MaterialProgressDrawable.ProgressDrawableSize int style) {
        mProgress.updateSizes(style);
    }

    /**
     * 设置圆圈颜色
     *
     * @param colors 颜色可为可变参数
     */
    public void setColors(int... colors) {
        mProgress.setColorSchemeColors(colors);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        final boolean visible = visibility == VISIBLE && getVisibility() == VISIBLE;
        if (visible) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void startAnimation() {
        if (mProgress != null) {
            mProgress.start();
        }
    }

    private void stopAnimation() {
        if (mProgress != null) {
            mProgress.stop();
        }
    }

}
