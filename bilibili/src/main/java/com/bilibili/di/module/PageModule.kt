package com.bilibili.di.module


import com.bilibili.di.scope.PerActivity
import com.bilibili.di.scope.PerFragment
import com.bilibili.ui.bangumi.BangumiFragment
import com.bilibili.ui.live.LiveFragment
import com.bilibili.ui.main.MainFragment
import com.bilibili.ui.recommed.RecommendFragment
import com.bilibili.ui.region.RegionFragment
import com.bilibili.ui.test.fragment.NewsFragment

import dagger.Module
import dagger.Provides

/**
 * Created by jiayiyang on 17/4/14.
 */

@Module
class PageModule {

    //Test
    @Provides
    @PerActivity
    internal fun provideNewsFragment(): NewsFragment = NewsFragment()

    //main
    @Provides
    @PerActivity
    internal fun provideMainFragment(): MainFragment = MainFragment()

    @Provides
    @PerFragment
    internal fun provideLiveFragment(): LiveFragment = LiveFragment()

    @Provides
    @PerFragment
    internal fun provideRecommendFragment(): RecommendFragment = RecommendFragment()

    @Provides
    @PerActivity
    internal fun provideRegionFragment(): RegionFragment = RegionFragment()

    @Provides
    @PerFragment
    internal fun provideBangumiFragmentKotlin(): BangumiFragment = BangumiFragment()
}
