package com.bilibili.di.component

import com.bilibili.di.module.ApiModule
import com.bilibili.di.scope.GlobalApis
import com.bilibili.model.api.AppApis
import com.bilibili.model.api.BangumiApis
import com.bilibili.model.api.LiveApis
import com.bilibili.model.api.RecommendApis
import com.bilibili.model.api.RegionApis
import com.bilibili.model.api.WeChatApis
import com.bilibili.model.api.ZhihuApis
import com.common.app.AppComponent

import dagger.Component

/**
 * Created by Android_ZzT on 17/6/15.
 */

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ApiModule::class))
@GlobalApis
interface ApiComponent {

    fun appApis(): AppApis

    fun liveApis(): LiveApis

    fun recommendApis(): RecommendApis

    fun biliBiliApis(): BangumiApis

    fun regionApis(): RegionApis

    //Test
    fun zhihuApis(): ZhihuApis

    fun weChatApis(): WeChatApis
}
