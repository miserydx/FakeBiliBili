package com.bilibili.ui.bangumi;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bilibili.model.bean.ResultObjectResponse;
import com.bilibili.ui.bangumi.viewbinder.BangumiHomeBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexFollowBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexRecommendBinder;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/6/29.
 */

public class BangumiPresenter extends AbsBasePresenter<BangumiContract.View> implements BangumiContract.Presenter {

    private static final String TAG = BangumiPresenter.class.getSimpleName();

    private BangumiApis bangumiApis;

    @Inject
    public BangumiPresenter(BangumiApis bangumiApis) {
        this.bangumiApis = bangumiApis;
    }

    @Override
    public void loadData() {
        bangumiApis.getIndexPage(ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultObjectResponse<BangumiIndexPage>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        mView.onRefreshingStateChanged(true);
                    }

                    @Override
                    public void onNext(@NonNull ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) {
                        Log.d(TAG, "ResultObjectResponse<BangumiIndexPage> = " + bangumiIndexPageRes);
                        Items items = getItems(bangumiIndexPageRes.getData());
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(BangumiFragment.TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.onRefreshingStateChanged(false);
                    }
                });
    }

    private Items getItems(BangumiIndexPage bangumiIndexPage) {
        Items items = new Items();
        items.add(new BangumiIndexFollowBinder.BangumiIndexFollow());
        items.add(new BangumiHomeBinder.BangumiHome());
        items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_JP));
        for (BangumiIndexPage.Recommend recommend : bangumiIndexPage.getRecommend_jp().getRecommend()) {
            items.add(recommend);
        }
        items.add(bangumiIndexPage.getRecommend_jp().getFoot().get(0));
        items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_CN));
        for (BangumiIndexPage.Recommend recommend : bangumiIndexPage.getRecommend_cn().getRecommend()) {
            items.add(recommend);
        }
        items.add(bangumiIndexPage.getRecommend_cn().getFoot().get(0));
        return items;
    }

    @Override
    public void releaseData() {

    }
}
