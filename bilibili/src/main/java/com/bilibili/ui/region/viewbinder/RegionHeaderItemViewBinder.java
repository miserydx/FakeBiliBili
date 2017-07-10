package com.bilibili.ui.region.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionHeaderItemViewBinder extends ItemViewBinder<RegionHeaderItemViewBinder.RegionHeader, RegionHeaderItemViewBinder.RegionHeaderViewHolder> {

    @NonNull
    @Override
    protected RegionHeaderViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_region_header, parent, false);
        return new RegionHeaderViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RegionHeaderViewHolder holder, @NonNull RegionHeader item) {

    }

    static class RegionHeaderViewHolder extends RecyclerView.ViewHolder {

        public RegionHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RegionHeader {

    }
}
