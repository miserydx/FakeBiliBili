package com.common.app;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
}
