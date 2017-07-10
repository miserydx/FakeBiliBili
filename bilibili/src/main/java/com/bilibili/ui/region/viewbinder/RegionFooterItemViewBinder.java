package com.bilibili.ui.region.viewbinder;

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
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionFooterItemViewBinder extends ItemViewBinder<RegionFooterItemViewBinder.RegionFooter, RegionFooterItemViewBinder.RegionFooterViewHolder> {

    @NonNull
    @Override
    protected RegionFooterViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_more, parent, false);
        return new RegionFooterViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RegionFooterViewHolder holder, @NonNull RegionFooter item) {
        String region = holder.tvMore.getContext().getString(R.string.more_format, item.getRegion());
        holder.tvMore.setText(region);
    }

    static class RegionFooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_more)
        TextView tvMore;

        public RegionFooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class RegionFooter {

        private String region;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
