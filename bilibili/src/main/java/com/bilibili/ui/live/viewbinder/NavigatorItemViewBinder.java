package com.bilibili.ui.live.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/26.
 */

public class NavigatorItemViewBinder extends ItemViewBinder<NavigatorItemViewBinder.NavigatorItem, NavigatorItemViewBinder.NavigatorViewHolder> {

    @NonNull
    @Override
    protected NavigatorViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_navigation, parent, false);
        return new NavigatorViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull NavigatorViewHolder holder, @NonNull NavigatorItem item) {

    }

    static class NavigatorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_live_follow)
        TextView tvFollow;
        @BindView(R.id.tv_live_center)
        TextView tvCenter;
        @BindView(R.id.tv_live_search)
        TextView tvSearch;
        @BindView(R.id.tv_live_video)
        TextView tvVideo;
        @BindView(R.id.tv_live_category)
        TextView tvCategory;

        public NavigatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class NavigatorItem {
    }
}
