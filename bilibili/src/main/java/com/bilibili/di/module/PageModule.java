package com.bilibili.di.module;

import com.bilibili.di.scope.PerActivity;
import com.bilibili.di.scope.PerFragment;
import com.bilibili.ui.bangumi.BangumiFragment;
import com.bilibili.ui.live.LiveFragment;
import com.bilibili.ui.main.MainFragment;
import com.bilibili.ui.recommed.RecommendFragment;
import com.bilibili.ui.region.RegionFragment;
import com.bilibili.ui.test.fragment.NewsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayiyang on 17/4/14.
 */

@Module
public class PageModule {

    //Test
    @Provides
    @PerActivity
    NewsFragment provideNewsFragment(){
        return new NewsFragment();
    }

    //main
    @Provides
    @PerActivity
    MainFragment provideMainFragment() {
        return new MainFragment();
    }

    @Provides
    @PerFragment
    LiveFragment provideLiveFragment() {
        return new LiveFragment();
    }

    @Provides
    @PerFragment
    RecommendFragment provideRecommendFragment() {
        return new RecommendFragment();
    }

    @Provides
    @PerFragment
    BangumiFragment provideBangumiFragment() {
        return new BangumiFragment();
    }

    @Provides
    @PerActivity
    RegionFragment provideRegionFragment() {
        return new RegionFragment();
    }
}
