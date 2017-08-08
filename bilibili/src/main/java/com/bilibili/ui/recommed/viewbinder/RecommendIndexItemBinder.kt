package com.bilibili.ui.recommed.viewbinder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.bilibili.R
import com.bilibili.model.bean.AppIndex
import com.common.util.ImageUtil
import com.common.util.ScreenUtil
import com.common.util.StringUtil
import com.common.util.SystemUtil
import com.facebook.drawee.view.SimpleDraweeView

import butterknife.BindView
import butterknife.ButterKnife
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/6.
 */

class RecommendIndexItemBinder : ItemViewBinder<AppIndex, RecommendIndexItemBinder.RecommendIndexItemHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecommendIndexItemHolder {
        val itemView = inflater.inflate(R.layout.item_recommend_index_item, null)
        return RecommendIndexItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendIndexItemHolder, item: AppIndex) {
        if ("login" == item.goto) {
            holder.flLoginCover.visibility = View.VISIBLE
            if ("2" == item.param) {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.context, R.drawable.ic_promo_index_sign2_v2))
            } else if ("3" == item.param) {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.context, R.drawable.ic_promo_index_sign3_v2))
            } else {
                holder.ivLoginCover.setImageDrawable(ContextCompat.getDrawable(holder.ivLoginCover.context, R.drawable.ic_promo_index_sign1_v2))
            }
        }
        val context = holder.ivCover.context
        val width = ScreenUtil.getScreenWidth(context) / 2 - SystemUtil.dp2px(context, 14f)
        val height = context.resources.getDimensionPixelSize(R.dimen.recommend_cover_height)
        ImageUtil.load(holder.ivCover, item.cover, width, height)
        holder.tvPlay.text = StringUtil.numberToWord(item.play)
        holder.tvReply.text = StringUtil.numberToWord(item.reply)
        holder.tvDuration.text = StringUtil.secToTime(item.duration)
        holder.tvTitle.text = item.title
        holder.tvTName.text = item.tname
    }

    class RecommendIndexItemHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.cover_iv)
        lateinit var ivCover: SimpleDraweeView
        @BindView(R.id.play_tv)
        lateinit var tvPlay: TextView
        @BindView(R.id.reply_tv)
        lateinit var tvReply: TextView
        @BindView(R.id.duration_tv)
        lateinit var tvDuration: TextView
        @BindView(R.id.title_tv)
        lateinit var tvTitle: TextView
        @BindView(R.id.t_name_tv)
        lateinit var tvTName: TextView
        @BindView(R.id.login_cover_fl)
        lateinit var flLoginCover: FrameLayout
        @BindView(R.id.login_cover_iv)
        lateinit var ivLoginCover: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}
