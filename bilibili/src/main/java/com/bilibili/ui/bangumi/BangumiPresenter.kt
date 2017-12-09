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

class BangumiPresenter @Inject
constructor(private val bangumiApis: BangumiApis) : AbsBasePresenter<BangumiContract.View>(), BangumiContract.Presenter {

    companion object {
        private val TAG = BangumiPresenter::class.java.simpleName
        val STATE_NORMAL = 0
        val STATE_INITIAL = 1
        val STATE_REFRESHING = 2
        val STATE_LOAD_MORE = 3
        val STATE_LOAD_ERROR = 4
    }

    private var cursor: Long = 0

    override fun loadData() {
        getIndexPage(STATE_INITIAL)
    }

    override fun pullToRefresh() {
        getIndexPage(STATE_REFRESHING)
    }

    private fun getIndexPage(state: Int) {
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
                        mView.onRefreshingStateChanged(false)
                    }
                })
    }

    private fun getItems(bangumiIndexPage: BangumiIndexPage): Items {
        val items = Items()
        items.add(BangumiIndexFollowBinder.BangumiIndexFollow())
        items.add(BangumiHomeBinder.BangumiHome())
        items.add(BangumiDividerBinder.BangumiDivider())
        items.add(BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_JP_RECOMMEND))
        for (recommend in bangumiIndexPage.recommend_jp.recommend) {
            items.add(recommend)
        }
        items.add(bangumiIndexPage.recommend_jp.foot[0])
        items.add(BangumiDividerBinder.BangumiDivider())
        items.add(BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_CN_RECOMMEND))
        for (recommend in bangumiIndexPage.recommend_cn.recommend) {
            items.add(recommend)
        }
        items.add(bangumiIndexPage.recommend_cn.foot[0])
        return items
    }

    override fun loadMore() {
        bangumiApis.getIndexFall(ApiHelper.APP_KEY, ApiHelper.BUILD, cursor, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map { bangumiIndexFallRes ->
                    val items = Items()
                    if (cursor == 0L) {
                        items.add(BangumiDividerBinder.BangumiDivider())
                        items.add(BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND))
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
                    }

                    override fun onNext(@NonNull items: Items) {
                        mView.onDataUpdated(items, STATE_LOAD_MORE)
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Log.e(TAG, "onError")
                        e.printStackTrace()
                        mView.onDataUpdated(null, STATE_LOAD_ERROR)
                    }

                    override fun onComplete() {}
                })
    }

    override fun releaseData() {

    }
}
