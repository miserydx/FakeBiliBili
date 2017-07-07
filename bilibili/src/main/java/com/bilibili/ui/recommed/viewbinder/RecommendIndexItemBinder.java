package com.bilibili.ui.recommed.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.AppIndex;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.common.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/6.
 */

public class RecommendIndexItemBinder extends ItemViewBinder<AppIndex, RecommendIndexItemBinder.RecommendIndexItemHolder> {

    @NonNull
    @Override
    protected RecommendIndexItemHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_recommend_index_item, null);
        return new RecommendIndexItemHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final RecommendIndexItemHolder holder, @NonNull AppIndex item) {
        Glide.with(holder.ivCover.getContext())
                .load(item.getCover())
                .placeholder(R.drawable.bili_default_image_tv)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCover);
        holder.tvPlay.setText(StringUtil.numberToWord(item.getPlay()));
        holder.tvReply.setText(StringUtil.numberToWord(item.getReply()));
        holder.tvDuration.setText(StringUtil.secToTime(item.getDuration()));
        holder.tvTitle.setText(item.getTitle());
        holder.tvTName.setText(item.getTname());
    }

    static class RecommendIndexItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_iv)
        ImageView ivCover;
        @BindView(R.id.play_tv)
        TextView tvPlay;
        @BindView(R.id.reply_tv)
        TextView tvReply;
        @BindView(R.id.duration_tv)
        TextView tvDuration;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.t_name_tv)
        TextView tvTName;

        private RecommendIndexItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
