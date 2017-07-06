package com.bilibili.ui.bangumi.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/3.
 */

public class BangumiIndexRecommendBinder extends ItemViewBinder<BangumiIndexRecommendBinder.BangumiIndexRecommend, BangumiIndexRecommendBinder.BangumiIndexRecommendHolder>{

    @NonNull
    @Override
    protected BangumiIndexRecommendHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_index_recommend, null);
        return new BangumiIndexRecommendHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiIndexRecommendHolder holder, @NonNull BangumiIndexRecommend item) {
        switch (item.section){
            case BangumiIndexRecommend.SECTION_JP:
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_recommend);
                holder.tvTitle.setText(R.string.bangumi_common_section_header_recommend);
                break;
            case BangumiIndexRecommend.SECTION_CN:
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_domestic_recommend);
                holder.tvTitle.setText(R.string.bangumi_follow_section_header_recommend_domestic);
                break;
        }

    }

    static class BangumiIndexRecommendHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recommend_iv)
        ImageView ivRecommend;
        @BindView(R.id.title_tv)
        TextView tvTitle;

        private BangumiIndexRecommendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class BangumiIndexRecommend {

        public static final String SECTION_JP = "section_jp";
        public static final String SECTION_CN = "section_cn";

        private String section;

        public BangumiIndexRecommend(String section) {
            this.section = section;
        }
    }
}
