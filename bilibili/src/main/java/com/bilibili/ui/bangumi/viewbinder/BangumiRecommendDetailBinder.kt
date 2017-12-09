package com.bilibili.ui.bangumi.viewbinder

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.bilibili.R
import com.common.util.ImageUtil
import com.common.util.ScreenUtil
import com.common.util.StringUtil
import com.common.util.SystemUtil
import com.facebook.drawee.view.SimpleDraweeView

import butterknife.BindView
import butterknife.ButterKnife
import com.bilibili.model.bean.Recommend
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/6/30.
 */

class BangumiRecommendDetailBinder : ItemViewBinder<Recommend, BangumiRecommendDetailBinder.BangumiRecommendDetailHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiRecommendDetailHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_recommend_detail, null)
        return BangumiRecommendDetailHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiRecommendDetailHolder, item: Recommend) {
        val context = holder.ivCover.context
        val width = ScreenUtil.getScreenWidth(context) / 3 - SystemUtil.dp2px(context, 14f)
        val height = width * 4 / 3
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height)
        holder.rlCover.layoutParams = params
        ImageUtil.load(holder.ivCover, item.cover, width, height)
        if (!TextUtils.isEmpty(item.badge)) {
            holder.tvBadge.visibility = View.VISIBLE
            holder.tvBadge.text = item.badge
        }
        val favouritesText = String.format(holder.tvNewest.context.resources.getString(R.string.bangumi_index_text_suffix_hit), StringUtil.numberToWord(Integer.valueOf(item.favourites)))
        holder.tvFavourites.text = favouritesText
        holder.tvTitle.text = item.title
        val newestText = String.format(holder.tvNewest.context.resources.getString(R.string.bangumi_index_status_format_3), item.newest_ep_index)
        holder.tvNewest.text = newestText
    }

    class BangumiRecommendDetailHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.cover_rl)
        lateinit var rlCover: RelativeLayout
        @BindView(R.id.cover_iv)
        lateinit var ivCover: SimpleDraweeView
        @BindView(R.id.badge_tv)
        lateinit var tvBadge: TextView
        @BindView(R.id.favourites_tv)
        lateinit var tvFavourites: TextView
        @BindView(R.id.title_tv)
        lateinit var tvTitle: TextView
        @BindView(R.id.newest_tv)
        lateinit var tvNewest: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
