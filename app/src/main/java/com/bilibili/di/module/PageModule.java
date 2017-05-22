package com.bilibili.di.module;

import com.bilibili.ui.test.fragment.NewsFragment;
import com.bilibili.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayiyang on 17/4/14.
 */

@Module
public class PageModule {

    @Provides
    @PerActivity
    NewsFragment provideNewsFragemnt(){
        return new NewsFragment();
    }

}
