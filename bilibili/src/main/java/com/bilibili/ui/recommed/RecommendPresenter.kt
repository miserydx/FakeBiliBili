package com.bilibili.ui.recommed

import android.util.Log

import com.bilibili.model.api.ApiHelper
import com.bilibili.model.api.RecommendApis
import com.bilibili.ui.recommed.viewbinder.RecommendBannerItemViewBinder
import com.common.base.AbsBasePresenter
import com.common.util.DateUtil

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.drakeet.multitype.Items

/**
 * Created by miserydx on 17/7/6.
 */

class RecommendPresenter @Inject
constructor(private val recommendApis: RecommendApis) : AbsBasePresenter<RecommendContract.View>(), RecommendContract.Presenter {

    companion object {
        private val TAG = RecommendPresenter::class.java.simpleName
        private val LOGIN_EVENT_NORMAL = 0
        private val LOGIN_EVENT_INITIAL = 1
        private val OPEN_EVENT_NULL = ""
        private val OPEN_EVENT_COLD = "cold"
        private val STYLE = 2
        val STATE_NORMAL = 0
        val STATE_INITIAL = 1
        val STATE_REFRESHING = 2
        val STATE_LOAD_MORE = 3
    }

    private var loginEvent: Int = 0
    private var openEvent: String? = null
    private var pull: Boolean = false
    private var idx: Int = 0
    private var isLoadingMore: Boolean = false

    override fun loadingMoreFinished(){
        isLoadingMore = false
    }

    override fun loadData() {
        getIndex(STATE_INITIAL)
    }

    override fun pullToRefresh(idx: Int) {
        this.idx = idx
        getIndex(STATE_REFRESHING)
    }

    override fun loadMore(idx: Int) {
        if(isLoadingMore){
            return
        }
        isLoadingMore = true
        this.idx = idx
        getIndex(STATE_LOAD_MORE)
    }


    /**
     * 列表数据接口

     * @param operationState 请求状态：初次请求，下拉刷新，上滑加载更多
     */
    private fun getIndex(operationState: Int) {
        when (operationState) {
            STATE_INITIAL -> {
                loginEvent = LOGIN_EVENT_INITIAL
                openEvent = OPEN_EVENT_COLD
                pull = true
            }
            STATE_REFRESHING -> {
                loginEvent = LOGIN_EVENT_NORMAL
                openEvent = OPEN_EVENT_NULL
                pull = true
            }
            STATE_LOAD_MORE -> {
                loginEvent = LOGIN_EVENT_NORMAL
                openEvent = OPEN_EVENT_NULL
                pull = false
            }
        }
        recommendApis.getIndex(ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                idx,
                loginEvent,
                ApiHelper.MOBI_APP,
                ApiHelper.NETWORK_WIFI,
                openEvent,
                ApiHelper.PLATFORM,
                pull,
                STYLE,
                DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map { appIndexDataListResponse ->
                    val items = Items()
                    for (appIndex in appIndexDataListResponse.data) {
                        if (appIndex.banner_item != null) {
                            val banner = RecommendBannerItemViewBinder.Banner(appIndex.banner_item,
                                    appIndex.param,
                                    appIndex.goto,
                                    appIndex.idx)
                            items.add(banner)
                        } else {
                            items.add(appIndex)
                        }
                    }
                    items
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Items> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        registerRx(d)
                        if (operationState == STATE_INITIAL || operationState == STATE_REFRESHING) {
                            mView.onRefreshingStateChanged(true)
                        }
                    }

                    override fun onNext(@NonNull items: Items) {
                        mView.onDataUpdated(items, operationState)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Log.e(TAG, "onError")
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    override fun releaseData() {

    }
}
