package com.bilibili.ui.bangumi.viewbinder;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/3.
 */

public class BangumiIndexRecommendBinder extends ItemViewBinder<BangumiIndexRecommendBinder.BangumiIndexRecommend, BangumiIndexRecommendBinder.BangumiIndexRecommendHolder> {

    @NonNull
    @Override
    protected BangumiIndexRecommendHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_index_recommend, null);
        return new BangumiIndexRecommendHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiIndexRecommendHolder holder, @NonNull BangumiIndexRecommend item) {
        switch (item.section) {
            case BangumiIndexRecommend.SECTION_JP_RECOMMEND:
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_recommend);
                holder.tvTitle.setText(R.string.bangumi_common_section_header_recommend);
                holder.tvMore.setText(R.string.bangumi_follow_section_header_more);
                break;
            case BangumiIndexRecommend.SECTION_CN_RECOMMEND:
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_domestic_recommend);
                holder.tvTitle.setText(R.string.bangumi_follow_section_header_recommend_domestic);
                holder.tvMore.setText(R.string.bangumi_follow_section_header_more);
                break;
            case BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.rlContainer.setBackground(null);
                } else {
                    holder.rlContainer.setBackgroundDrawable(null);
                }
                holder.ivRecommend.setImageResource(R.drawable.bangumi_common_editor_recommend);
                holder.tvTitle.setText(R.string.bangumi_follow_section_header_recommend_editor);
                holder.tvMore.setVisibility(View.GONE);
                break;
        }

    }

    static class BangumiIndexRecommendHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container_rl)
        RelativeLayout rlContainer;
        @BindView(R.id.recommend_iv)
        ImageView ivRecommend;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.more_tv)
        TextView tvMore;

        private BangumiIndexRecommendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class BangumiIndexRecommend {

        public static final String SECTION_JP_RECOMMEND = "section_jp_recommend";
        public static final String SECTION_CN_RECOMMEND = "section_cn_recommend";
        public static final String SECTION_EDITORS_RECOMMEND = "section_editor_recommend";

        private String section;

        public BangumiIndexRecommend(String section) {
            this.section = section;
        }
    }
}