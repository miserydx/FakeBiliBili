package com.bilibili.ui.bangumi.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bilibili.R

import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/18.
 */

class BangumiDividerBinder : ItemViewBinder<BangumiDividerBinder.BangumiDivider, BangumiDividerBinder.BangumiDividerHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiDividerHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_divider, null)
        itemView.tag = BangumiDivider()
        return BangumiDividerHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiDividerHolder, item: BangumiDivider) {

    }

    class BangumiDividerHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    class BangumiDivider

}
