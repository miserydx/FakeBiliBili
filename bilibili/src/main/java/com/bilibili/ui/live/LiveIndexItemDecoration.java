package com.bilibili.ui.live;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveHeader;

/**
 * Created by miserydx on 17/7/6.
 */

public class LiveIndexItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) parent.getLayoutManager()).getSpanSizeLookup();
        if (spanSizeLookup.getSpanSize(position) == 2) {
            if (!(view.getTag() instanceof LiveHeader)) {
                outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
            }
        } else {
            int spanIndex = spanSizeLookup.getSpanIndex(position, 2);
            switch (spanIndex) {
                case 0:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                    break;
                case 1:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    break;
            }
        }
        if (!(view.getTag() instanceof LiveHeader)) {
            outRect.top = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
        }
    }
}