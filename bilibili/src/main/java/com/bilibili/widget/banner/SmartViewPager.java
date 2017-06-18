package com.bilibili.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bilibili.R;

import java.lang.ref.WeakReference;

/**
 * Created by Android_ZzT on 17/5/15.
 */

public class SmartViewPager extends FrameLayout {

    private static final int DEFAULT_CIRCULATE_DELAY_TIME = 3000;

    public static final int MSG_AUTO_CIRCULATE_START = 1000;

    private static CirculateHandler sHandler;

    private Context mContext;

    private ViewPager mViewPager;

    private IndicatorView mIndicator;

    private int mCurItemPosition;

    private boolean mIsNeedIndicator;

    private boolean mIsNeedAutoScroll;

    private boolean mIsNeedCirculate;

    private int mDelayedTime;

    private PagerAdapter mAdapter;

    public SmartViewPager(Context context) {
        this(context, null, 0);
    }

    public SmartViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sHandler = new CirculateHandler(this);
        mContext = context;
        initView();

        TypedArray a = context.obtainStyledAttributes(R.styleable.SmartViewPager);
        mDelayedTime = a.getInteger(R.styleable.SmartViewPager_scroll_delay, DEFAULT_CIRCULATE_DELAY_TIME);
        mIsNeedAutoScroll = a.getBoolean(R.styleable.SmartViewPager_auto_scroll, false);
        mIsNeedIndicator = a.getBoolean(R.styleable.SmartViewPager_need_indicator, true);
        mIsNeedCirculate = a.getBoolean(R.styleable.SmartViewPager_circulate, false);
        a.recycle();
    }

    private void initView() {
        mViewPager = new ViewPager(mContext);
        LayoutParams viewPagerLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(viewPagerLp);
        mViewPager.addOnPageChangeListener(mPageChangeListener);

        mIndicator = new IndicatorView(mContext);
        LayoutParams indicatorLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        indicatorLp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        indicatorLp.bottomMargin = bottomMargin;
        mIndicator.setLayoutParams(indicatorLp);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superSate = super.onSaveInstanceState();
        SavedState ss = new SavedState(superSate);
        ss.position = mCurItemPosition;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);
        mViewPager.setCurrentItem(ss.position);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (mIsNeedAutoScroll) {
            if (visibility == GONE) {
                sHandler.removeMessages(MSG_AUTO_CIRCULATE_START);
            } else {
                sHandler.sendEmptyMessageDelayed(MSG_AUTO_CIRCULATE_START, mDelayedTime);
            }
        }
        mViewPager.setCurrentItem(mCurItemPosition);
    }

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = adapter;
        mViewPager.setAdapter(adapter);
        if (mIsNeedIndicator) {
            int count = mIsNeedCirculate ? (mAdapter.getCount() - 2) : mAdapter.getCount();
            mIndicator.setItemCount(count);
            addView(mIndicator);
        }
        addView(mViewPager);
    }

    public void setNeedAutoCirculate(boolean needAutoCirculate) {
        mIsNeedAutoScroll = needAutoCirculate;
        sHandler.sendEmptyMessageDelayed(MSG_AUTO_CIRCULATE_START, mDelayedTime);
    }

    public void setNeedCirculate(boolean isCirculate) {
        mIsNeedCirculate = isCirculate;
    }

    public void setIndicatorColor(int color, int indicatorColor) {
        mIndicator.setColor(color);
        mIndicator.setIndicatorColor(indicatorColor);
    }

    public void setIndicatorRadius(int dp) {
        mIndicator.setRadius(dp);
    }

    public void setIndicatorGravity(int gravity) {
        LayoutParams indicatorLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        indicatorLp.gravity = gravity;
        int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        indicatorLp.bottomMargin = bottomMargin;
        mIndicator.setLayoutParams(indicatorLp);
        requestLayout();
    }

    private Runnable mCirculateTask = new Runnable() {
        @Override
        public void run() {
            if (mCurItemPosition == mAdapter.getCount() - 1) {
                mViewPager.setCurrentItem(0, true);
            } else {
                mViewPager.setCurrentItem(mCurItemPosition + 1, true);
            }
        }
    };

    private static class CirculateHandler extends Handler {

        WeakReference<SmartViewPager> ref;

        public CirculateHandler(SmartViewPager smartViewPager) {
            ref = new WeakReference<>(smartViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            SmartViewPager smartViewPager = ref.get();
            if (smartViewPager == null) {
                return;
            }
            if (msg.what == MSG_AUTO_CIRCULATE_START) {
                if (hasMessages(MSG_AUTO_CIRCULATE_START)) {
                    return;
                }
                post(smartViewPager.mCirculateTask);
                sendEmptyMessageDelayed(MSG_AUTO_CIRCULATE_START, smartViewPager.mDelayedTime);
            }
        }
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mIndicator.setPositionAndOffset(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            mCurItemPosition = position;
            if (mIsNeedCirculate) {
                final int count = mAdapter.getCount();
                if (position == 0) {
                    //让 task 的动画先执行完，再将 currentItem 跳到正确位置
                    //否则最后一张图和第一张衔接的时候不会出现 smoothScroll 的动画，显得很突兀
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(count - 2, false);
                        }
                    }, 200);
                } else if (position == count - 1) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(1, false);
                        }
                    }, 200);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    if (mIsNeedAutoScroll) {
                        if (!sHandler.hasMessages(MSG_AUTO_CIRCULATE_START)) {
                            sHandler.sendEmptyMessageDelayed(MSG_AUTO_CIRCULATE_START, mDelayedTime);
                        }
                    }
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    if (mIsNeedAutoScroll) {
                        if (sHandler.hasMessages(MSG_AUTO_CIRCULATE_START)) {
                            sHandler.removeMessages(MSG_AUTO_CIRCULATE_START);
                        }
                    }
                    break;
            }
        }
    };

    private static class SavedState extends BaseSavedState {

        int position;

        public SavedState(Parcelable superSate) {
            super(superSate);
        }

        public SavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
