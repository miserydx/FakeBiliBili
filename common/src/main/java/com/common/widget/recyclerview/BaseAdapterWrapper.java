package com.common.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;

import com.common.widget.recyclerview.base.BaseLoadFailedBinder;
import com.common.widget.recyclerview.base.BaseLoadMoreBinder;
import com.common.widget.recyclerview.base.BaseLoadingBinder;
import com.common.widget.recyclerview.base.BaseViewHolder;
import com.common.widget.recyclerview.binder.DefaultLoadFailedBinder;
import com.common.widget.recyclerview.binder.DefaultLoadMoreBinder;
import com.common.widget.recyclerview.binder.DefaultLoadingBinder;
import com.common.widget.recyclerview.listener.CommonOnScrollListener;
import com.common.widget.recyclerview.base.BaseLoadFailedItem;
import com.common.widget.recyclerview.base.BaseLoadMoreItem;
import com.common.widget.recyclerview.base.BaseLoadingItem;
import com.common.widget.recyclerview.item.DefaultLoadFailedItem;
import com.common.widget.recyclerview.item.DefaultLoadMoreItem;
import com.common.widget.recyclerview.item.DefaultLoadingItem;


/**
 * 实现加载更多、加载失败、Loading功能的AdapterWrapper
 * Created by miserydx on 18/1/17.
 */
public class BaseAdapterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = BaseAdapterWrapper.class.getSimpleName();

    private static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 1;

    private static final int ITEM_TYPE_LOADING = Integer.MAX_VALUE - 2;

    private static final int ITEM_TYPE_LOAD_FAILED = Integer.MAX_VALUE - 3;

    private static final int STATE_DEFAULT = 0x00000000;

    private static final int STATE_LOADING = 0x00000001;

    private static final int STATE_LOAD_FAILED = 0x00000002;

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
     * 是否启用加载更多
     */
    private boolean loadMoreEnabled = true;

    /**
     * 当前状态
     */
    private int state = STATE_DEFAULT;

    /**
     * 滑动事件处理
     */
    private CommonOnScrollListener commonOnScrollListener;

    protected T targetAdapter;

    public BaseAdapterWrapper(T adapter) {
        targetAdapter = adapter;
        commonOnScrollListener = new CommonOnScrollListener();
    }

    @Override
    public int getItemViewType(int position) {
        if (state == STATE_LOADING) {
            return ITEM_TYPE_LOADING;
        } else if (state == STATE_LOAD_FAILED) {
            return ITEM_TYPE_LOAD_FAILED;
        } else if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return targetAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        if (state == STATE_LOADING || state == STATE_LOAD_FAILED) {
            return super.getItemId(position);
        } else if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return targetAdapter.getItemId(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_LOAD_MORE:
                return loadMoreBinder.onCreateVH(parent);
            case ITEM_TYPE_LOADING:
                return loadingBinder.onCreateVH(parent);
            case ITEM_TYPE_LOAD_FAILED:
                return loadFailedBinder.onCreateVH(parent);
            default:
                return targetAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_LOAD_MORE:
                loadMoreBinder.onBindVH((BaseViewHolder) holder, loadMoreItem);
                break;
            case ITEM_TYPE_LOADING:
                loadingBinder.onBindVH((BaseViewHolder) holder, loadingItem);
                break;
            case ITEM_TYPE_LOAD_FAILED:
                loadFailedBinder.onBindVH((BaseViewHolder) holder, loadFailedItem);
                break;
            default:
                targetAdapter.onBindViewHolder(holder, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (targetAdapter.getItemCount() == 0
                && (state == STATE_LOADING || state == STATE_LOAD_FAILED)) {
            return 1;
        } else if (state == STATE_LOADING || state == STATE_LOAD_FAILED) {
            Log.d(TAG, "the method getItemCount() not equal to 0 so it can not showLoading or loadFailed");
        }
        state = STATE_DEFAULT;
        return targetAdapter.getItemCount() == 0 ? 0 : targetAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (isTargetHolder(holder)) {
            targetAdapter.onViewRecycled(holder);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        if (isTargetHolder(holder)) {
            return targetAdapter.onFailedToRecycleView(holder);
        }
        return super.onFailedToRecycleView(holder);
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
                    if (getItemViewType(position) == ITEM_TYPE_LOAD_MORE
                            || getItemViewType(position) == ITEM_TYPE_LOADING
                            || getItemViewType(position) == ITEM_TYPE_LOAD_FAILED) {
                        return layoutManager.getSpanCount();
                    } else {
                        return oldSpanSizeLookup.getSpanSize(position);
                    }
                }
            });
        }
        targetAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(commonOnScrollListener);
        targetAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (isTargetHolder(holder)) {
            targetAdapter.onViewAttachedToWindow(holder);
        } else {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (isTargetHolder(holder)) {
            targetAdapter.onViewDetachedFromWindow(holder);
        }
    }

    public void setTargetAdapter(T targetAdapter) {
        this.targetAdapter = targetAdapter;
    }

    public T getTargetAdapter() {
        return targetAdapter;
    }

    public void setOnLoadMoreListener(BaseAdapterWrapper.OnLoadMoreListener listener) {
        if (listener != null) {
            loadMoreBinder.setOnLoadMoreListener(listener);
        }
    }

    /**
     * 通知加载更多状态完成
     */
    public void setLoadMoreFinished() {
        if (loadMoreBinder != null) {
            loadMoreBinder.setLoadMoreFinished();
            notifyDataSetChanged();
        }
    }

    /**
     * 设置是否启用加载更多
     */
    public void setLoadMoreEnabled(boolean flag) {
        loadMoreEnabled = flag;
        if (loadMoreEnabled) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }


    /**
     * 设置LoadMoreItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadMoreItem(@NonNull BaseLoadMoreItem item, @NonNull BaseLoadMoreBinder binder) {
        loadMoreItem = item;
        loadMoreBinder = binder;
    }

    /**
     * 设置LoadFailedItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadFailedItem(@NonNull BaseLoadFailedItem item, @NonNull BaseLoadFailedBinder binder) {
        loadFailedItem = item;
        loadFailedBinder = binder;
    }

    /**
     * 设置LoadingItem，默认不显示
     */
    @SuppressWarnings("unchecked")
    public void setLoadingItem(@NonNull BaseLoadingItem item, @NonNull BaseLoadingBinder binder) {
        loadingItem = item;
        loadingBinder = binder;
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
        setLoadFailedItem(new DefaultLoadFailedItem(), new DefaultLoadFailedBinder());
    }

    /**
     * 显示加载失败
     * 当目标Adapter的getItemCount返回0时此方法才会有效
     */
    @SuppressWarnings("unchecked")
    public void showLoadFailed() {
        if (loadFailedBinder != null && loadFailedItem != null) {
            state = STATE_LOAD_FAILED;
            notifyDataSetChanged();
        } else {
            Log.d(TAG, "loadFailedItem has not been initialized");
        }
    }

    /**
     * 显示Loading
     * 当目标Adapter的getItemCount返回0时此方法才会有效
     */
    @SuppressWarnings("unchecked")
    public void showLoading() {
        if (loadingBinder != null && loadingItem != null) {
            state = STATE_LOADING;
            notifyDataSetChanged();
        } else {
            Log.d(TAG, "loadingItem has not been initialized");
        }
    }

    public boolean isLoading() {
        return state == STATE_LOADING;
    }

    public boolean isLoadFailed() {
        return state == STATE_LOAD_FAILED;
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
            Log.d(TAG, "loadMoreItem has not been initialized");
        }
    }

    private boolean hasLoadMore() {
        return loadMoreEnabled && (loadMoreItem != null || loadMoreBinder != null);
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position == targetAdapter.getItemCount());
    }

    private boolean isTargetHolder(RecyclerView.ViewHolder holder) {
        return !(holder instanceof BaseViewHolder);
    }

    /**
     * 定义加载更多的回调接口
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
