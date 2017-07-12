package com.bilibili.ui.live.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveRecommend;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.StringUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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
        Context context = holder.ivCover.getContext();
        int width = ScreenUtil.getScreenWidth(context);
        int height = SystemUtil.dp2px(context, 120);
        ImageUtil.load(holder.ivCover, item.getCover().getSrc(), width, height);
        holder.tvName.setText(item.getOwner().getName());
        holder.tvOnline.setText(StringUtil.numberToWord(item.getOnline()));
        String tintArea = "<font color='#FF4081'>" + "#" + item.getArea() + "#&nbsp;" + "</font>";
        holder.tvAreaTitle.setText(Html.fromHtml(tintArea + item.getTitle()));
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
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
