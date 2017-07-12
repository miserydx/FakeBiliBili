package com.bilibili.ui.region.viewbinder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.region.AppRegionShow;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.StringUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionBodyItemViewBinder extends ItemViewBinder<AppRegionShow.Body, RegionBodyItemViewBinder.RegionBodyViewHolder> {

    @NonNull
    @Override
    protected RegionBodyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_region_body, parent, false);
        return new RegionBodyViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RegionBodyViewHolder holder, @NonNull AppRegionShow.Body item) {
        Context context = holder.ivCover.getContext();
        int width = ScreenUtil.getScreenWidth(context) / 2 - SystemUtil.dp2px(context, 24);
        int height = context.getResources().getDimensionPixelSize(R.dimen.recommend_cover_height);
        ImageUtil.load(holder.ivCover, item.getCover(), width, height);
        holder.tvAreaTitle.setText(item.getTitle());
        holder.tvPlay.setText(StringUtil.numberToWord(item.getPlay()));
        String favourite = StringUtil.numberToWord(item.getFavourite());
        if (!TextUtils.equals("0", favourite)) {
            holder.tvDanmaku.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_promo_index_follow, 0, 0, 0);
            holder.tvDanmaku.setText(favourite);
        } else {
            holder.tvDanmaku.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bangumi_common_ic_video_danmakus, 0, 0, 0);
            holder.tvDanmaku.setText(StringUtil.numberToWord(item.getDanmaku()));
        }
        holder.tvDanmaku.setCompoundDrawablePadding(SystemUtil.dp2px(holder.tvDanmaku.getContext(), 2));
        holder.tvDanmaku.setCompoundDrawableTintList(ColorStateList.valueOf(0xffa5a5a5));
    }

    static class RegionBodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.tv_play)
        TextView tvPlay;
        @BindView(R.id.tv_danmaku)
        TextView tvDanmaku;

        public RegionBodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
