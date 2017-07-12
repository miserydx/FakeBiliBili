package com.bilibili.ui.region.viewbinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bilibili.R;
import com.bilibili.model.bean.region.AppRegionShow;
import com.bilibili.widget.banner.BannerAdapter;
import com.bilibili.widget.banner.SmartViewPager;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class RegionBannerItemViewBinder extends ItemViewBinder<AppRegionShow.Banner, RegionBannerItemViewBinder.RegionBannerViewHolder> {

    @NonNull
    @Override
    protected RegionBannerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_banner, null);
        return new RegionBannerViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RegionBannerViewHolder holder, @NonNull AppRegionShow.Banner item) {
        holder.setData(item.getTop());
    }

    static class RegionBannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        SmartViewPager banner;

        private RegionBannerAdapter adapter;

        public RegionBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setNeedCirculate(true);
            banner.setIndicatorGravity(Gravity.BOTTOM | Gravity.RIGHT);
            banner.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.white),
                    ContextCompat.getColor(itemView.getContext(), R.color.pink));
            adapter = new RegionBannerAdapter(itemView.getContext());
        }

        public void setData(List<AppRegionShow.Banner.Top> data) {
            adapter.setData(data, true);
            banner.setAdapter(adapter);
        }
    }

    static class RegionBannerAdapter extends BannerAdapter<AppRegionShow.Banner.Top, SimpleDraweeView> {

        private Context context;

        public RegionBannerAdapter(Context context) {
            this.context = context;
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
        protected void bindData(SimpleDraweeView itemView, AppRegionShow.Banner.Top item) {
            int width = ScreenUtil.getScreenWidth(context);
            int height = SystemUtil.dp2px(context, 120);
            ImageUtil.load(itemView, item.getImage(), width, height);
        }
    }
}
