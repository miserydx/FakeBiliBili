package com.bilibili.ui.live.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveAllList;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/26.
 */

public class RecommendBannerItemViewBinder extends ItemViewBinder<LiveAllList.Recommend_data.Banner_data, RecommendBannerItemViewBinder.LiveViewHolder> {

    @NonNull
    @Override
    protected LiveViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_recommend_banner, parent, false);
        return new LiveViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final LiveViewHolder holder, @NonNull final LiveAllList.Recommend_data.Banner_data item) {
        final Context context = holder.cvContainer.getContext();
        int coverWidth = ScreenUtil.getScreenWidth(context) / 2 - SystemUtil.dp2px(context, 18);
        int coverHeight = context.getResources().getDimensionPixelSize(R.dimen.live_card_image_height);
        ImageUtil.load(holder.ivCover, item.getCover().getSrc(), coverWidth, coverHeight);
        holder.tvTitle.setText(item.getTitle());
    }

    static class LiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_container)
        CardView cvContainer;
        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public LiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
