package com.common.widget.recyclerview.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.R;
import com.common.widget.recyclerview.base.BaseLoadFailedBinder;
import com.common.widget.recyclerview.base.BaseViewHolder;
import com.common.widget.recyclerview.item.DefaultLoadFailedItem;

/**
 * Created by miserydx on 17/12/20.
 */

public class DefaultLoadFailedBinder extends BaseLoadFailedBinder<DefaultLoadFailedItem, DefaultLoadFailedBinder.LoadFailedHolder> {

    @NonNull
    @Override
    protected LoadFailedHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_load_failed, parent, false);
        return new LoadFailedHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LoadFailedHolder holder, @NonNull DefaultLoadFailedItem item) {
        holder.tvLoadFailed.setText(holder.tvLoadFailed.getContext().getText(R.string.load_failed));
    }

    class LoadFailedHolder extends BaseViewHolder {

        TextView tvLoadFailed;

        public LoadFailedHolder(View itemView) {
            super(itemView);
            tvLoadFailed = itemView.findViewById(R.id.tv_load_failed);
        }
    }

}
