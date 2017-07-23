package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import me.drakeet.multitype.ItemViewBinder;


/**
 * Created by Android_ZzT on 17/7/23.
 */

public abstract class BaseFooterViewBinder<T extends BaseFooterItem, VH extends RecyclerView.ViewHolder> extends ItemViewBinder<T, VH> {


    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull T item) {
        switch (item.getState()) {
            case BaseFooterItem.STATE_NORMAL:
                initNormalItemView(holder, item);
                break;
            case BaseFooterItem.STATE_LOAD_MORE:
                initLoadMoreItemView(holder, item);
                break;
            case BaseFooterItem.STATE_NO_MORE:
                initNoMoreItemView(holder, item);
                break;
            case BaseFooterItem.STATE_LOAD_FAIL:
                initLoadFailItemView(holder, item);
                break;
        }
    }

    //不需要加载更多功能的 Footer 实现这个方法
    abstract void initNormalItemView(VH holder, T item);

    abstract void initLoadMoreItemView(VH holder, T item);

    abstract void initNoMoreItemView(VH holder, T item);

    abstract void initLoadFailItemView(VH holder, T item);
}
