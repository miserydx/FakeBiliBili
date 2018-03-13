package com.bilibili.widget.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;

import com.bilibili.R;
import com.bilibili.widget.adapter.binder.BiliLoadFailedBinder;
import com.bilibili.widget.adapter.binder.BiliLoadMoreBinder;
import com.common.widget.adapter.DefaultAdapterWrapper;
import com.facebook.drawee.backends.pipeline.Fresco;

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

public class CommonAdapter extends DefaultAdapterWrapper<MultiTypeAdapter> {

    private boolean mSaveStrategyEnabled = false;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mSaveStrategyEnabled) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Fresco.getImagePipeline().pause();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                }
            }
        }
    };

    public CommonAdapter() {
        super(new MultiTypeAdapter(new Items()));
        useDefaultLoadMore();
        useDefaultLoadFailed();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(mOnScrollListener);
    }

    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        innerAdapter.register(clazz, binder);
    }

    public @NonNull
    <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        return innerAdapter.register(clazz);
    }

    public void registerAll(@NonNull final TypePool pool) {
        innerAdapter.registerAll(pool);
    }

    public void setItems(List<?> items) {
        innerAdapter.setItems(items);
    }

    public @NonNull
    List<?> getItems() {
        return innerAdapter.getItems();
    }

    public void setTypePool(@NonNull TypePool typePool) {
        innerAdapter.setTypePool(typePool);
    }

    public @NonNull
    TypePool getTypePool() {
        return innerAdapter.getTypePool();
    }

    /**
     * 在item list尾部添加项
     *
     * @param item 项
     */
    public void addItem(@NonNull Object item) {
        int position = innerAdapter.getItemCount();
        addItem(item, position);
    }

    /**
     * 在item list尾部添加集合
     *
     * @param list 添加集合
     */
    public void addItems(@NonNull List<?> list) {
        int position = innerAdapter.getItemCount();
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
        notifyItemRangeInserted(position, list.size());
    }

    public void useDefaultLoadMore() {
        setLoadMoreBinder(new BiliLoadMoreBinder());
    }

    public void useDefaultLoadFailed() {
        BiliLoadFailedBinder binder = new BiliLoadFailedBinder();
        binder.setResId(R.drawable.img_tips_error_load_error);
        binder.setStringId(R.string.tips_load_error);
        setLoadFailedBinder(binder);
    }

    public void useDefaultLoadFailed(@DrawableRes int resId, @StringRes int stringId) {
        BiliLoadFailedBinder binder = new BiliLoadFailedBinder();
        binder.setResId(resId);
        binder.setStringId(stringId);
        setLoadFailedBinder(binder);
    }

    /**
     * 设置是否滑动中停止加载图片，默认为false
     *
     * @param flag 是否滑动中加载图片
     */
    public void setScrollSaveStrategyEnabled(boolean flag) {
        mSaveStrategyEnabled = flag;
    }
}
