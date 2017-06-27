package com.bilibili.ui.live.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/27.
 */

public class FooterItemViewBinder extends ItemViewBinder<FooterItemViewBinder.FooterItem, FooterItemViewBinder.FooterViewHolder> {

    @NonNull
    @Override
    protected FooterViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_footer, parent, false);
        return new FooterViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull FooterViewHolder holder, @NonNull FooterItem item) {

    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterItem {

    }
}
