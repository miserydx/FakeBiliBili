package com.bilibili.ui.bangumi.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/2.
 */

public class BangumiHomeBinder extends ItemViewBinder<BangumiHomeBinder.BangumiHome, BangumiHomeBinder.BangumiHomeHolder> {

    @NonNull
    @Override
    protected BangumiHomeHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_home, parent, false);
        return new BangumiHomeHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiHomeHolder holder, @NonNull BangumiHome item) {

    }

    static class BangumiHomeHolder extends RecyclerView.ViewHolder {

        private BangumiHomeHolder(View itemView) {
            super(itemView);
        }

    }

    public static class BangumiHome {

    }
}