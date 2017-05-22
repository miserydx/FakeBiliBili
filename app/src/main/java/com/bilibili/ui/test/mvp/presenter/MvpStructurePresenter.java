package com.bilibili.ui.test.mvp.presenter;

import android.util.Log;

import com.bilibili.base.AbsBasePresenter;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.model.api.WeChatApis;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class MvpStructurePresenter extends AbsBasePresenter<MvpStructureContract.View> implements MvpStructureContract.Presenter {

    private static final String TAG = MvpStructurePresenter.class.getSimpleName();

    private WeChatApis weChatApis;

    @Inject
    public MvpStructurePresenter(WeChatApis weChatApis) {
        this.weChatApis = weChatApis;
    }


    @Override
    public void loadData() {
        Subscription rxSubscription2 = weChatApis.getWeiXinJingXuan(WeChatApis.KEY, 25, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Action1<WeiXinJingXuanBean>() {
                    @Override
                    public void call(WeiXinJingXuanBean weiXinJingXuanBean) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeiXinJingXuanBean>() {
                    @Override
                    public void onStart() {
                        mView.setRefreshing();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d("misery", "onCompleted");
                    }

                    @Override
                    public void onNext(WeiXinJingXuanBean weiXinJingXuanBean) {
                        mView.updateData(weiXinJingXuanBean.getNewslist());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("misery", "onError");
                        Log.e("zzt", "error " + e.toString());
                    }
                });
        subscribeRx(rxSubscription2);
    }

    @Override
    public void releaseData() {

    }

}
