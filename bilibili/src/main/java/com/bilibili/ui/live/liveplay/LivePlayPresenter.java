package com.bilibili.ui.live.liveplay;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.ApiLiveApis;
import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.live.LiveIndex;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by miserydx on 17/12/20.
 */

public class LivePlayPresenter extends AbsBasePresenter<LivePlayContract.View> implements LivePlayContract.Presenter {

    private static final String TAG = LivePlayPresenter.class.getSimpleName();

    private ApiLiveApis apiLiveApis;

    @Inject
    public LivePlayPresenter(ApiLiveApis apiLiveApis) {
        this.apiLiveApis = apiLiveApis;
    }

    @Override
    public void getLiveIndex(int roomId) {
        apiLiveApis.getLiveIndex(ApiHelper.DEVICE,
                ApiHelper.APP_KEY,
                ApiHelper.BUILD,
                ApiHelper.DEVICE,
                ApiHelper.MOBI_APP,
                ApiHelper.PLATFORM,
                roomId,
                ApiHelper.SCALE,
                ApiHelper.SRC,
                ApiHelper.getTraceId(),
                DateUtil.getSystemTime(),
                ApiHelper.VERSION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataObjectResponse<LiveIndex>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void onNext(DataObjectResponse<LiveIndex> liveIndexRes) {
                        mView.onInfoPrepared(liveIndexRes.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Override
    public void releaseData() {

    }
}
