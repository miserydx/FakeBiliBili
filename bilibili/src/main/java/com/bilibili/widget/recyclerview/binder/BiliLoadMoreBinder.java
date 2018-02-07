package com.bilibili.widget.recyclerview.binder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.common.widget.material.MaterialLoadingView;
import com.common.widget.adapter.base.BaseLoadMoreBinder;
import com.common.widget.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miserydx on 17/7/2.
 */

public class BiliLoadMoreBinder extends BaseLoadMoreBinder<BiliLoadMoreBinder.LoadMoreHolder> {

    @NonNull
    @Override
    protected LoadMoreHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_load_more, parent, false);
        return new LoadMoreHolder(itemView);
    }

    @Override
    protected void onLoadMore(LoadMoreHolder holder) {
        holder.tvDesc.setText(R.string.charge_loading);
        holder.loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNoMore(LoadMoreHolder holder) {
        holder.tvDesc.setText(R.string.charge_no_data_tips);
        holder.loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void onLoadFailed(final LoadMoreHolder holder) {
        holder.tvDesc.setText(R.string.upper_load_failed_with_click);
        holder.loadingView.setVisibility(View.GONE);
    }

    public static class LoadMoreHolder extends BaseViewHolder {

        @BindView(R.id.loading_view)
        MaterialLoadingView loadingView;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        private LoadMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
