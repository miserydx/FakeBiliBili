package com.bilibili.ui.bangumi.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.common.util.StringUtil;

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
        holder.rlCover.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = holder.rlCover.getWidth();
                CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, width * 4 / 3);
                holder.rlCover.setLayoutParams(params);
                holder.rlCover.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        Glide.with(holder.ivCover.getContext())
                .load(item.getCover())
                .placeholder(R.drawable.bili_default_image_tv)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCover);
        if (!TextUtils.isEmpty(item.getBadge())) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(item.getBadge());
        }
        String favouritesText = String.format(holder.tvNewest.getContext().getResources().getString(R.string.bangumi_index_text_suffix_hit), StringUtil.numberToWord(Integer.valueOf(item.getFavourites())));
        holder.tvFavourites.setText(favouritesText);
        holder.tvTitle.setText(item.getTitle());
        String newestText = String.format(holder.tvNewest.getContext().getResources().getString(R.string.bangumi_index_status_format_3), item.getNewest_ep_index());
        holder.tvNewest.setText(newestText);
    }

    static class BangumiRecommendDetailHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_rl)
        RelativeLayout rlCover;
        @BindView(R.id.cover_iv)
        ImageView ivCover;
        @BindView(R.id.badge_tv)
        TextView tvBadge;
        @BindView(R.id.favourites_tv)
        TextView tvFavourites;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.newest_tv)
        TextView tvNewest;

        private BangumiRecommendDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
