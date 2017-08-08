package com.bilibili.ui.bangumi

import com.common.base.BasePresenter
import com.common.base.BaseView

import me.drakeet.multitype.Items

/**
 * Created by miserydx on 17/6/29.
 */

interface BangumiContractKotlin {

    interface View : BaseView {

        fun onDataUpdated(items: Items?, state: Int)

        fun onRefreshingStateChanged(isRefresh: Boolean)

    }

    interface Presenter : BasePresenter {

        fun pullToRefresh()

        fun loadMore()

    }
}
