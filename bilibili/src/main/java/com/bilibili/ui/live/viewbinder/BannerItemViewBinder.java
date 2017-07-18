package com.bilibili.ui.live.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bilibili.R;
import com.bilibili.model.bean.live.LiveCommon;
import com.bilibili.widget.banner.BannerAdapter;
import com.bilibili.widget.banner.SmartViewPager;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/6/21.
 */

public class BannerItemViewBinder extends ItemViewBinder<LiveCommon, BannerItemViewBinder.BannerItemViewHolder> {

    @NonNull
    @Override
    protected BannerItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_banner, null);
        return new BannerItemViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull BannerItemViewHolder holder, @NonNull LiveCommon item) {
        holder.setData(item.getBanner());
    }

    static class BannerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        SmartViewPager banner;

        private LiveBannerAdapter adapter;


        public BannerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setNeedCirculate(true);
            banner.setNeedAutoScroll(true);
            banner.setIndicatorGravity(Gravity.BOTTOM | Gravity.RIGHT);
            banner.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.white),
                    ContextCompat.getColor(itemView.getContext(), R.color.pink));
            int height = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.top_banner_height);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            banner.setLayoutParams(params);
            adapter = new LiveBannerAdapter(itemView.getContext());
        }

        private void setData(List<LiveCommon.Banner> data) {
            adapter.setData(data, true);
            banner.setAdapter(adapter);
        }
    }

    static class LiveBannerAdapter extends BannerAdapter<LiveCommon.Banner, SimpleDraweeView> {

        private Context context;

        public LiveBannerAdapter(Context ctx) {
            context = ctx;
        }

        @Override
        protected int getLayoutId() {
            return INVALID_ID;
        }

        @Override
        protected SimpleDraweeView getItemView() {
            SimpleDraweeView imageView = new SimpleDraweeView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            return imageView;
        }

        @Override
        protected void bindData(SimpleDraweeView itemView, LiveCommon.Banner item) {
            int width = ScreenUtil.getScreenWidth(context);
            int height = context.getResources().getDimensionPixelSize(R.dimen.top_banner_height);
            ImageUtil.load(itemView, item.getImg(), width, height);
        }
    }
}
