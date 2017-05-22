package com.bilibili.di.component;

import com.bilibili.di.module.ApiModule;
import com.bilibili.di.module.AppModule;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.api.ZhihuApis;
import com.bilibili.model.api.WeChatApis;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Component(modules = {AppModule.class, ApiModule.class})
@Singleton
public interface AppComponent {

    ZhihuApis zhihuApis();

    WeChatApis weChatApis();

    BangumiApis biliBiliApis();

    AppApis appApis();

    LiveApis liveApis();

}
