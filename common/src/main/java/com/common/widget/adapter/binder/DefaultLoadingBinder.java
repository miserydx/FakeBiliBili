package com.common.widget.adapter.binder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.widget.material.MaterialLoadingView;
import com.common.widget.adapter.BaseLoadingBinder;
import com.common.widget.adapter.BaseViewHolder;

/**
 * Created by miserydx on 17/12/24.
 */

public class DefaultLoadingBinder extends BaseLoadingBinder<DefaultLoadingBinder.DefaultLoadingHolder> {

    @NonNull
    @Override
    protected DefaultLoadingHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.common_item_loading, parent, false);
        return new DefaultLoadingHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull DefaultLoadingHolder holder) {

    }

    class DefaultLoadingHolder extends BaseViewHolder {

        MaterialLoadingView loadingView;

        public DefaultLoadingHolder(View itemView) {
            super(itemView);
            loadingView = itemView.findViewById(R.id.loading_view);
        }
    }

}
