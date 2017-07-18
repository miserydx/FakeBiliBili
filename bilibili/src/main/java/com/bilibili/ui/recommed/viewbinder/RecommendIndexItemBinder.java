package com.bilibili.ui.recommed.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.recommend.AppIndex;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.StringUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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
        if ("login".equals(item.getGoto())) {
            holder.flLoginCover.setVisibility(View.VISIBLE);
            if ("2".equals(item.getParam())) {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.getContext(), R.drawable.ic_promo_index_sign2_v2));
            } else if ("3".equals(item.getParam())) {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.getContext(), R.drawable.ic_promo_index_sign3_v2));
            } else {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.getContext(), R.drawable.ic_promo_index_sign1_v2));
            }
        }
        Context context = holder.ivCover.getContext();
        int width = ScreenUtil.getScreenWidth(context) / 2 - SystemUtil.dp2px(context, 14);
        int height = context.getResources().getDimensionPixelSize(R.dimen.recommend_cover_height);
        ImageUtil.load(holder.ivCover, item.getCover(), width, height);
        holder.tvPlay.setText(StringUtil.numberToWord(item.getPlay()));
        holder.tvReply.setText(StringUtil.numberToWord(item.getReply()));
        holder.tvDuration.setText(StringUtil.secToTime(item.getDuration()));
        holder.tvTitle.setText(item.getTitle());
        holder.tvTName.setText(item.getTname());
    }

    static class RecommendIndexItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_iv)
        SimpleDraweeView ivCover;
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
        @BindView(R.id.login_cover_fl)
        FrameLayout flLoginCover;
        @BindView(R.id.login_cover_iv)
        ImageView ivLoginCover;

        private RecommendIndexItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
