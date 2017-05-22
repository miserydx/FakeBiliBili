package com.bilibili.ui.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bilibili.R;
import com.bilibili.model.bean.WeiXinJingXuanBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/8/29.
 */

public class MvpStructureAdapter extends RecyclerView.Adapter<MvpStructureAdapter.ViewHolder>{

    private Context mContext;
    private List<WeiXinJingXuanBean.NewsList> mList;

    public MvpStructureAdapter(Context mContext, List<WeiXinJingXuanBean.NewsList> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wechat_jingxuan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mList.get(position).getPicUrl())
                .asBitmap()
                .placeholder(R.color.colorPrimaryDark)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCover);
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvDescription.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_iv)
        ImageView ivCover;
        @BindView(R.id.title_tv)
        TextView tvTitle;
        @BindView(R.id.description_tv)
        TextView tvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
