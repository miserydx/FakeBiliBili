package com.common.widget.adapter.binder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.R;
import com.common.widget.material.MaterialLoadingView;
import com.common.widget.adapter.BaseLoadMoreBinder;
import com.common.widget.adapter.BaseViewHolder;

/**
 * Created by miserydx on 17/7/2.
 */

public class DefaultLoadMoreBinder extends BaseLoadMoreBinder<DefaultLoadMoreBinder.LoadMoreHolder> {

    @NonNull
    @Override
    protected LoadMoreHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.common_item_load_more, parent, false);
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

    static class LoadMoreHolder extends BaseViewHolder {

        MaterialLoadingView loadingView;

        TextView tvDesc;

        private LoadMoreHolder(View itemView) {
            super(itemView);
            loadingView = itemView.findViewById(R.id.loading_view);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
