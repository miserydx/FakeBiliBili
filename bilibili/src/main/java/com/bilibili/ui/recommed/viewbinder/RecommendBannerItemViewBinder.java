package com.bilibili.ui.recommed.viewbinder;

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
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.widget.banner.BannerAdapter;
import com.bilibili.widget.banner.SmartViewPager;
import com.common.util.ImageUtil;
import com.common.util.ScreenUtil;
import com.common.util.SystemUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by miserydx on 17/7/13.
 */

public class RecommendBannerItemViewBinder extends ItemViewBinder<RecommendBannerItemViewBinder.Banner, RecommendBannerItemViewBinder.RecommendBannerViewHolder> {

    @NonNull
    @Override
    protected RecommendBannerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_banner, null);
        return new RecommendBannerViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecommendBannerViewHolder holder, @NonNull RecommendBannerItemViewBinder.Banner item) {
        holder.setData(item.getBannerItemList());
    }

    static class RecommendBannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        SmartViewPager banner;

        private RecommendBannerAdapter adapter;

        public RecommendBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setNeedCirculate(true);
            banner.setNeedAutoScroll(true);
            banner.setIndicatorGravity(Gravity.BOTTOM | Gravity.RIGHT);
            banner.setIndicatorColor(ContextCompat.getColor(itemView.getContext(), R.color.white),
                    ContextCompat.getColor(itemView.getContext(), R.color.pink));
            adapter = new RecommendBannerAdapter(itemView.getContext());
        }

        public void setData(List<AppIndex.Banner_item> data) {
            adapter.setData(data, true);
            banner.setAdapter(adapter);
        }
    }

    static class RecommendBannerAdapter extends BannerAdapter<AppIndex.Banner_item, SimpleDraweeView> {

        private Context context;

        public RecommendBannerAdapter(Context context) {
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
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadius(context.getResources().getDimensionPixelSize(R.dimen.index_card_radius));
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            imageView.setHierarchy(hierarchy);
            return imageView;
        }

        @Override
        protected void bindData(SimpleDraweeView itemView, AppIndex.Banner_item item) {
            int width = ScreenUtil.getScreenWidth(context);
            int height = SystemUtil.dp2px(context, 120);
            ImageUtil.load(itemView, item.getImage(), width, height);
        }
    }

    public static class Banner {

        private List<AppIndex.Banner_item> bannerItemList;

        public List<AppIndex.Banner_item> getBannerItemList() {
            return bannerItemList;
        }

        public void setBannerItemList(List<AppIndex.Banner_item> bannerItemList) {
            this.bannerItemList = bannerItemList;
        }
    }
}
