package com.bilibili.widget.recyclerview.binder;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.widget.recyclerview.item.LoadFailedItem;
import com.common.widget.recyclerview.base.BaseLoadFailedBinder;
import com.common.widget.recyclerview.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miserydx on 17/12/20.
 */

public class BiliLoadFailedBinder extends BaseLoadFailedBinder<LoadFailedItem, BiliLoadFailedBinder.LoadFailedHolder> {

    private static final int NO_ID = -1;

    private int resId = NO_ID;

    private int stringId = NO_ID;

    public void setResId(@DrawableRes int resId) {
        this.resId = resId;
    }

    public void setStringId(@StringRes int stringId) {
        this.stringId = stringId;
    }

    @NonNull
    @Override
    protected LoadFailedHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_load_failed, parent, false);
        return new LoadFailedHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LoadFailedHolder holder, @NonNull LoadFailedItem item) {
        if (resId != NO_ID) {
            holder.ivLoadFailed.setImageResource(resId);
        }
        if (stringId != NO_ID) {
            holder.tvLoadFailed.setText(stringId);
        }
    }

    class LoadFailedHolder extends BaseViewHolder {

        @BindView(R.id.iv_load_failed)
        ImageView ivLoadFailed;
        @BindView(R.id.tv_load_failed)
        TextView tvLoadFailed;

        public LoadFailedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
