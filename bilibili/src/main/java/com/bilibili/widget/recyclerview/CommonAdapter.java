package com.bilibili.widget.recyclerview;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;

import com.bilibili.R;
import com.bilibili.widget.recyclerview.binder.BiliLoadFailedBinder;
import com.bilibili.widget.recyclerview.binder.BiliLoadMoreBinder;
import com.bilibili.widget.recyclerview.item.LoadFailedItem;
import com.bilibili.widget.recyclerview.item.LoadMoreItem;
import com.bilibili.widget.recyclerview.listener.BiliOnScrollListener;
import com.common.widget.recyclerview.BaseAdapterWrapper;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.OneToManyFlow;
import me.drakeet.multitype.TypePool;

/**
 * 继承自BaseAdapterWrapper、内部封装了多类型Adapter — MultiTypeAdapter，重写了默认的加载更多，加载失败及其
 * 它对Adapter的通用处理
 * Created by miserydx on 18/1/17.
 */

public class CommonAdapter extends BaseAdapterWrapper<MultiTypeAdapter> {

    private BiliOnScrollListener biliOnScrollListener;

    public CommonAdapter() {
        super(new MultiTypeAdapter(new Items()));
        biliOnScrollListener = new BiliOnScrollListener();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(biliOnScrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(biliOnScrollListener);
    }

    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        targetAdapter.register(clazz,binder);
    }

    public @NonNull <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        return targetAdapter.register(clazz);
    }

    public void registerAll(@NonNull final TypePool pool) {
        targetAdapter.registerAll(pool);
    }

    public void setItems(List<?> items) {
        targetAdapter.setItems(items);
    }

    public @NonNull List<?> getItems() {
        return targetAdapter.getItems();
    }

    public void setTypePool(@NonNull TypePool typePool) {
        targetAdapter.setTypePool(typePool);
    }

    public @NonNull TypePool getTypePool() {
        return targetAdapter.getTypePool();
    }

    /**
     * 在item list尾部添加项
     *
     * @param item 项
     */
    public void addItem(@NonNull Object item) {
        int position = targetAdapter.getItemCount();
        addItem(item, position);
    }

    /**
     * 在item list尾部添加集合
     *
     * @param list 添加集合
     */
    public void addItems(@NonNull List<?> list) {
        int position = targetAdapter.getItemCount();
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
        items.add(item);
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
        notifyItemInserted(position);
    }

    @Override
    public void useDefaultLoadMore() {
        setLoadMoreItem(new LoadMoreItem(), new BiliLoadMoreBinder());
    }

    @Override
    public void useDefaultLoadFailed() {
        BiliLoadFailedBinder binder = new BiliLoadFailedBinder();
        binder.setResId(R.drawable.img_tips_error_load_error);
        binder.setStringId(R.string.tips_load_error);
        setLoadFailedItem(new LoadFailedItem(), binder);
    }

    public void setLoadFailedItem(@DrawableRes int resId, @StringRes int stringId) {
        BiliLoadFailedBinder binder = new BiliLoadFailedBinder();
        binder.setResId(resId);
        binder.setStringId(stringId);
        setLoadFailedItem(new LoadFailedItem(), binder);
    }

    /**
     * 设置是否滑动中停止加载图片，默认为false
     *
     * @param flag 是否滑动中加载图片
     */
    public void setScrollSaveStrategyEnabled(boolean flag) {
        biliOnScrollListener.setScrollSaveStrategyEnabled(flag);
    }
}
