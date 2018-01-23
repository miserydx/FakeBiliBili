package com.bilibili.widget.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by miserydx on 17/12/26.
 */

public class BiliOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isScrollLoad = false;

    public void setScrollSaveStrategyEnabled(boolean flag) {
        isScrollLoad = flag;
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
    }
}
