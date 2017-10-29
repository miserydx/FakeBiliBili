package com.bilibili.ui.live.liveplay.fragment.binder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bilibili.R
import com.bilibili.model.bean.AppIndex
import com.common.util.ImageUtil
import com.common.util.ScreenUtil
import com.common.util.StringUtil
import com.common.util.SystemUtil
import com.facebook.drawee.view.SimpleDraweeView
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/6.
 */

class LiveDanmuItemBinder : ItemViewBinder<String, LiveDanmuItemBinder.RecommendIndexItemHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecommendIndexItemHolder {
        val itemView = inflater.inflate(R.layout.item_live_danmu, null)
        return RecommendIndexItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendIndexItemHolder, item: String) {
        holder.tvDanmu.text = item
    }

    class RecommendIndexItemHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_danmu)
        lateinit var tvDanmu: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}