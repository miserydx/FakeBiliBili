package com.bilibili.ui.recommed.viewbinder

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import com.bilibili.R
import com.bilibili.model.bean.AppIndex
import com.bilibili.widget.banner.BannerAdapter
import com.bilibili.widget.banner.SmartViewPager
import com.common.util.ImageUtil
import com.common.util.ScreenUtil
import com.common.util.SystemUtil
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.annotations.SerializedName

import butterknife.BindView
import butterknife.ButterKnife
import com.bilibili.model.bean.Banner_item
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/13.
 */

class RecommendBannerItemViewBinder : ItemViewBinder<RecommendBannerItemViewBinder.Banner, RecommendBannerItemViewBinder.RecommendBannerViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecommendBannerViewHolder {
        val itemView = inflater.inflate(R.layout.item_banner, null)
        return RecommendBannerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendBannerViewHolder, item: RecommendBannerItemViewBinder.Banner) {
        holder.setData(item.bannerItemList)
    }

    class RecommendBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.banner)
        lateinit var banner: SmartViewPager

        private val adapter: RecommendBannerAdapter

        init {
            ButterKnife.bind(this, itemView)
            banner.setNeedCirculate(true)
            banner.setNeedAutoScroll(true)
            banner.setIndicatorGravity(Gravity.BOTTOM or Gravity.RIGHT)
            banner.setIndicatorColor(ContextCompat.getColor(itemView.context, R.color.white),
                    ContextCompat.getColor(itemView.context, R.color.pink))
            val height = itemView.context.resources.getDimensionPixelSize(R.dimen.banner_item_height)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            banner.layoutParams = params
            adapter = RecommendBannerAdapter(itemView.context)
        }

        fun setData(data: List<Banner_item>) {
            adapter.setData(data, true)
            banner.setAdapter(adapter)
        }
    }

    class RecommendBannerAdapter(val context: Context) : BannerAdapter<Banner_item, SimpleDraweeView>() {

        override fun getLayoutId(): Int {
            return BannerAdapter.INVALID_ID
        }

        override fun getItemView(): SimpleDraweeView {
            val imageView = SimpleDraweeView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            imageView.layoutParams = params
            val roundingParams = RoundingParams()
            roundingParams.setCornersRadius(context.resources.getDimensionPixelSize(R.dimen.index_card_radius).toFloat())
            val builder = GenericDraweeHierarchyBuilder(context.resources)
            val hierarchy = builder.build()
            hierarchy.roundingParams = roundingParams
            imageView.hierarchy = hierarchy
            return imageView
        }

        override fun bindData(itemView: SimpleDraweeView, item: Banner_item) {
            val width = ScreenUtil.getScreenWidth(context) - SystemUtil.dp2px(context, 16f)
            val height = context.resources.getDimensionPixelSize(R.dimen.banner_item_height)
            ImageUtil.load(itemView, item.image, width, height)
        }
    }

    data class Banner (var bannerItemList: List<Banner_item>,
                       var param: String,
                       @SerializedName("goto") var _goto: String,
                       var idx: Int)
}
