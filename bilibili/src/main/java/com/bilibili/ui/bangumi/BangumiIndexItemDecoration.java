package com.bilibili.ui.bangumi;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.R;
import com.bilibili.ui.bangumi.viewbinder.BangumiDividerBinder;
import com.common.widget.recyclerview.BaseAdapterWrapper;

/**
 * Created by miserydx on 17/7/2.
 */

public class BangumiIndexItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * * 用于BangumiFragment的ItemDecoration，注意因为Adapter的封装，外部构造的spanSizeLookup在Adapter中的
     * {@link BaseAdapterWrapper#onAttachedToRecyclerView(RecyclerView)}中被包裹一层，所以不能直接传入用做参数，
     * 用时应从RecyclerView对象中获取
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) parent.getLayoutManager()).getSpanSizeLookup();
        if (spanSizeLookup.getSpanSize(position) == 3) {
            //追翻页列表前两项为静态item
            if (position > 1 && position < state.getItemCount() - 2) {
                if (!(view.getTag() instanceof BangumiDividerBinder.BangumiDivider)) {
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                }
                outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
            } else if (position == state.getItemCount() - 2) {//Bnaugmi有加载更多，特殊处理
                outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
            }
        } else {
            int spanIndex = spanSizeLookup.getSpanIndex(position, 3);
            switch (spanIndex) {
                case 0:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_min);
                    break;
                case 1:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_7);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_7);
                    break;
                case 2:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_min);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    break;
            }
            outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_15);
        }
    }

}