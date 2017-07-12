package com.bilibili.ui.bangumi;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.R;

/**
 * Created by miserydx on 17/7/2.
 */

public class BangumiIndexItemDecoration extends RecyclerView.ItemDecoration {

    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    public BangumiIndexItemDecoration(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (spanSizeLookup.getSpanSize(position) == 3) {
            if (position == 1) {
                outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.bangumi_index_home_bottom_margin);
            }
            if (position > 1) {//追翻页列表前两项为静态item
                outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
            }
        } else {
            int spanIndex = spanSizeLookup.getSpanIndex(position, 3);
            switch (spanIndex) {
                case 0:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_min);
                    break;
                case 1:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.common_margin_7);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.common_margin_7);
                    break;
                case 2:
                    outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_min);
                    outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    break;
            }
            outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.common_margin_15);
        }
    }

}
