package com.bilibili.ui.live;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.live.LiveCommon;
import com.bilibili.model.bean.live.LiveRecommend;
import com.bilibili.model.bean.live.LiveResponse;
import com.bilibili.ui.live.viewbinder.FooterItemViewBinder;
import com.bilibili.ui.live.viewbinder.NavigatorItemViewBinder;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/6/18.
 */

public class LivePresenter extends AbsBasePresenter<LiveContract.View> implements LiveContract.Presenter {

    @Inject
    LiveApis liveApis;

    @Inject
    public LivePresenter() {
    }

    @Override
    public void loadData() {
        Observable<DataObjectResponse<LiveCommon>> common = liveApis.getCommon(
                ApiHelper.DEVICE,
                ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                ApiHelper.SCALE,
                DateUtil.getSystemTime());

        Observable<DataObjectResponse<LiveRecommend>> recommend = liveApis.getRecommend(
                ApiHelper.DEVICE,
                ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                ApiHelper.SCALE,
                DateUtil.getSystemTime());

        Observable.zip(common, recommend, new BiFunction<DataObjectResponse<LiveCommon>, DataObjectResponse<LiveRecommend>, DataObjectResponse<LiveResponse>>() {

            @Override
            public DataObjectResponse<LiveResponse> apply(@NonNull DataObjectResponse<LiveCommon> liveCommon, @NonNull DataObjectResponse<LiveRecommend> liveRecommend) throws Exception {
                LiveResponse liveResponse = new LiveResponse(liveRecommend.getData(), liveCommon.getData());
                DataObjectResponse<LiveResponse> liveResponseDataObjectResponse = new DataObjectResponse<>();
                liveResponseDataObjectResponse.setData(liveResponse);
                return liveResponseDataObjectResponse;
            }
        })

                .map(new Function<DataObjectResponse<LiveResponse>, Items>() {

                    @Override
                    public Items apply(@NonNull DataObjectResponse<LiveResponse> liveResponseDataObjectResponse) throws Exception {
                        return liveResponse2Items(liveResponseDataObjectResponse);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Items>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        mView.onRefreshingStateChanged(true);
                    }

                    @Override
                    public void onNext(@NonNull Items items) {
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(LiveFragment.TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e(LiveFragment.TAG, "onComplete");
                        mView.onRefreshingStateChanged(false);
                    }
                });

    }

    private Items liveResponse2Items(DataObjectResponse<LiveResponse> liveResponseDataObjectResponse) {
        Items items = new Items();
        LiveResponse liveResponse = liveResponseDataObjectResponse.getData();
        LiveCommon liveCommon = liveResponse.getLiveCommonResponse();
        LiveRecommend liveRecommend = liveResponse.getLiveRecommendResponse();

        //recommend
        items.add(liveCommon);
        items.add(new NavigatorItemViewBinder.NavigatorItem());
        items.add(liveRecommend.getRecommend_data());
        //common
        for (LiveCommon.Partitions partition : liveCommon.getPartitions()) {
            items.add(partition);
        }
        //footer
        items.add(new FooterItemViewBinder.FooterItem());
        return items;
    }

    @Override
    public void releaseData() {

    }
}
