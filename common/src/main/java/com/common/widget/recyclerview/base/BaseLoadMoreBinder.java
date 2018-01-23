package com.common.widget.recyclerview.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.common.widget.recyclerview.BaseAdapterWrapper;

/**
 * 作用于MultiTypeAdapter的LoadMoreBinder的基类，封装了onViewAttachedToWindow加载更多的操作
 * Created by Android_ZzT on 17/7/23.
 */

public abstract class BaseLoadMoreBinder<T extends BaseLoadMoreItem, VH extends BaseViewHolder> extends BaseItemViewBinder<T, VH> {

    private BaseAdapterWrapper.OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading = false;

    public final void setLoadMoreFinished() {
        isLoading = false;
    }

    public final void setOnLoadMoreListener(BaseAdapterWrapper.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public BaseAdapterWrapper.OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    @Override
    protected final void onBindViewHolder(@NonNull final VH holder, @NonNull final T item) {
        switch (item.getState()) {
            case BaseLoadMoreItem.STATE_LOAD_MORE:
                initLoadMoreItemView(holder, item);
                if (onLoadMoreListener != null && !isLoading) {
                    isLoading = true;
                    onLoadMoreListener.onLoadMore();
                }
                holder.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {

                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        v.removeOnAttachStateChangeListener(this);
                    }
                });
                break;
            case BaseLoadMoreItem.STATE_NO_MORE:
                initNoMoreItemView(holder, item);
                break;
            case BaseLoadMoreItem.STATE_LOAD_FAIL:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getOnLoadMoreListener() != null) {
                            item.setState(BaseLoadMoreItem.STATE_LOAD_MORE);
                            initLoadMoreItemView(holder, item);
                            getOnLoadMoreListener().onLoadMore();
                        }
                    }
                });
                initLoadFailItemView(holder, item);
                break;
        }
    }

    protected abstract void initLoadMoreItemView(VH holder, T item);

    protected abstract void initNoMoreItemView(VH holder, T item);

    protected abstract void initLoadFailItemView(VH holder, T item);
}
