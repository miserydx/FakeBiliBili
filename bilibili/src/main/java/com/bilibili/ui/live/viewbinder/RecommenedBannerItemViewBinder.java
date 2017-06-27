package com.bilibili.ui.live.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveRecommend;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/27.
 */

public class RecommenedBannerItemViewBinder extends ItemViewBinder<LiveRecommend.Recommend_data.Banner_data, RecommenedBannerItemViewBinder.BannerViewHolder> {

    @NonNull
    @Override
    protected BannerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_common, null);
        return new BannerViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BannerViewHolder holder, @NonNull LiveRecommend.Recommend_data.Banner_data item) {
        int height = item.getCover().getHeight();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height*3/2);
        holder.ivCover.setLayoutParams(params);
        holder.ivCover.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(holder.ivCover.getContext())
                .load(item.getCover().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(200)
                .into(holder.ivCover);
        holder.tvName.setText(item.getOwner().getName());
        holder.tvOnline.setText(String.valueOf(item.getOnline()));
        String tintArea = "<font color='#FF4081'>" + "#" + item.getArea() + "#&nbsp;" + "</font>";
        holder.tvAreaTitle.setText(Html.fromHtml(tintArea + item.getTitle()));
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.tv_online)
        TextView tvOnline;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
