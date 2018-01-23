package com.bilibili.ui.live.liveplay.fragment.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/12/20.
 */

public class LiveDanmuItemBinder extends ItemViewBinder<String, LiveDanmuItemBinder.RecommendIndexItemHolder> {

    @NonNull
    @Override
    protected RecommendIndexItemHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_live_danmu, parent, false);
        return new RecommendIndexItemHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecommendIndexItemHolder holder, @NonNull String item) {
        holder.tvDanmu.setText(item);
    }

    class RecommendIndexItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_danmu)
        TextView tvDanmu;

        public RecommendIndexItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
