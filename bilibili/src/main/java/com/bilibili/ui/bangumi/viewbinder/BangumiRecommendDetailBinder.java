package com.bilibili.ui.bangumi.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.StringUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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
        View itemView = inflater.inflate(R.layout.item_bangumi_recommend_detail, parent, false);
        return new BangumiRecommendDetailHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BangumiRecommendDetailHolder holder, @NonNull final BangumiIndexPage.Recommend item) {
        Context context = holder.ivCover.getContext();
        int width = ScreenUtil.getScreenWidth(context) / 3 - SystemUtil.dp2px(context, 14);
        int height = width * 4 / 3;
        CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, height);
        holder.rlCover.setLayoutParams(params);
        ImageUtil.load(holder.ivCover, item.getCover(), width, height);
        holder.tvBadge.setVisibility(View.GONE);
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
        SimpleDraweeView ivCover;
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