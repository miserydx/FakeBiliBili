package com.bilibili.ui.recommed;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.R;

/**
 * Created by miserydx on 17/7/6.
 */

public class RecommendIndexItemDecoration extends RecyclerView.ItemDecoration {

    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    public RecommendIndexItemDecoration(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (spanSizeLookup.getSpanSize(position) == 3) {
            outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
            outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
        } else {
            int spanIndex = spanSizeLookup.getSpanIndex(position, 2);
            switch (spanIndex) {
                case 0:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                    break;
                case 1:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
                    break;
            }
        }
        outRect.top = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_10);
        if (position == state.getItemCount() - 1 || position == state.getItemCount() - 2) {
            outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
        }
    }
}