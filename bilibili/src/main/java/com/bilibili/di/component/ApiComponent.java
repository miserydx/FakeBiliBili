package com.bilibili.di.component;

import com.bilibili.di.module.ApiModule;
import com.bilibili.di.scope.GlobalApis;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.api.RecommendApis;
import com.bilibili.model.api.RegionApis;
import com.bilibili.model.api.WeChatApis;
import com.bilibili.model.api.ZhihuApis;
import com.common.app.AppComponent;

import dagger.Component;

/**
 * Created by Android_ZzT on 17/6/15.
 */

@Component(dependencies = AppComponent.class, modules = ApiModule.class)
@GlobalApis
public interface ApiComponent {

    AppApis appApis();

    LiveApis liveApis();

    RecommendApis recommendApis();

    BangumiApis biliBiliApis();

    RegionApis regionApis();

    //Test
    ZhihuApis zhihuApis();

    WeChatApis weChatApis();
}
