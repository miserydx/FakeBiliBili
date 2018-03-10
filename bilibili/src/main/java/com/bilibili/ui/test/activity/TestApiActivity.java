package com.bilibili.ui.test.activity;

import android.util.Log;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.ApiLiveApis;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.bean.AppSearchHot;
import com.bilibili.model.bean.AppSplash;
import com.bilibili.model.bean.DataListResponse;
import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.ResultObjectResponse;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bilibili.model.bean.live.LiveAllList;
import com.bilibili.model.bean.live.LiveAreas;
import com.bilibili.model.bean.live.LiveCommon;
import com.bilibili.model.bean.live.LiveRecommend;
import com.common.base.IBaseActivity;
import com.common.util.DateUtil;
import com.common.util.StatusBarUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    @Inject
    ApiLiveApis apiLiveApis;

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

//        appApis.getSplash(ApiHelper.MOBI_APP, ApiHelper.BUILD, AppApis.CHANNEL, 1080, 1920, AppApis.VER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DataListResponse<AppSplash>>() {
//                    @Override
//                    public void accept(DataListResponse<AppSplash> appSplashRes) {
//                        Log.d("misery", "DataListResponse<AppSplash>="+ appSplashRes);
//                    }
//                });
//
//        liveApis.getCommon(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DataObjectResponse<LiveCommon>>() {
//                    @Override
//                    public void accept(DataObjectResponse<LiveCommon> liveCommonRes) {
//                        Log.d("misery", "DataObjectResponse<LiveCommon>="+ liveCommonRes);
//                    }
//                });
//
//        liveApis.getRecommend(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DataObjectResponse<LiveRecommend>>() {
//                    @Override
//                    public void accept(DataObjectResponse<LiveRecommend> liveRecommendRes) {
//                        Log.d("misery", "DataObjectResponse<LiveRecommend>="+ liveRecommendRes);
//                    }
//                });
//
//        liveApis.getAreas(ApiHelper.DEVICE, ApiHelper.APP_KEY, ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, ApiHelper.SCALE, DateUtil.getSystemTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DataListResponse<LiveAreas>>() {
//                    @Override
//                    public void accept(DataListResponse<LiveAreas> liveAreasRes) {
//                        Log.d("misery", "DataListResponse<LiveAreas>="+ liveAreasRes);
//                    }
//                });
//
//        bangumiApis.getIndexPage(ApiHelper.APP_KEY,ApiHelper.BUILD, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ResultObjectResponse<BangumiIndexPage>>() {
//                    @Override
//                    public void accept(ResultObjectResponse<BangumiIndexPage> bangumiIndexPageRes) {
//                        Log.d("misery", "ResultObjectResponse<BangumiIndexPage>="+ bangumiIndexPageRes);
//                    }
//                });
//
//        appApis.getSerchHot(ApiHelper.MOBI_APP, ApiHelper.BUILD, 50, ApiHelper.MOBI_APP, ApiHelper.PLATFORM, DateUtil.getSystemTime())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DataObjectResponse<AppSearchHot>>() {
//                    @Override
//                    public void accept(DataObjectResponse<AppSearchHot> appSerchHotRes) {
//                        Log.d("misery", "DataObjectResponse<AppSearchHot>="+ appSerchHotRes);
//                    }
//                });
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataObjectResponse<LiveAllList>>() {
                    @Override
                    public void accept(DataObjectResponse<LiveAllList> liveAllListRes) throws Exception {
                        Log.d("misery", "DataObjectResponse<LiveAllList>="+ liveAllListRes);
                    }
                });
    }
}
