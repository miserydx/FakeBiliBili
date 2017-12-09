package com.bilibili.di.component

import com.bilibili.di.module.ActivityModule
import com.bilibili.di.module.PageModule
import com.bilibili.di.scope.PerActivity
import com.bilibili.ui.live.liveplay.LivePlayActivity
import com.bilibili.ui.main.MainActivity
import com.bilibili.ui.test.activity.*

import dagger.Component

/**
 * Created by jiayiyang on 17/3/23.
 */

@Component(dependencies = arrayOf(ApiComponent::class), modules = arrayOf(ActivityModule::class, PageModule::class))
@PerActivity
interface ActivityComponent {
    //Bilibili
    fun inject(mainActivity: MainActivity)
    fun inject(livePlayActivity: LivePlayActivity)

    //Test
    fun inject(newsActivity: NewsActivity)
    fun inject(toolbarBehaviorActivity: ToolbarBehaviorActivity)
    fun inject(statusWithPictureActivity: StatusWithPictureActivity)
    fun inject(scrollGradientActivity: ScrollGradientActivity)
    fun inject(testApiActivity: TestApiActivity)
    fun inject(testNoBaseActivity: TestNoBaseActivity)
    fun inject(testNoBaseMvpActivity: TestNoBaseMvpActivity)

}
