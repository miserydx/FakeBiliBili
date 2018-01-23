package com.common.widget.recyclerview.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.widget.material.MaterialLoadingView;
import com.common.widget.recyclerview.base.BaseLoadingBinder;
import com.common.widget.recyclerview.base.BaseViewHolder;
import com.common.widget.recyclerview.item.DefaultLoadingItem;

/**
 * Created by miserydx on 17/12/24.
 */

public class DefaultLoadingBinder extends BaseLoadingBinder<DefaultLoadingItem, DefaultLoadingBinder.DefaultLoadingHolder> {

    @NonNull
    @Override
    protected DefaultLoadingHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_loading, parent, false);
        return new DefaultLoadingHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull DefaultLoadingHolder holder, @NonNull DefaultLoadingItem item) {

    }

    class DefaultLoadingHolder extends BaseViewHolder {

        MaterialLoadingView loadingView;

        public DefaultLoadingHolder(View itemView) {
            super(itemView);
            loadingView = itemView.findViewById(R.id.loading_view);
        }
    }

}
