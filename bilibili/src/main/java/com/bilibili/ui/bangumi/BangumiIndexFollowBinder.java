package com.bilibili.ui.bangumi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/2.
 */

public class BangumiIndexFollowBinder extends ItemViewBinder<BangumiIndexFollowBinder.BangumiIndexFollow, BangumiIndexFollowBinder.BangumiIndexFollowHolder>{

    @NonNull
    @Override
    protected BangumiIndexFollowHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_index_follow, null);
        return new BangumiIndexFollowHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiIndexFollowHolder holder, @NonNull BangumiIndexFollow item) {

    }

    static class BangumiIndexFollowHolder extends RecyclerView.ViewHolder{
        public BangumiIndexFollowHolder(View itemView) {
            super(itemView);
        }
    }

    static class BangumiIndexFollow {

    }
}
