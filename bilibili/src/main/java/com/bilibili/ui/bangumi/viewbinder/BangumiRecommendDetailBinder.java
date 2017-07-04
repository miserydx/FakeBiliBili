package com.bilibili.ui.bangumi.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/6/30.
 */

public class BangumiRecommendDetailBinder extends ItemViewBinder<BangumiIndexPage.Recommend, BangumiRecommendDetailBinder.BangumiRecommendDetailHolder> {

    public BangumiRecommendDetailBinder() {

    }

    @NonNull
    @Override
    protected BangumiRecommendDetailHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_recommend_detail, null);
        return new BangumiRecommendDetailHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BangumiRecommendDetailHolder holder, @NonNull BangumiIndexPage.Recommend item) {
        holder.ivCover.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = holder.ivCover.getWidth();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width * 4 / 3);
                holder.ivCover.setLayoutParams(params);
            }
        });
        Glide.with(holder.ivCover.getContext())
                .load(item.getCover())
                .placeholder(R.drawable.bili_default_image_tv)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCover);
        holder.tvTitle.setText(item.getTitle());
        String newestText = String.format(holder.tvNewest.getContext().getResources().getString(R.string.bangumi_index_status_format_3), item.getNewest_ep_index());
        holder.tvNewest.setText(newestText);
    }

    static class BangumiRecommendDetailHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_iv)
        ImageView ivCover;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.newest_tv)
        TextView tvNewest;

        public BangumiRecommendDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
