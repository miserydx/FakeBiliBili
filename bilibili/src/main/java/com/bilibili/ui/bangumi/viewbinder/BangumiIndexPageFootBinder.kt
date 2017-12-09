package com.bilibili.ui.bangumi.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.bilibili.R
import com.common.util.ImageUtil
import com.common.util.ScreenUtil
import com.facebook.drawee.view.SimpleDraweeView

import butterknife.BindView
import butterknife.ButterKnife
import com.bilibili.model.bean.Foot
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/6/30.
 */

class BangumiIndexPageFootBinder : ItemViewBinder<Foot, BangumiIndexPageFootBinder.BangumiIndexPageFootHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiIndexPageFootHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_index_foot, null)
        return BangumiIndexPageFootHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiIndexPageFootHolder, item: Foot) {
        val context = holder.ivCover.context
        val width = ScreenUtil.getScreenWidth(context) - 24
        val height = context.resources.getDimensionPixelSize(R.dimen.bangumi_card_image_height)
        ImageUtil.load(holder.ivCover, item.cover, width, height)
        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.desc
    }

    class BangumiIndexPageFootHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.cover_iv)
        lateinit var ivCover: SimpleDraweeView
        @BindView(R.id.title_tv)
        lateinit var tvTitle: TextView
        @BindView(R.id.desc_tv)
        lateinit var tvDesc: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
