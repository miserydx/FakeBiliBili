package com.bilibili.di.component

import com.bilibili.di.module.FragmentModule
import com.bilibili.di.module.PageModule
import com.bilibili.di.scope.PerFragment
import com.bilibili.ui.bangumi.BangumiFragment
import com.bilibili.ui.live.LiveFragment
import com.bilibili.ui.main.MainFragment
import com.bilibili.ui.recommed.RecommendFragment
import com.bilibili.ui.region.RegionFragment
import com.bilibili.ui.test.fragment.NewsFragment
import com.bilibili.ui.test.fragment.NewsPageFragment

import dagger.Component

/**
 * Created by jiayiyang on 17/4/14.
 */

@Component(dependencies = arrayOf(ApiComponent::class), modules = arrayOf(FragmentModule::class, PageModule::class))
@PerFragment
interface FragmentComponent {

    fun inject(newsFragment: NewsFragment)

    fun inject(newsPageFragment: NewsPageFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(liveFragment: LiveFragment)

    fun inject(recommendFragment: RecommendFragment)

    fun inject(regionFragment: RegionFragment)

    fun inject(bangumiFragment: BangumiFragment)
}
