package com.bilibili.di.module;


import com.bilibili.di.scope.PerActivity;
import com.bilibili.di.scope.PerFragment;
import com.bilibili.ui.live.LiveFragment;
import com.bilibili.ui.test.fragment.NewsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayiyang on 17/4/14.
 */

@Module
public class PageModule {

    @Provides
    @PerActivity
    NewsFragment provideNewsFragment(){
        return new NewsFragment();
    }

    //main
    @Provides
    @PerFragment
    LiveFragment provideLiveFragment() {
        return new LiveFragment();
    }

}
