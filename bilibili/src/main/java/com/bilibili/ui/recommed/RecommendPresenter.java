package com.bilibili.ui.recommed;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.RecommendApis;
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.model.bean.DataListResponse;
import com.bilibili.ui.bangumi.BangumiFragment;
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
 * Created by miserydx on 17/7/6.
 */

public class RecommendPresenter extends AbsBasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private static final String TAG = RecommendPresenter.class.getSimpleName();

    private RecommendApis recommendApis;

    @Inject
    public RecommendPresenter(RecommendApis recommendApis) {
        this.recommendApis = recommendApis;
    }

    @Override
    public void loadData() {
        recommendApis.getIndex(ApiHelper.APP_KEY, ApiHelper.BUILD, "1493277505", ApiHelper.MOBI_APP, "wifi", ApiHelper.PLATFORM, "true", DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataListResponse<AppIndex>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        mView.onRefreshingStateChanged(true);
                    }

                    @Override
                    public void onNext(@NonNull DataListResponse<AppIndex> appIndexDataListResponse) {
                        Items items = new Items();
                        for(AppIndex appIndex : appIndexDataListResponse.getData()){
                            items.add(appIndex);
                        }
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(BangumiFragment.TAG, e.getMessage().toString());
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
