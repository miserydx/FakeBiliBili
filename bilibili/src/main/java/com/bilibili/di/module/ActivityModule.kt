package com.bilibili.di.module

import android.content.Context

import com.bilibili.di.scope.PerActivity

import dagger.Module
import dagger.Provides

/**
 * Created by jiayiyang on 17/3/25.
 */

@Module
class ActivityModule(private val context: Context) {

    @Provides
    @PerActivity
    internal fun provideContext(): Context {
        return context
    }
}
