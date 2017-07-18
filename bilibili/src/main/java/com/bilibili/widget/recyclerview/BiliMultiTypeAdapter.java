package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by miserydx on 17/7/14.
 */

public class BiliMultiTypeAdapter extends MultiTypeAdapter {

    private static final String TAG = BiliMultiTypeAdapter.class.getSimpleName();

    private List items;
    private boolean showFooterItem = false;
    private OnFooterViewVisibleChangedListener OnFooterViewVisibleChangedListener;
    private Object footerItem;
    private LoadMoreBinder loadMoreBinder;

    public BiliMultiTypeAdapter() {
        super();
        footerItem = new LoadMoreBinder.LoadMore();
        loadMoreBinder = new LoadMoreBinder();
        register(LoadMoreBinder.LoadMore.class, loadMoreBinder);
    }

    /**
     * 设置是否显示FooterItem，默认不显示
     *
     * @param flag 是否显示FooterItem
     */
    public void setFooterItemEnabled(boolean flag) {
        showFooterItem = flag;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        loadMoreBinder.setOnLoadMoreBinder(listener);
    }

    /**
     * 设置默认footer显示没有更多
     */
    public void showNoMore() {
        changeLoadMoreState(LoadMoreBinder.LoadMore.STATE_NO_MORE);
    }

    /**
     * 设置默认fotter显示加载失败
     */
    public void showFailToLoad() {
        changeLoadMoreState(LoadMoreBinder.LoadMore.STATE_FAILED_TO_LOAD);
    }

    private void changeLoadMoreState(int state) {
        if (footerItem instanceof LoadMoreBinder.LoadMore) {
            ((LoadMoreBinder.LoadMore) footerItem).setState(state);
            notifyItemChanged(items.size() - 1);
        }
    }

    /**
     * 自定义FooterItem，默认为LoadMore
     *
     * @param clazz  item的class
     * @param binder item的viewbinder
     * @param <T>    item的class
     */
    public <T> void registerCustomFooterItem(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        try {
            footerItem = clazz.newInstance();
            register(clazz, binder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setItems(@NonNull List<?> items) {
        if (items.contains(footerItem)) {
            items.remove(items.indexOf(footerItem));
        }
        this.items = items;
        if (showFooterItem) {
            this.items.add(footerItem);
        }
        super.setItems(items);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (OnFooterViewVisibleChangedListener != null) {
            if (holder instanceof LoadMoreBinder.LoadMoreHolder) {
                OnFooterViewVisibleChangedListener.attachedToWindow((LoadMoreBinder.LoadMoreHolder) holder);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (OnFooterViewVisibleChangedListener != null) {
            if (holder instanceof LoadMoreBinder.LoadMoreHolder) {
                OnFooterViewVisibleChangedListener.detachedFromWindow((LoadMoreBinder.LoadMoreHolder) holder);
            }
        }
    }

    public void setOnFooterViewVisibleChangedListener(OnFooterViewVisibleChangedListener listener) {
        this.OnFooterViewVisibleChangedListener = listener;
    }

    public interface OnFooterViewVisibleChangedListener {
        void attachedToWindow(LoadMoreBinder.LoadMoreHolder holder);

        void detachedFromWindow(LoadMoreBinder.LoadMoreHolder holder);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
