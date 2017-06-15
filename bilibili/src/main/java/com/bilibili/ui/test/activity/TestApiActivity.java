package com.bilibili.ui.test.activity;

import android.util.Log;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.bean.BangumiIndexPageResponse;
import com.bilibili.model.bean.IndexResponse;
import com.bilibili.model.bean.LiveAreasResponse;
import com.bilibili.model.bean.LiveCommonResponse;
import com.bilibili.model.bean.LiveRecommendResponse;
import com.bilibili.model.bean.RegionResponse;
import com.bilibili.model.bean.RegionShowResponse;
import com.bilibili.model.bean.ResultList;
import com.bilibili.model.bean.ResultObject;
import com.bilibili.model.bean.SearchHotResponse;
import com.bilibili.model.bean.SplashResponse;
import com.common.base.IBaseActivity;
import com.common.util.DateUtil;
import com.common.util.StatusBarUtil;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestApiActivity extends SupportActivity implements IBaseActivity {

    @Inject
    AppApis appApis;
    @Inject
    BangumiApis bangumiApis;
    @Inject
    LiveApis liveApis;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_api;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public void initViewAndEvent() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color_primary));
        appApis.getRegionShow(ApiHelper.getAppKey(),ApiHelper.getBUILD(),ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultList<RegionShowResponse>>() {
                    @Override
                    public void call(ResultList<RegionShowResponse> regionShowResponseResultList) {
                        Log.d("misery", "regionShowResponseResultList="+ regionShowResponseResultList);
                    }
                });

        appApis.getRegion(ApiHelper.getBUILD())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultList<RegionResponse>>() {
                    @Override
                    public void call(ResultList<RegionResponse> regionResponseResultList) {
                        Log.d("misery", "regionResponseResultList="+ regionResponseResultList);
                    }
                });

        appApis.getIndex(ApiHelper.getAppKey(), ApiHelper.getBUILD(), "1493277505", ApiHelper.getMobiApp(), "wifi", ApiHelper.getPLATFORM(), "true", DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultList<IndexResponse>>() {
                    @Override
                    public void call(ResultList<IndexResponse> indexResponseResultList) {
                        Log.d("misery", "indexResponseResultList="+ indexResponseResultList);
                    }
                });

        appApis.getSplash(ApiHelper.getMobiApp(), ApiHelper.getBUILD(), AppApis.CHANNEL, 1080, 1920, AppApis.VER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultList<SplashResponse>>() {
                    @Override
                    public void call(ResultList<SplashResponse> splashResponseResultList) {
                        Log.d("misery", "splashResponseResultList="+ splashResponseResultList);
                    }
                });

        liveApis.getCommon(ApiHelper.getDevice(), ApiHelper.getAppKey(), ApiHelper.getBUILD(), ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), ApiHelper.getScale(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultObject<LiveCommonResponse>>() {
                    @Override
                    public void call(ResultObject<LiveCommonResponse> liveCommonResultList) {
                        Log.d("misery", "liveCommonResultList="+ liveCommonResultList);
                    }
                });

        liveApis.getRecommend(ApiHelper.getDevice(), ApiHelper.getAppKey(), ApiHelper.getBUILD(), ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), ApiHelper.getScale(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultObject<LiveRecommendResponse>>() {
                    @Override
                    public void call(ResultObject<LiveRecommendResponse> liveRecommendResponseResultList) {
                        Log.d("misery", "liveRecommendResponseResultList="+ liveRecommendResponseResultList);
                    }
                });

        liveApis.getAreas(ApiHelper.getDevice(), ApiHelper.getAppKey(), ApiHelper.getBUILD(), ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), ApiHelper.getScale(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultList<LiveAreasResponse>>() {
                    @Override
                    public void call(ResultList<LiveAreasResponse> liveAreasResponseResultList) {
                        Log.d("misery", "liveAreasResponseResultList="+ liveAreasResponseResultList);
                    }
                });

        bangumiApis.getIndexPage(ApiHelper.getAppKey(),ApiHelper.getBUILD(), ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultObject<BangumiIndexPageResponse>>() {
                    @Override
                    public void call(ResultObject<BangumiIndexPageResponse> bangumiIndexPageResponseResultObject) {
                        Log.d("misery", "bangumiIndexPageResponseResultObject="+bangumiIndexPageResponseResultObject);
                    }
                });

        appApis.getSerchHot(ApiHelper.getMobiApp(), ApiHelper.getBUILD(), 50, ApiHelper.getMobiApp(), ApiHelper.getPLATFORM(), DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultObject<SearchHotResponse>>() {
                    @Override
                    public void call(ResultObject<SearchHotResponse> appSerchHotResponseResultObject) {
                        Log.d("misery", "appSerchHotResponseResultObject="+ appSerchHotResponseResultObject);
                    }
                });
    }
}
