package com.bilibili.ui.bangumi;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.bean.BangumiIndexFall;
import com.bilibili.model.bean.Recommend;
import com.bilibili.model.bean.ResultListResponse;
import com.bilibili.model.bean.ResultObjectResponse;
import com.bilibili.model.bean.BangumiIndexPage;
import com.bilibili.ui.bangumi.viewbinder.BangumiDividerBinder;
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
    public static final int STATE_LOAD_ERROR = 4;

    private BangumiApis bangumiApis;
    private int state = 0;
    private long cursor = 0;

    @Inject
    public BangumiPresenter(BangumiApis bangumiApis) {
        this.bangumiApis = bangumiApis;
    }

    @Override
    public void loadData() {
        state = STATE_INITIAL;
        getIndexPage();
    }

    @Override
    public void pullToRefresh() {
        if (state == STATE_REFRESHING) {
            return;
        }
        state = STATE_REFRESHING;
        getIndexPage();
    }

    private void getIndexPage() {
        bangumiApis.getIndexPage(ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<ResultObjectResponse<BangumiIndexPage>, Items>() {
                    @Override
                    public Items apply(@NonNull ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) throws Exception {
                        return getItems(bangumiIndexPageRes.getData());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        mView.onRefreshingStateChanged(true);
                    }

                    @Override
                    public void onNext(@NonNull Items items) {
                        mView.onDataUpdated(items, state);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        state = STATE_NORMAL;
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
        for (Recommend recommend : bangumiIndexPage.getRecommend_jp().getRecommend()) {
            items.add(recommend);
        }
        items.add(bangumiIndexPage.getRecommend_jp().getFoot().get(0));
        items.add(new BangumiDividerBinder.BangumiDivider());
        items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_CN_RECOMMEND));
        for (Recommend recommend : bangumiIndexPage.getRecommend_cn().getRecommend()) {
            items.add(recommend);
        }
        items.add(bangumiIndexPage.getRecommend_cn().getFoot().get(0));
        return items;
    }

    @Override
    public void loadMore() {
        if (state == STATE_LOAD_MORE) {
            return;
        }
        bangumiApis.getIndexFall(ApiHelper.APP_KEY, ApiHelper.BUILD, cursor, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<ResultListResponse<BangumiIndexFall>, Items>() {
                    @Override
                    public Items apply(@NonNull ResultListResponse<BangumiIndexFall> bangumiIndexFallRes) throws Exception {
                        Items items = new Items();
                        if (cursor == 0) {
                            items.add(new BangumiDividerBinder.BangumiDivider());
                            items.add(new BangumiIndexRecommendBinder.BangumiIndexRecommend(BangumiIndexRecommendBinder.BangumiIndexRecommend.SECTION_EDITORS_RECOMMEND));
                        }
                        for (BangumiIndexFall bangumiIndexFall : bangumiIndexFallRes.getData()) {
                            if (bangumiIndexFall.getCursor() != 0) {
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
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        state = STATE_LOAD_MORE;
                    }

                    @Override
                    public void onNext(@NonNull Items items) {
                        mView.onDataUpdated(items, state);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                        state = STATE_LOAD_ERROR;
                        mView.onDataUpdated(null, STATE_LOAD_ERROR);
                    }

                    @Override
                    public void onComplete() {
                        state = STATE_NORMAL;
                    }
                });
    }

    @Override
    public void releaseData() {

    }
}
