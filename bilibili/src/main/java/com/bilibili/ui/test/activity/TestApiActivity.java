package com.bilibili.ui.test.activity;

import android.util.Log;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.bean.BangumiIndexPage;
import com.bilibili.model.bean.DataListResponse;
import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.AppIndex;
import com.bilibili.model.bean.LiveAreas;
import com.bilibili.model.bean.LiveCommon;
import com.bilibili.model.bean.LiveRecommend;
import com.bilibili.model.bean.AppRegion;
import com.bilibili.model.bean.AppRegionShow;
import com.bilibili.model.bean.ResultObjectResponse;
import com.bilibili.model.bean.AppSearchHot;
import com.bilibili.model.bean.AppSplash;
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
                .subscribe(new Consumer<DataListResponse<AppRegionShow>>() {
                    @Override
                    public void accept(@NonNull DataListResponse<AppRegionShow> appRegionShowRes) throws Exception {
                        Log.d("misery", "DataListResponse<AppRegionShow>="+ appRegionShowRes);

                    }
                });

        appApis.getRegion(ApiHelper.BUILD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataListResponse<AppRegion>>() {
                    @Override
                    public void accept(DataListResponse<AppRegion> appRegionRes) {
                        Log.d("misery", "DataListResponse<AppRegion>="+ appRegionRes);
                    }
                });

        appApis.getIndex(ApiHelper.APP_KEY, ApiHelper.BUILD, "1493277505", ApiHelper.MOBI_APP, "wifi", ApiHelper.PLATFORM, "true", DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataListResponse<AppIndex>>() {
                    @Override
                    public void accept(DataListResponse<AppIndex> appIndexRes) {
                        Log.d("misery", "DataListResponse<AppIndex>="+ appIndexRes);
                    }
                });

        appApis.getSplash(ApiHelper.MOBI_APP, ApiHelper.BUILD, AppApis.CHANNEL, 1080, 1920, AppApis.VER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataListResponse<AppSplash>>() {
                    @Override
                    public void accept(DataListResponse<AppSplash> appSplashRes) {
                        Log.d("misery", "DataListResponse<AppSplash>="+ appSplashRes);
                    }
                });

        liveApis.getCommon(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataObjectResponse<LiveCommon>>() {
                    @Override
                    public void accept(DataObjectResponse<LiveCommon> liveCommonRes) {
                        Log.d("misery", "DataObjectResponse<LiveCommon>="+ liveCommonRes);
                    }
                });

        liveApis.getRecommend(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataObjectResponse<LiveRecommend>>() {
                    @Override
                    public void accept(DataObjectResponse<LiveRecommend> liveRecommendRes) {
                        Log.d("misery", "DataObjectResponse<LiveRecommend>="+ liveRecommendRes);
                    }
                });

        liveApis.getAreas(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataListResponse<LiveAreas>>() {
                    @Override
                    public void accept(DataListResponse<LiveAreas> liveAreasRes) {
                        Log.d("misery", "DataListResponse<LiveAreas>="+ liveAreasRes);
                    }
                });

        bangumiApis.getIndexPage(ApiHelper.APP_KEY,ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultObjectResponse<BangumiIndexPage>>() {
                    @Override
                    public void accept(ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) {
                        Log.d("misery", "ResultObjectResponse<BangumiIndexPage>="+ bangumiIndexPageRes);
                    }
                });

        appApis.getSerchHot(ApiHelper.MOBI_APP, ApiHelper.BUILD, 50, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataObjectResponse<AppSearchHot>>() {
                    @Override
                    public void accept(DataObjectResponse<AppSearchHot> appSerchHotRes) {
                        Log.d("misery", "DataObjectResponse<AppSearchHot>="+ appSerchHotRes);
                    }
                });
    }
}
