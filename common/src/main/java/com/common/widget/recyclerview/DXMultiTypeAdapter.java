package com.common.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.common.widget.recyclerview.base.BaseLoadFailedBinder;
import com.common.widget.recyclerview.base.BaseLoadingBinder;
import com.common.widget.recyclerview.binder.DefaultLoadFailedBinder;
import com.common.widget.recyclerview.binder.DefaultLoadMoreBinder;
import com.common.widget.recyclerview.binder.DefaultLoadingBinder;
import com.common.widget.recyclerview.base.BaseLoadFailedItem;
import com.common.widget.recyclerview.base.BaseLoadMoreBinder;
import com.common.widget.recyclerview.base.BaseLoadMoreItem;
import com.common.widget.recyclerview.base.BaseLoadingItem;
import com.common.widget.recyclerview.item.DefaultLoadFailedItem;
import com.common.widget.recyclerview.item.DefaultLoadMoreItem;
import com.common.widget.recyclerview.item.DefaultLoadingItem;
import com.common.widget.recyclerview.listener.CommonOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypePool;
import me.drakeet.multitype.TypePool;

/**
 * 基于MultiTypeAdapter封装
 * Created by miserydx on 17/7/14.
 */

@Deprecated
public class DXMultiTypeAdapter extends MultiTypeAdapter {

    private static final String TAG = DXMultiTypeAdapter.class.getSimpleName();

    private static final int STATE_DEFAULT = 0x00000000;

    private static final int STATE_LOAD_MORE = 0x00000001;

    private static final int STATE_LOADING = 0x00000002;

    private static final int STATE_LOAD_FAILED = 0x00000003;

    private int state = STATE_DEFAULT;

    private boolean loadMoreEnabled;

    /**
     * 加载更多项
     */
    private BaseLoadMoreItem loadMoreItem;

    /**
     * 加载失败项
     */
    private BaseLoadFailedItem loadFailedItem;

    /**
     * Loading项
     */
    private BaseLoadingItem loadingItem;

    /**
     * 加载更多ItemViewBinder
     */
    private BaseLoadMoreBinder loadMoreBinder;

    /**
     * 加载失败ItemViewBinder
     */
    private BaseLoadFailedBinder loadFailedBinder;

    /**
     * Loading ItemViewBinder
     */
    private BaseLoadingBinder loadingBinder;

    /**
     * 滑动事件处理
     */
    private CommonOnScrollListener commonOnScrollListener;

    public DXMultiTypeAdapter() {
        this(new ArrayList());
    }

    public DXMultiTypeAdapter(@NonNull List<?> items) {
        this(items, new MultiTypePool());
    }

    public DXMultiTypeAdapter(@NonNull List<?> items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity));
    }

    public DXMultiTypeAdapter(@NonNull List<?> items, @NonNull TypePool pool) {
        super(new Items(items), pool);
        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        commonOnScrollListener = new CommonOnScrollListener();
    }

    @Override
    public final void setItems(@NonNull List<?> list) {
        Items items = new Items(list);
        if (hasLoadMore()) {
            items.add(loadMoreItem);
        }
        super.setItems(items);
        notifyDataSetChanged();
    }

    /**
     * 在item list尾部添加项
     *
     * @param item 项
     */
    public void addItem(@NonNull Object item) {
        int position;
        if (hasLoadMore() && getItems().contains(loadMoreItem)) {
            position = getItemCount() - 1;
        } else {
            position = getItemCount();
        }
        addItem(item, position);
    }

    /**
     * 在item list尾部添加集合
     *
     * @param list 添加集合
     */
    public void addItems(@NonNull List<?> list) {
        int position;
        if (hasLoadMore() && getItems().contains(loadMoreItem)) {
            position = getItemCount() - 1;
        } else {
            position = getItemCount();
        }
        addItems(list, position);
    }

    /**
     * 在item list中插入项
     *
     * @param item     项
     * @param position 插入位置
     */
    @SuppressWarnings("unchecked")
    public void addItem(@NonNull Object item, int position) {
        List items = getItems();
        items.add(position, item);
        if (hasLoadMore() && !items.contains(loadMoreItem)) {
            items.add(loadMoreItem);
        }
        notifyItemInserted(position);
    }

    /**
     * 在item list中插入集合
     *
     * @param list     插入的集合
     * @param position 插入位置
     */
    @SuppressWarnings("unchecked")
    public void addItems(@NonNull List<?> list, int position) {
        List items = getItems();
        items.addAll(position, list);
        if (hasLoadMore() && !items.contains(loadMoreItem)) {
            items.add(loadMoreItem);
        }
        notifyItemInserted(position);
    }

    public void clear() {
        getItems().clear();
    }

    /**
     * 获取数据集
     *
     * @return 返回纯净的数据集合
     */
    public final List<?> getDataList() {
        if (getItems().contains(loadMoreItem)) {
            return getItems().subList(0, getItemCount() - 1);
        } else if (isLoadFailed() || isLoading()) {
            return getItems().subList(0, 0);
        } else {
            return getItems();
        }
    }

    /**
     * 获取数据集Count
     *
     * @return 返回纯净的数据集合Count
     */
    public final int getDataCount() {
        return getDataList().size();
    }

    /**
     * 设置默认Loading
     */
    public void useDefaultLoading() {
        setLoadingItem(new DefaultLoadingItem(), new DefaultLoadingBinder());
    }

    /**
     * 设置默认加载更多
     */
    public void useDefaultLoadMore() {
        setLoadMoreItem(new DefaultLoadMoreItem(), new DefaultLoadMoreBinder());
    }

    /**
     * 设置默认加载失败
     */
    public void useDefaultLoadFailed() {
        DefaultLoadFailedBinder binder = new DefaultLoadFailedBinder();
        setLoadFailedItem(new DefaultLoadFailedItem(), binder);
    }

    public void setLoadMoreEnabled(boolean flag) {
        loadMoreEnabled = flag;
    }

    /**
     * 设置LoadMoreItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadMoreItem(@NonNull BaseLoadMoreItem item, @NonNull BaseLoadMoreBinder binder) {
        loadMoreItem = item;
        loadMoreBinder = binder;
        loadMoreItem.setState(BaseLoadMoreItem.STATE_LOAD_MORE);
        register(item.getClass(), binder);
    }

    /**
     * 设置LoadFailedItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadFailedItem(@NonNull BaseLoadFailedItem item, @NonNull BaseLoadFailedBinder binder) {
        loadFailedItem = item;
        loadFailedBinder = binder;
        register(item.getClass(), binder);
    }

    /**
     * 设置LoadingItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadingItem(@NonNull BaseLoadingItem item, @NonNull BaseLoadingBinder binder) {
        loadingItem = item;
        loadingBinder = binder;
        register(item.getClass(), binder);
    }

    /**
     * 通知加载更多状态完成
     */
    public void setLoadMoreFinished() {
        if (loadMoreBinder != null) {
            loadMoreBinder.setLoadMoreFinished();
        } else {
            Log.e(TAG, "DefaultLoadMoreItem has not been initialized!");
        }
    }

    /**
     * 显示加载失败
     */
    @SuppressWarnings("unchecked")
    public void showLoadFailed() {
        if (loadFailedBinder != null && loadFailedItem != null) {
            state = STATE_LOAD_FAILED;
            clear();
            addItem(loadFailedItem);
            notifyDataSetChanged();
        } else {
            Log.e(TAG, "DefaultLoadFailedItem has not been initialized!");
        }
    }

    /**
     * 显示Loading
     */
    @SuppressWarnings("unchecked")
    public void showLoading() {
        if (loadingBinder != null && loadingItem != null) {
            state = STATE_LOADING;
            clear();
            addItem(loadingItem);
            notifyDataSetChanged();
        } else {
            Log.e(TAG, "loadingItem has not been initialized!");
        }
    }

    /**
     * 设置默认footer显示没有更多
     */
    public void showNoMore() {
        changeLoadMoreState(BaseLoadMoreItem.STATE_NO_MORE);
    }

    /**
     * 设置默认footer显示加载失败
     */
    public void showFailToLoadMore() {
        changeLoadMoreState(BaseLoadMoreItem.STATE_LOAD_FAIL);
    }

    private void changeLoadMoreState(int state) {
        if (hasLoadMore()) {
            loadMoreItem.setState(state);
            notifyItemChanged(getItemCount() - 1);
        } else {
            Log.e(TAG, "DefaultLoadFailedItem has not been initialized!");
        }
    }

    private boolean hasLoadMore() {
        return loadMoreEnabled && (loadMoreItem != null && loadMoreBinder != null);
    }

    private boolean isLoadFailed(){
        return state == STATE_LOAD_FAILED;
    }

    private boolean isLoading(){
        return state == STATE_LOADING;
    }

    /**
     * 设置加载更多监听事件,需要setLoadMoreItem
     *
     * @param listener 监听器
     */
    public void setOnLoadMoreListener(BaseAdapterWrapper.OnLoadMoreListener listener) {
        if (loadMoreBinder != null) {
            loadMoreBinder.setOnLoadMoreListener(listener);
        } else {
            Log.e(TAG, "DefaultLoadMoreItem has not been initialized!");
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(commonOnScrollListener);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            final GridLayoutManager.SpanSizeLookup oldSpanSizeLookup = layoutManager.getSpanSizeLookup();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    Object o = getItems().get(position);
                    if (o instanceof BaseLoadMoreItem
                            || o instanceof BaseLoadingItem
                            || o instanceof BaseLoadFailedItem) {
                        return layoutManager.getSpanCount();
                    } else {
                        return oldSpanSizeLookup.getSpanSize(position);
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(commonOnScrollListener);
    }

}
