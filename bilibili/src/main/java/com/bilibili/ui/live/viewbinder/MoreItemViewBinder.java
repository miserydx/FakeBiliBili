package com.bilibili.ui.live.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/27.
 */

public class MoreItemViewBinder extends ItemViewBinder<MoreItemViewBinder.MoreItem, MoreItemViewBinder.MoreViewHolder> {

    @NonNull
    @Override
    protected MoreViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_more, null);
        return new MoreViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull MoreViewHolder holder, @NonNull MoreItem item) {

    }

    static class MoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_more)
        CardView btnMore;
        @BindView(R.id.iv_refresh)
        ImageView ivRefresh;
        @BindView(R.id.layout_refresh)
        View layoutRefresh;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class MoreItem {

    }
}
