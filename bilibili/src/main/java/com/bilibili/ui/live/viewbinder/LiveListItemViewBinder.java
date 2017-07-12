package com.bilibili.ui.live.viewbinder;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveCommon;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Android_ZzT on 17/6/29.
 */

public class LiveListItemViewBinder extends ItemViewBinder<LiveCommon.Partitions, LiveListItemViewBinder.LiveListViewHolder> {

    @NonNull
    @Override
    protected LiveListViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.recyclerview, null);
        return new LiveListViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiveListViewHolder holder, @NonNull LiveCommon.Partitions item) {
        holder.setData(item);
    }

    static class LiveListViewHolder extends RecyclerView.ViewHolder {

        MultiTypeAdapter adapter;

        RecyclerView recyclerView;

        Items items;

        static final int SPAN_SIZE = 2;

        public LiveListViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            items = new Items();
            adapter = new MultiTypeAdapter();
            adapter.register(LiveCommon.Partitions.Partition.class, new PartitionItemViewBinder(itemView.getContext()));
            adapter.register(LiveCommon.Partitions.Lives.class, new LiveItemViewBinder());
            adapter.register(MoreItemViewBinder.MoreItem.class, new MoreItemViewBinder());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), SPAN_SIZE);
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanSize = SPAN_SIZE;
                    Object item = items.get(position);
                    if (item instanceof LiveCommon.Partitions.Lives) {
                        spanSize = 1;
                    }
                    return spanSize;
                }
            };
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
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

        public void setData(LiveCommon.Partitions item) {
            items.clear();
            items.add(item.getPartition());
//            for (LiveCommon.Partitions.Lives live: item.getLives()) {
//                items.add(live);
//            }
            for (int i = 0; i < 4; i++) {
                items.add(item.getLives().get(i));
            }
            items.add(new MoreItemViewBinder.MoreItem());
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }
}
