package com.bilibili.ui.recommed

import com.common.base.BasePresenter
import com.common.base.BaseView

import me.drakeet.multitype.Items

/**
 * Created by miserydx on 17/7/6.
 */

interface RecommendContract {

    interface View : BaseView {

        fun onDataUpdated(items: Items, state: Int)

        fun onRefreshingStateChanged(isRefresh: Boolean)

    }

    interface Presenter : BasePresenter {

        fun pullToRefresh(idx: Int)

        fun loadMore(idx: Int)

        fun loadingMoreFinished()

    }

}
