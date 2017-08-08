package com.bilibili.ui.bangumi.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.BangumiIndexFall;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/6/30.
 */

public class BangumiIndexFallBinder extends ItemViewBinder<BangumiIndexFall, BangumiIndexFallBinder.BangumiIndexFallHolder> {

    @NonNull
    @Override
    protected BangumiIndexFallHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_index_foot, null);
        return new BangumiIndexFallHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiIndexFallHolder holder, @NonNull BangumiIndexFall item) {
        Context context = holder.ivCover.getContext();
        int width = ScreenUtil.getScreenWidth(context) - 24;
        int height = context.getResources().getDimensionPixelSize(R.dimen.bangumi_card_image_height);
        ImageUtil.load(holder.ivCover, item.getCover(), width, height);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDesc());
    }

    static class BangumiIndexFallHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_iv)
        SimpleDraweeView ivCover;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.desc_tv)
        TextView tvDesc;

        private BangumiIndexFallHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
