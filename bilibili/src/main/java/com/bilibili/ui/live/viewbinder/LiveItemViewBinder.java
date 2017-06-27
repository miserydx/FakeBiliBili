package com.bilibili.ui.live.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveCommon;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/26.
 */

public class LiveItemViewBinder extends ItemViewBinder<LiveCommon.Partitions.Lives, LiveItemViewBinder.LiveViewHolder> {

    @NonNull
    @Override
    protected LiveViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_common, null);
        return new LiveViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiveViewHolder holder, @NonNull LiveCommon.Partitions.Lives item) {
        int height = Integer.valueOf(item.getCover().getHeight());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * 3 / 2);
        holder.ivCover.setLayoutParams(params);
        holder.ivCover.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(holder.ivCover.getContext())
                .load(item.getCover().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(300)
                .into(holder.ivCover);

        holder.tvTitle.setText(item.getTitle());
        holder.tvOnline.setText(String.valueOf(item.getOnline()));
        holder.tvName.setText(item.getOwner().getName());
    }

    static class LiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_online)
        TextView tvOnline;

        public LiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
