package com.bilibili.di.module


import com.bilibili.di.scope.PerActivity
import com.bilibili.di.scope.PerFragment
import com.bilibili.ui.bangumi.BangumiFragment
import com.bilibili.ui.bangumi.BangumiFragmentKotlin
import com.bilibili.ui.live.LiveFragment
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
    internal fun provideNewsFragment(): NewsFragment {
        return NewsFragment()
    }

    //main
    @Provides
    @PerFragment
    internal fun provideLiveFragment(): LiveFragment {
        return LiveFragment()
    }

    @Provides
    @PerFragment
    internal fun provideRecommendFragment(): RecommendFragment {
        return RecommendFragment()
    }

    @Provides
    @PerFragment
    internal fun provideBangumiFragment(): BangumiFragment {
        return BangumiFragment()
    }

    @Provides
    @PerFragment
    internal fun provideRegionFragment(): RegionFragment {
        return RegionFragment()
    }

    @Provides
    @PerFragment
    internal fun provideBangumiFragmentKotlin(): BangumiFragmentKotlin {
        return BangumiFragmentKotlin()
    }
}
