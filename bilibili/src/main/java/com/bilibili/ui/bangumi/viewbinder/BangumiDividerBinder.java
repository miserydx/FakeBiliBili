package com.bilibili.ui.bangumi.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/18.
 */

public class BangumiDividerBinder extends ItemViewBinder<BangumiDividerBinder.BangumiDivider, BangumiDividerBinder.BangumiDividerHolder> {

    @NonNull
    @Override
    protected BangumiDividerHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_divider, null);
        itemView.setTag(new BangumiDivider());
        return new BangumiDividerHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiDividerHolder holder, @NonNull BangumiDivider item) {

    }

    static class BangumiDividerHolder extends RecyclerView.ViewHolder {

        private BangumiDividerHolder(View itemView) {
            super(itemView);
        }
    }

    public static class BangumiDivider {

    }

}
