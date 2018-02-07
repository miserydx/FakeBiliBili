package com.bilibili.ui.region;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.R;
import com.common.widget.adapter.DefaultAdapterWrapper;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionIndexItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 注意因为Adapter的封装，外部构造的spanSizeLookup在Adapter中的
     * {@link DefaultAdapterWrapper#onAttachedToRecyclerView(RecyclerView)}中被包裹一层，所以不能直接传入
     * 用做参数，用时应从RecyclerView对象中获取
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) parent.getLayoutManager()).getSpanSizeLookup();
        int spanSize = spanSizeLookup.getSpanSize(position);
        int margin_normal = view.getContext().getResources().getDimensionPixelOffset(R.dimen.margin_normal);
        int margin_small = view.getContext().getResources().getDimensionPixelOffset(R.dimen.margin_small);
        int margin_tiny = view.getContext().getResources().getDimensionPixelOffset(R.dimen.margin_tiny);
        if (spanSize == 2) {
            if (position == 0) { //header
                outRect.bottom = margin_tiny;
            } else {
                outRect.left = margin_normal;
                outRect.right = margin_normal;
                outRect.bottom = margin_small;
                outRect.top = margin_small;
            }
        } else {
            int index = spanSizeLookup.getSpanIndex(position , 2);
            switch (index) {
                case 0:
                    outRect.left = margin_normal;
                    outRect.right = margin_small;
                    break;
                case 1:
                    outRect.left = margin_small;
                    outRect.right = margin_normal;
                    break;
            }
            outRect.bottom = margin_small;
            outRect.top = margin_small;
        }
    }
}
