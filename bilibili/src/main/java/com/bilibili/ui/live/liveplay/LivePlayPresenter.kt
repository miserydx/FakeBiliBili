package com.bilibili.ui.live.liveplay

import com.common.base.AbsBasePresenter
import javax.inject.Inject

/**
 * Created by miserydx on 17/9/28.
 */
class LivePlayPresenter @Inject
constructor() : AbsBasePresenter<LivePlayContract.View>(), LivePlayContract.Presenter {

    companion object {
        private val TAG = LivePlayPresenter::class.java.simpleName
    }

    override fun loadData() {

    }

    override fun releaseData() {

    }
}