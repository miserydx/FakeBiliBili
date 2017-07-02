package com.bilibili.ui.bangumi;

import android.util.Log;

import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.bean.BangumiIndexPage;
import com.bilibili.model.bean.ResultObjectResponse;
import com.common.base.AbsBasePresenter;
import com.common.util.DateUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
        Disposable disposable = bangumiApis.getIndexPage(ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObjectResponse<BangumiIndexPage>>() {
                    @Override
                    public void accept(@NonNull ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) throws Exception {
                        Log.d(TAG, "ResultObjectResponse<BangumiIndexPage> = "+bangumiIndexPageRes);
                        Items items = new Items();
                        items.add(new BangumiIndexFollowBinder.BangumiIndexFollow());
                        items.add(new BangumiHomeBinder.BangumiHome());
                        items.add(bangumiIndexPageRes.getData().getRecommend_cn().getFoot().get(0));
                        items.add(bangumiIndexPageRes.getData().getRecommend_jp().getFoot().get(0));
                        mView.updateData(items);
                    }
                });
        registerRx(disposable);
    }

    @Override
    public void releaseData() {

    }
}
