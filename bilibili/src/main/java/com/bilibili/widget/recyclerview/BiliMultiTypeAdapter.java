package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.bilibili.widget.recyclerview.BaseFooterItem.STATE_LOAD_FAIL;
import static com.bilibili.widget.recyclerview.BaseFooterItem.STATE_NO_MORE;


/**
 * Created by miserydx on 17/7/14.
 */

public class BiliMultiTypeAdapter extends MultiTypeAdapter {

    private static final String TAG = BiliMultiTypeAdapter.class.getSimpleName();

    public static final int LOAD_MORE_MODE_LAST_ITEM = 0;
    public static final int LOAD_MORE_MODE_BOTTOM = 1;

    private List items;
    private boolean showFooterItem = false;
    private OnFooterViewVisibleChangedListener OnFooterViewVisibleChangedListener;
    private BaseFooterItem footerItem;
    private DefaultLoadMoreBinder defaultLoadMoreBinder;
    private BiliOnScrollListenerProxy biliOnScrollListenerProxy;

    public BiliMultiTypeAdapter() {
        super();
        footerItem = new BaseFooterItem();
        footerItem.setState(BaseFooterItem.STATE_LOAD_MORE);
        defaultLoadMoreBinder = new DefaultLoadMoreBinder();
        register(BaseFooterItem.class, defaultLoadMoreBinder);
        biliOnScrollListenerProxy = new BiliOnScrollListenerProxy();
    }

    /**
     * 通知加载更多状态完成
     */
    public void setLoadMoreFinished(){
        defaultLoadMoreBinder.setLoadingFinished();
        biliOnScrollListenerProxy.setLoadingFinished();
    }

    /**
     * 设置列表数据集合
     *
     * @param items
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setItems(@NonNull List<?> items) {
        items.remove(footerItem);
        this.items = items;
        if (showFooterItem) {
            this.items.add(footerItem);
        }
        super.setItems(items);
    }

    /**
     * 设置是否显示FooterItem，默认不显示
     *
     * @param flag 是否显示FooterItem
     */
    public void setFooterItemEnabled(boolean flag) {
        showFooterItem = flag;
    }

    /**
     * 设置加载更多监听事件
     *
     * @param listener
     * @param mode 分为上拉到底加载和上拉到最后一项出现时加载
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener, int mode) {
        switch (mode) {
            case LOAD_MORE_MODE_BOTTOM://上拉到底加载
                biliOnScrollListenerProxy.setOnLoadMoreListener(listener);
                break;
            case LOAD_MORE_MODE_LAST_ITEM://上拉到最后一项出现时加载
                defaultLoadMoreBinder.setOnLoadMoreBinder(listener);
                break;
        }
    }

    /**
     * 设置默认footer显示没有更多
     */
    public void showNoMore() {
        changeLoadMoreState(STATE_NO_MORE);
    }

    /**
     * 设置默认footer显示加载失败
     */
    public void showFailToLoad() {
        changeLoadMoreState(STATE_LOAD_FAIL);
    }

    private void changeLoadMoreState(int state) {
        if (footerItem instanceof BaseFooterItem) {
            (footerItem).setState(state);
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
    public <T extends BaseFooterItem> void registerCustomFooterItem(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        try {
            footerItem = clazz.newInstance();
            register(clazz, binder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过adapter为recyclerview设置滑动监听
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        biliOnScrollListenerProxy.setTarget(onScrollListener);
    }

    /**
     * 设置是否滑动中停止加载图片，默认为false
     *
     * @param flag 是否滑动中加载图片
     */
    public void setScrollSaveStrategyEnabled(boolean flag) {
        biliOnScrollListenerProxy.setScrollSaveStrategyEnabled(flag);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (OnFooterViewVisibleChangedListener != null) {
            if (holder instanceof DefaultLoadMoreBinder.LoadMoreHolder) {
                OnFooterViewVisibleChangedListener.attachedToWindow((DefaultLoadMoreBinder.LoadMoreHolder) holder);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (OnFooterViewVisibleChangedListener != null) {
            if (holder instanceof DefaultLoadMoreBinder.LoadMoreHolder) {
                OnFooterViewVisibleChangedListener.detachedFromWindow((DefaultLoadMoreBinder.LoadMoreHolder) holder);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(biliOnScrollListenerProxy);
    }

    public void setOnFooterViewVisibleChangedListener(OnFooterViewVisibleChangedListener listener) {
        this.OnFooterViewVisibleChangedListener = listener;
    }

    public interface OnFooterViewVisibleChangedListener {
        void attachedToWindow(DefaultLoadMoreBinder.LoadMoreHolder holder);

        void detachedFromWindow(DefaultLoadMoreBinder.LoadMoreHolder holder);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
