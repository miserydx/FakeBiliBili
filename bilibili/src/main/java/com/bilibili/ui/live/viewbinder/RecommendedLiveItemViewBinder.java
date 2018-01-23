package com.bilibili.ui.live.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveRecommend;
import com.bilibili.ui.live.liveplay.LivePlayActivity;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.StringUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/26.
 */

public class RecommendedLiveItemViewBinder extends ItemViewBinder<LiveRecommend.Recommend_data.Lives, RecommendedLiveItemViewBinder.LiveViewHolder> {

    @NonNull
    @Override
    protected LiveViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_common, null);
        return new LiveViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull final LiveViewHolder holder, @NonNull final LiveRecommend.Recommend_data.Lives item) {
        Context context = holder.cvContainer.getContext();
        int coverWidth = ScreenUtil.getScreenWidth(context) / 2 - SystemUtil.dp2px(context, 18);
        int coverHeight = context.getResources().getDimensionPixelSize(R.dimen.live_card_image_height);
        ImageUtil.load(holder.ivCover, item.getCover().getSrc(), coverWidth, coverHeight);
        holder.tvName.setText(item.getOwner().getName());
        holder.tvOnline.setText(StringUtil.numberToWord(item.getOnline()));
        String tintArea = "<font color='#FF4081'>" + "#" + item.getArea() + "#&nbsp;" + "</font>";
        holder.tvAreaTitle.setText(Html.fromHtml(tintArea + item.getTitle()));
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TestDanmuActivity.Companion.startActivity(holder.cvContainer.getContext(), item.getPlayurl(), String.valueOf(item.getRoom_id()));
                LivePlayActivity.startActivity(holder.cvContainer.getContext(), item.getPlayurl(), item.getRoom_id());
            }
        });
    }

    static class LiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_container)
        CardView cvContainer;
        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_area_title)
        TextView tvAreaTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_online)
        TextView tvOnline;

        public LiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
