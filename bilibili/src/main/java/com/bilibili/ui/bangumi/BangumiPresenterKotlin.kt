package com.bilibili.ui.bangumi

import android.util.Log

import com.bilibili.model.api.ApiHelper
import com.bilibili.model.api.BangumiApis
import com.bilibili.model.bean.BangumiIndexPage
import com.bilibili.ui.bangumi.viewbinder.*
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
 * Created by miserydx on 17/6/29.
 */

class BangumiPresenterKotlin @Inject
constructor(private val bangumiApis: BangumiApis) : AbsBasePresenter<BangumiContractKotlin.View>(), BangumiContractKotlin.Presenter {

    companion object {
        private val TAG = BangumiPresenterKotlin::class.java.simpleName
        val STATE_NORMAL = 0
        val STATE_INITIAL = 1
        val STATE_REFRESHING = 2
        val STATE_LOAD_MORE = 3
        val STATE_LOAD_ERROR = 4
    }

    private var state = 0
    private var cursor: Long = 0

    override fun loadData() {
        state = STATE_INITIAL
        getIndexPage()
    }

    override fun pullToRefresh() {
        if (state == STATE_REFRESHING) {
            return
        }
        state = STATE_REFRESHING
        getIndexPage()
    }

    private fun getIndexPage() {
        bangumiApis.getIndexPage(ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map { bangumiIndexPageRes -> getItems(bangumiIndexPageRes.data) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Items> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        registerRx(d)
                        mView.onRefreshingStateChanged(true)
                    }

                    override fun onNext(@NonNull items: Items) {
                        mView.onDataUpdated(items, state)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Log.e(TAG, "onError")
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        state = STATE_NORMAL
                        mView.onRefreshingStateChanged(false)
                    }
                })
    }

    private fun getItems(bangumiIndexPage: BangumiIndexPage): Items {
        val items = Items()
        items.add(BangumiIndexFollowBinderKotlin.BangumiIndexFollow())
        items.add(BangumiHomeBinderKotlin.BangumiHome())
        items.add(BangumiDividerBinderKotlin.BangumiDivider())
        items.add(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend.SECTION_JP_RECOMMEND))
        for (recommend in bangumiIndexPage.recommend_jp.recommend) {
            items.add(recommend)
        }
        items.add(bangumiIndexPage.recommend_jp.foot[0])
        items.add(BangumiDividerBinderKotlin.BangumiDivider())
        items.add(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend.SECTION_CN_RECOMMEND))
        for (recommend in bangumiIndexPage.recommend_cn.recommend) {
            items.add(recommend)
        }
        items.add(bangumiIndexPage.recommend_cn.foot[0])
        return items
    }

    override fun loadMore() {
        if (state == STATE_LOAD_MORE) {
            return
        }
        bangumiApis.getIndexFall(ApiHelper.APP_KEY, ApiHelper.BUILD, cursor, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map { bangumiIndexFallRes ->
                    val items = Items()
                    if (cursor == 0L) {
                        items.add(BangumiDividerBinderKotlin.BangumiDivider())
                        items.add(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend(BangumiIndexRecommendBinderKotlin.BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND))
                    }
                    for (bangumiIndexFall in bangumiIndexFallRes.data) {
                        if (bangumiIndexFall.cursor != 0.0) {
                            cursor = bangumiIndexFall.cursor.toLong()
                        }
                        items.add(bangumiIndexFall)
                    }
                    items
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Items> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        registerRx(d)
                        state = STATE_LOAD_MORE
                    }

                    override fun onNext(@NonNull items: Items) {
                        mView.onDataUpdated(items, state)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Log.e(TAG, "onError")
                        e.printStackTrace()
                        state = STATE_LOAD_ERROR
                        mView.onDataUpdated(null, STATE_LOAD_ERROR)
                    }

                    override fun onComplete() {
                        state = STATE_NORMAL
                    }
                })
    }

    override fun releaseData() {

    }
}
