package com.bilibili.widget.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.R;

/**
 * Created by YoKeyword on 17/12/5.
 */
class BottomBarTab extends FrameLayout {
    private ImageView mIvIcon;
    private TextView mTvTitle;
    private Context mContext;
    private int mTabPosition = -1;

    private CustomTabEntity mEntity;

    public BottomBarTab(Context context) {
        this(context, null);
    }

    public BottomBarTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(CustomTabEntity entity){
        init(getContext(), entity);
    }

    private void init(Context context, CustomTabEntity entity) {
        mContext = context;
        mEntity = entity;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);

        mIvIcon = new ImageView(context);
//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        int size = dp2px(20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        mIvIcon.setImageResource(mEntity.getTabUnselectedIcon());
        mIvIcon.setLayoutParams(params);
//        mIvIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        lLContainer.addView(mIvIcon);

        mTvTitle = new TextView(context);
        mTvTitle.setText(mEntity.getTabTitle());
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        mTvTitle.setTextSize(10);
//        mTvTitle.setTextColor(ContextCompat.getColor(context, R.color.tab_unselect));
        mTvTitle.setLayoutParams(paramsTv);
        lLContainer.addView(mTvTitle);

        addView(lLContainer);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIvIcon.setImageResource(mEntity.getTabSelectedIcon());
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.theme_color_primary));
        } else {
            mIvIcon.setImageResource(mEntity.getTabUnselectedIcon());
            mTvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray));
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
