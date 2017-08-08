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

class BangumiIndexFollowBinderKotlin : ItemViewBinder<BangumiIndexFollowBinderKotlin.BangumiIndexFollow, BangumiIndexFollowBinderKotlin.BangumiIndexFollowHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BangumiIndexFollowHolder {
        val itemView = inflater.inflate(R.layout.item_bangumi_index_follow, null)
        return BangumiIndexFollowHolder(itemView)
    }

    override fun onBindViewHolder(holder: BangumiIndexFollowHolder, item: BangumiIndexFollow) {

    }

    class BangumiIndexFollowHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    class BangumiIndexFollow
}
