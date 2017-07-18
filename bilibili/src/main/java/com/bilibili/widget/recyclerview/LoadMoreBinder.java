package com.bilibili.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.widget.material.MaterialProgressDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/2.
 */

public class LoadMoreBinder extends ItemViewBinder<LoadMoreBinder.LoadMore, LoadMoreBinder.LoadMoreHolder> {

    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener;

    @NonNull
    @Override
    protected LoadMoreHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_load_more, null);
        return new LoadMoreHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LoadMoreHolder holder, @NonNull final LoadMore item) {
        switch (item.getState()) {
            case LoadMore.STATE_LOAD_MORE:
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.tvDesc.setText(R.string.charge_loading);
                ((BiliMultiTypeAdapter) getAdapter()).setOnFooterViewVisibleChangedListener(new BiliMultiTypeAdapter.OnFooterViewVisibleChangedListener() {
                    @Override
                    public void attachedToWindow(LoadMoreHolder holder) {
                        if (item.getState() == LoadMore.STATE_LOAD_MORE) {
                            ((MaterialProgressDrawable) holder.ivIcon.getDrawable()).start();
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                    }

                    @Override
                    public void detachedFromWindow(LoadMoreHolder holder) {
                        item.setState(LoadMore.STATE_LOAD_MORE);
                        ((MaterialProgressDrawable) holder.ivIcon.getDrawable()).stop();
                    }
                });
                break;
            case LoadMore.STATE_NO_MORE:
                holder.ivIcon.setVisibility(View.GONE);
                holder.tvDesc.setText(R.string.charge_no_data_tips);
                break;
            case LoadMore.STATE_FAILED_TO_LOAD:
                holder.ivIcon.setVisibility(View.GONE);
                holder.tvDesc.setText(R.string.tips_load_error);
                break;
        }

    }

    public void setOnLoadMoreBinder(BiliMultiTypeAdapter.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    static class LoadMoreHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_iv)
        ImageView ivIcon;
        @BindView(R.id.desc_tv)
        TextView tvDesc;

        private LoadMoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MaterialProgressDrawable progress = new MaterialProgressDrawable(ivIcon.getContext(), ivIcon);
            progress.setBackgroundColor(CIRCLE_BG_LIGHT);
            progress.setColorSchemeColors(ContextCompat.getColor(ivIcon.getContext(), R.color._progress__pink));
            progress.updateSizes(MaterialProgressDrawable.LARGE);
            ivIcon.setImageDrawable(progress);
            progress.showArrow(false);
            progress.setAlpha(255);
        }

    }

    public static class LoadMore {

        public static final int STATE_LOAD_MORE = 0;
        public static final int STATE_NO_MORE = 1;
        public static final int STATE_FAILED_TO_LOAD = 3;

        public int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
