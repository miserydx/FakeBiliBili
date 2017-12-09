package com.bilibili.widget.textview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.common.util.ScreenUtil;

/**
 * Created by miserydx on 17/12/7.
 * Warning:在FrameLayout内使用
 */

public class AlwaysCenterTextView extends AppCompatTextView {

    public AlwaysCenterTextView(Context context) {
        super(context);
    }

    public AlwaysCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(final int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new Runnable() {
            @Override
            public void run() {
                if(!(getLayoutParams() instanceof FrameLayout.LayoutParams)){
                    throw new RuntimeException("this view only can be placed in FrameLayout");
                }
                int screenWidth = ScreenUtil.getScreenWidth(getContext());
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
                lp.setMargins(screenWidth / 2 - w / 2, 0, 0, 0);
                lp.leftMargin = screenWidth / 2 - w / 2;
                setLayoutParams(lp);
            }
        });

    }

}
