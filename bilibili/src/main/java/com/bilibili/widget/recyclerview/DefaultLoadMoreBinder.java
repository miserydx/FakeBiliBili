package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.common.widget.MaterialLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miserydx on 17/7/2.
 */

public class DefaultLoadMoreBinder extends BaseFooterViewBinder<BaseFooterItem, DefaultLoadMoreBinder.LoadMoreHolder> {

    private boolean isLoading = false;
    private BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener;

    public void setLoadingFinished() {
        isLoading = false;
    }

    @NonNull
    @Override
    protected LoadMoreHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_load_more, null);
        return new LoadMoreHolder(itemView);
    }

    @Override
    void initNormalItemView(LoadMoreHolder holder, BaseFooterItem item) {

    }

    @Override
    void initLoadMoreItemView(LoadMoreHolder holder, final BaseFooterItem item) {
        holder.tvDesc.setText(R.string.charge_loading);
        ((BiliMultiTypeAdapter) getAdapter()).setOnFooterViewVisibleChangedListener(new BiliMultiTypeAdapter.OnFooterViewVisibleChangedListener() {
            @Override
            public void attachedToWindow(LoadMoreHolder holder) {
                if (item.getState() == BaseFooterItem.STATE_LOAD_MORE) {
                    if (onLoadMoreListener != null && isLoading) {
                        isLoading = true;
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void detachedFromWindow(LoadMoreHolder holder) {
//                item.setState(BaseFooterItem.STATE_LOAD_MORE);
            }
        });
    }

    @Override
    void initNoMoreItemView(LoadMoreHolder holder, BaseFooterItem item) {
        holder.tvDesc.setText(R.string.charge_no_data_tips);
    }

    @Override
    void initLoadFailItemView(final LoadMoreHolder holder, BaseFooterItem item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadMoreListener != null) {
                    holder.tvDesc.setText(R.string.charge_loading);
                    onLoadMoreListener.onLoadMore();
                }
            }
        });
        holder.tvDesc.setText(R.string.tips_load_error);
    }

    public void setOnLoadMoreBinder(BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    static class LoadMoreHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loading_view)
        MaterialLoadingView loadingView;
        @BindView(R.id.desc_tv)
        TextView tvDesc;

        private LoadMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
