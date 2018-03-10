package com.bilibili.ui.live;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.ApiLiveApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.live.LiveAllList;
import com.bilibili.model.bean.live.LiveHeader;
import com.bilibili.ui.live.viewbinder.FooterItemViewBinder;
import com.bilibili.ui.live.viewbinder.RefreshItemViewBinder;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    ApiLiveApis apiLiveApis;

    @Inject
    public LivePresenter() {
    }

    @Override
    public void loadData() {
        apiLiveApis.getAllList(ApiHelper.DEVICE,
                ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                ApiHelper.DEVICE,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                ApiHelper.SCALE,
                ApiHelper.SRC,
                ApiHelper.getTraceId(),
                DateUtil.getSystemTime(),
                ApiHelper.VERSION)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<DataObjectResponse<LiveAllList>, Items>() {
                    @Override
                    public Items apply(DataObjectResponse<LiveAllList> liveAllListRes) throws Exception {
                        return getItems(liveAllListRes.getData());
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
                        mView.onDataUpdated(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LiveFragment.TAG, "onError");
                        e.printStackTrace();
                        mView.showLoadFailed();
                        mView.onRefreshingStateChanged(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(LiveFragment.TAG, "onComplete");
                        mView.onRefreshingStateChanged(false);
                    }
                });
    }

    private Items getItems(LiveAllList liveAllList) {
        Items items = new Items();
        LiveHeader liveHeader = new LiveHeader();
        liveHeader.setBanner(liveAllList.getBanner());
        //header
        items.add(liveHeader);
        //recommend
        items.add(liveAllList.getRecommend_data().getPartition());
        List<LiveAllList.Recommend_data.Lives> recommendLives = liveAllList.getRecommend_data().getLives();
        for (int i = 0; i < recommendLives.size(); i++) {
            items.add(recommendLives.get(i));
            //recommend banner
            if (i == 5 && liveAllList.getRecommend_data().getBanner_data() != null &&
                    !liveAllList.getRecommend_data().getBanner_data().isEmpty()) {
                items.add(liveAllList.getRecommend_data().getBanner_data().get(0));
            }
        }
        items.add(new RefreshItemViewBinder.RefreshItem());
        //common
        for (LiveAllList.Partitions partitions : liveAllList.getPartitions()) {
            items.add(partitions.getPartition());
            items.addAll(partitions.getLives());
            items.add(new RefreshItemViewBinder.RefreshItem());
        }
        //footer
        items.add(new FooterItemViewBinder.FooterItem());
        return items;
    }

    @Override
    public void releaseData() {

    }
}
