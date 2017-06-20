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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

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
        appApis.getRegionShow(ApiHelper.APP_KEY,ApiHelper.BUILD,ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultList<RegionShowResponse>>() {
                    @Override
                    public void accept(@NonNull ResultList<RegionShowResponse> regionShowResponseResultList) throws Exception {
                        Log.d("misery", "regionShowResponseResultList="+ regionShowResponseResultList);

                    }
                });

        appApis.getRegion(ApiHelper.BUILD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultList<RegionResponse>>() {
                    @Override
                    public void accept(ResultList<RegionResponse> regionResponseResultList) {
                        Log.d("misery", "regionResponseResultList="+ regionResponseResultList);
                    }
                });

        appApis.getIndex(ApiHelper.APP_KEY, ApiHelper.BUILD, "1493277505", ApiHelper.MOBI_APP, "wifi", ApiHelper.PLATFORM, "true", DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultList<IndexResponse>>() {
                    @Override
                    public void accept(ResultList<IndexResponse> indexResponseResultList) {
                        Log.d("misery", "indexResponseResultList="+ indexResponseResultList);
                    }
                });

        appApis.getSplash(ApiHelper.MOBI_APP, ApiHelper.BUILD, AppApis.CHANNEL, 1080, 1920, AppApis.VER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultList<SplashResponse>>() {
                    @Override
                    public void accept(ResultList<SplashResponse> splashResponseResultList) {
                        Log.d("misery", "splashResponseResultList="+ splashResponseResultList);
                    }
                });

        liveApis.getCommon(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObject<LiveCommonResponse>>() {
                    @Override
                    public void accept(ResultObject<LiveCommonResponse> liveCommonResultList) {
                        Log.d("misery", "liveCommonResultList="+ liveCommonResultList);
                    }
                });

        liveApis.getRecommend(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObject<LiveRecommendResponse>>() {
                    @Override
                    public void accept(ResultObject<LiveRecommendResponse> liveRecommendResponseResultList) {
                        Log.d("misery", "liveRecommendResponseResultList="+ liveRecommendResponseResultList);
                    }
                });

        liveApis.getAreas(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultList<LiveAreasResponse>>() {
                    @Override
                    public void accept(ResultList<LiveAreasResponse> liveAreasResponseResultList) {
                        Log.d("misery", "liveAreasResponseResultList="+ liveAreasResponseResultList);
                    }
                });

        bangumiApis.getIndexPage(ApiHelper.APP_KEY,ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObject<BangumiIndexPageResponse>>() {
                    @Override
                    public void accept(ResultObject<BangumiIndexPageResponse> bangumiIndexPageResponseResultObject) {
                        Log.d("misery", "bangumiIndexPageResponseResultObject="+bangumiIndexPageResponseResultObject);
                    }
                });

        appApis.getSerchHot(ApiHelper.MOBI_APP, ApiHelper.BUILD, 50, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObject<SearchHotResponse>>() {
                    @Override
                    public void accept(ResultObject<SearchHotResponse> appSerchHotResponseResultObject) {
                        Log.d("misery", "appSerchHotResponseResultObject="+ appSerchHotResponseResultObject);
                    }
                });
    }
}
