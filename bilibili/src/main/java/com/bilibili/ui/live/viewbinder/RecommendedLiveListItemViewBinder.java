package com.bilibili.ui.live.viewbinder;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveRecommend;
import com.bilibili.widget.adapter.CommonAdapter;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/6/29.
 */

public class RecommendedLiveListItemViewBinder extends ItemViewBinder<LiveRecommend.Recommend_data, RecommendedLiveListItemViewBinder.LiveListViewHolder> {

    @NonNull
    @Override
    protected LiveListViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.recyclerview, null);
        return new LiveListViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiveListViewHolder holder, @NonNull LiveRecommend.Recommend_data item) {
        holder.setData(item);
    }

    static class LiveListViewHolder extends RecyclerView.ViewHolder {

        CommonAdapter adapter;

        RecyclerView recyclerView;

        Items items;

        static final int SPAN_SIZE = 2;

        public LiveListViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            adapter = new CommonAdapter();
            items = new Items();
            adapter.register(LiveRecommend.Recommend_data.Partition.class, new RecommendedPartitionItemViewBinder(itemView.getContext()));
            adapter.register(LiveRecommend.Recommend_data.Banner_data.class, new RecommenedBannerItemViewBinder());
            adapter.register(LiveRecommend.Recommend_data.Lives.class, new RecommendedLiveItemViewBinder());
            adapter.register(MoreItemViewBinder.MoreItem.class, new MoreItemViewBinder());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), SPAN_SIZE);
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanSize = SPAN_SIZE;
                    Object item = items.get(position);
                    if (item instanceof LiveRecommend.Recommend_data.Lives) {
                        spanSize = 1;
                    }
                    return spanSize;
                }
            };
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
            recyclerView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.bg_main));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getChildLayoutPosition(view);
                    if (spanSizeLookup.getSpanSize(position) == SPAN_SIZE) {
                        outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                        outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                    } else {
                        int spanIndex = spanSizeLookup.getSpanIndex(position, SPAN_SIZE);
                        switch (spanIndex) {
                            case 0:
                                outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                                outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                                break;
                            case 1:
                                outRect.left = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_tiny);
                                outRect.right = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_medium);
                                break;
                        }
                    }
                    outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
                    outRect.top = view.getContext().getResources().getDimensionPixelSize(R.dimen.margin_small);
                }
            });
        }

        public void setData(LiveRecommend.Recommend_data item) {
            items.clear();
            List<LiveRecommend.Recommend_data.Lives> lives = item.getLives();
            items.add(item.getPartition());
            for (int i = 0; i < lives.size() / 2; i++) {
                items.add(lives.get(i));
            }
            for (LiveRecommend.Recommend_data.Banner_data banner_data : item.getBanner_data()) {
                items.add(banner_data);
            }
            for (int i = lives.size() / 2; i < lives.size(); i++) {
                items.add(lives.get(i));
            }
            items.add(new MoreItemViewBinder.MoreItem());
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }
}
