package com.bilibili.ui.bangumi.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bilibili.R

import me.drakeet.multitype.ItemViewBinder

/**
 * Created by miserydx on 17/7/2.
 */

class BangumiHomeBinder : ItemViewBinder<BangumiHomeBinder.BangumiHome, BangumiHomeBinder.BangumiHomeHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiHomeHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_home, null)
        return BangumiHomeHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiHomeHolder, item: BangumiHome) {

    }

    class BangumiHomeHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    class BangumiHome
}
