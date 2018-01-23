package com.bilibili.ui.bangumi;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.bean.ResultListResponse;
import com.bilibili.model.bean.ResultObjectResponse;
import com.bilibili.model.bean.bangumi.BangumiIndexFall;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bilibili.ui.bangumi.viewbinder.BangumiDividerBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiHomeBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexFollowBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexRecommendBinder;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/6/29.
 */

public class BangumiPresenter extends AbsBasePresenter<BangumiContract.View> implements BangumiContract.Presenter {

    private static final String TAG = BangumiPresenter.class.getSimpleName();
    public static final int STATE_NORMAL = 0;
    public static final int STATE_INITIAL = 1;
    public static final int STATE_REFRESHING = 2;
    public static final int STATE_LOAD_MORE = 3;

    private BangumiApis bangumiApis;
    private long cursor = 0;

    @Inject
    public BangumiPresenter(BangumiApis bangumiApis) {
        this.bangumiApis = bangumiApis;
    }

    @Override
    public void loadData() {
        getIndexPage(STATE_INITIAL);
    }

    @Override
    public void pullToRefresh() {
        getIndexPage(STATE_REFRESHING);
    }

    private void getIndexPage(final int state) {
        bangumiApis.getIndexPage(ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<ResultObjectResponse<BangumiIndexPage>, Items>() {
                    @Override
                    public Items apply(ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) throws Exception {
                        return getItems(bangumiIndexPageRes.getData());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        registerRx(d);
                        mView.onRefreshingStateChanged(true);
                    }

                    @Override
                    public void onNext(Items items) {
                        mView.onDataUpdated(items, state);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                        mView.showLoadFailed();
                        mView.onRefreshingStateChanged(false);
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
        items.add(new BangumiDividerBinder.BangumiDivider());
        items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_JP_RECOMMEND));
        items.addAll(bangumiIndexPage.getRecommend_jp().getRecommend());
        items.add(bangumiIndexPage.getRecommend_jp().getFoot().get(0));
        items.add(new BangumiDividerBinder.BangumiDivider());
        items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_CN_RECOMMEND));
        items.addAll(bangumiIndexPage.getRecommend_cn().getRecommend());
        items.add(bangumiIndexPage.getRecommend_cn().getFoot().get(0));
        return items;
    }

    @Override
    public void loadMore() {
        bangumiApis.getIndexFall(ApiHelper.APP_KEY,
                ApiHelper.BUILD, cursor,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<ResultListResponse<BangumiIndexFall>, Items>() {
                    @Override
                    public Items apply(ResultListResponse<BangumiIndexFall> bangumiIndexFallRes) throws Exception {
                        Items items = new Items();
                        if (cursor == 0L) {
                            items.add(new BangumiDividerBinder.BangumiDivider());
                            items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(
                                    BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND));
                        }
                        for (BangumiIndexFall bangumiIndexFall : bangumiIndexFallRes.getData()) {
                            if (bangumiIndexFall.getCursor() != 0.0) {
                                cursor = (long) (bangumiIndexFall.getCursor());
                            }
                            items.add(bangumiIndexFall);
                        }
                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(Items items) {
                        mView.onDataUpdated(items, STATE_LOAD_MORE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BangumiFragment.TAG, "onError");
                        e.printStackTrace();
                        mView.showLoadMoreError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void releaseData() {

    }
}