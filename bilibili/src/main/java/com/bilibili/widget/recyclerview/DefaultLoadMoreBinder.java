package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.widget.material.MaterialProgressDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miserydx on 17/7/2.
 */

public class DefaultLoadMoreBinder extends BaseFooterViewBinder<BaseFooterItem, DefaultLoadMoreBinder.LoadMoreHolder> {

    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
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
        holder.ivIcon.setVisibility(View.VISIBLE);
        holder.tvDesc.setText(R.string.charge_loading);
        ((BiliMultiTypeAdapter) getAdapter()).setOnFooterViewVisibleChangedListener(new BiliMultiTypeAdapter.OnFooterViewVisibleChangedListener() {
            @Override
            public void attachedToWindow(LoadMoreHolder holder) {
                if (item.getState() == BaseFooterItem.STATE_LOAD_MORE) {
                    ((MaterialProgressDrawable) holder.ivIcon.getDrawable()).start();
                    if (onLoadMoreListener != null && isLoading) {
                        isLoading = true;
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void detachedFromWindow(LoadMoreHolder holder) {
//                item.setState(BaseFooterItem.STATE_LOAD_MORE);
                ((MaterialProgressDrawable) holder.ivIcon.getDrawable()).stop();
            }
        });
    }

    @Override
    void initNoMoreItemView(LoadMoreHolder holder, BaseFooterItem item) {
        holder.ivIcon.setVisibility(View.GONE);
        holder.tvDesc.setText(R.string.charge_no_data_tips);
    }

    @Override
    void initLoadFailItemView(final LoadMoreHolder holder, BaseFooterItem item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadMoreListener != null) {
                    holder.ivIcon.setVisibility(View.VISIBLE);
                    holder.tvDesc.setText(R.string.charge_loading);
                    ((MaterialProgressDrawable) holder.ivIcon.getDrawable()).start();
                    onLoadMoreListener.onLoadMore();
                }
            }
        });
        holder.ivIcon.setVisibility(View.GONE);
        holder.tvDesc.setText(R.string.tips_load_error);
    }

    public void setOnLoadMoreBinder(BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    static class LoadMoreHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_iv)
        ImageView ivIcon;
        @BindView(R.id.desc_tv)
        TextView tvDesc;

        private LoadMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MaterialProgressDrawable progress = new MaterialProgressDrawable(ivIcon.getContext(), ivIcon);
            progress.setBackgroundColor(CIRCLE_BG_LIGHT);
            progress.setColorSchemeColors(ContextCompat.getColor(ivIcon.getContext(), R.color._progress__pink));
            progress.updateSizes(MaterialProgressDrawable.LARGE);
            ivIcon.setImageDrawable(progress);
            progress.showArrow(false);
            progress.setAlpha(255);
        }
    }
}
