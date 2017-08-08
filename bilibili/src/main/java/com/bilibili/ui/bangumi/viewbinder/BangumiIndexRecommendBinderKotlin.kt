package com.bilibili.ui.bangumi.viewbinder

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bilibili.R

import butterknife.BindView
import butterknife.ButterKnife
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/3.
 */

class BangumiIndexRecommendBinderKotlin : ItemViewBinder<BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend, BangumiIndexRecommendBinderKotlin.BangumiIndexRecommendHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiIndexRecommendHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_index_recommend, null)
        return BangumiIndexRecommendHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiIndexRecommendHolder, item: BangumiIndexRecommend) {
        when (item.section) {
            BangumiIndexRecommend.SECTION_JP_RECOMMEND -> {
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_recommend)
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_recommend)
                holder.tvTitle.setText(R.string.bangumi_common_section_header_recommend)
                holder.tvMore.setText(R.string.bangumi_follow_section_header_more)
            }
            BangumiIndexRecommend.SECTION_CN_RECOMMEND -> {
                holder.ivRecommend.setImageResource(R.drawable.bangumi_follow_ic_domestic_recommend)
                holder.tvTitle.setText(R.string.bangumi_follow_section_header_recommend_domestic)
                holder.tvMore.setText(R.string.bangumi_follow_section_header_more)
            }
            BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.rlContainer.background = null
                } else {
                    holder.rlContainer.setBackgroundDrawable(null)
                }
                holder.ivRecommend.setImageResource(R.drawable.bangumi_common_editor_recommend)
                holder.tvTitle.setText(R.string.bangumi_follow_section_header_recommend_editor)
                holder.tvMore.visibility = View.GONE
            }
        }

    }

    class BangumiIndexRecommendHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.container_rl)
        lateinit var rlContainer: RelativeLayout
        @BindView(R.id.recommend_iv)
        lateinit var ivRecommend: ImageView
        @BindView(R.id.title_tv)
        lateinit var tvTitle: TextView
        @BindView(R.id.more_tv)
        lateinit var tvMore: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class BangumiIndexRecommend(val section: String) {
        companion object {

            val SECTION_JP_RECOMMEND = "section_jp_recommend"
            val SECTION_CN_RECOMMEND = "section_cn_recommend"
            val SECTION_EDITORS_RECOMMEND = "section_editor_recommend"
        }
    }
}
