package com.bilibili.widget.recyclerview;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by miserydx on 17/7/31.
 */

public class BiliOnScrollListenerProxy extends RecyclerView.OnScrollListener {

    private RecyclerView.OnScrollListener target;
    private boolean isScrollLoad = false;
    private BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener;

    public void setTarget(RecyclerView.OnScrollListener target) {
        this.target = target;
    }

    public void setScrollSaveStrategyEnabled(boolean flag) {
        isScrollLoad = flag;
    }

    public void setOnLoadMoreListener(BiliMultiTypeAdapter.OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
            }
        }
        if (target != null) {
            target.onScrolled(recyclerView, dx, dy);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (isScrollLoad) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                Fresco.getImagePipeline().pause();
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                Fresco.getImagePipeline().resume();
            }
        }
        if (target != null) {
            target.onScrollStateChanged(recyclerView, newState);
        }
    }
}
