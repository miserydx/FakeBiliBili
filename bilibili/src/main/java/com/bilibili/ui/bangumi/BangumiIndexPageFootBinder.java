package com.bilibili.ui.bangumi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.R;
import com.bilibili.model.bean.BangumiIndexPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/6/30.
 */

public class BangumiIndexPageFootBinder extends ItemViewBinder<BangumiIndexPage.Foot, BangumiIndexPageFootBinder.BangumiIndexPageFootHolder> {

    private Context mContext;

    public BangumiIndexPageFootBinder(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected BangumiIndexPageFootHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_bangumi_index_foot, null);
        return new BangumiIndexPageFootHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BangumiIndexPageFootHolder holder, @NonNull BangumiIndexPage.Foot item) {
        Glide.with(mContext)
                .load(item.getCover())
                .asBitmap()
                .placeholder(R.drawable.ic_download_cover_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCover);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDesc());
    }

    static class BangumiIndexPageFootHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cover_iv)
        ImageView ivCover;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.desc_tv)
        TextView tvDesc;

        public BangumiIndexPageFootHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
