package com.common.widget.recyclerview.listener;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.common.widget.recyclerview.BaseAdapterWrapper;

/**
 * Created by miserydx on 17/7/31.
 */

public class CommonOnScrollListener extends RecyclerView.OnScrollListener {

    private BaseAdapterWrapper.OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false;

    public void setOnLoadMoreListener(BaseAdapterWrapper.OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    public void setLoadingFinished() {
        isLoading = false;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!ViewCompat.canScrollVertically(recyclerView, 1) && !isLoading) {
            if (onLoadMoreListener != null) {
                isLoading = true;
                onLoadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }
}
