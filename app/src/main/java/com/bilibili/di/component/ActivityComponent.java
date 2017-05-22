package com.bilibili.di.component;

import com.bilibili.di.module.ActivityModule;
import com.bilibili.ui.main.activity.MainActivity;
import com.bilibili.ui.test.activity.NewsActivity;
import com.bilibili.ui.test.activity.TestApiActivity;
import com.bilibili.ui.test.activity.TestNoBaseActivity;
import com.bilibili.ui.test.activity.TestNoBaseMvpActivity;
import com.bilibili.ui.test.activity.ToolbarBehaviorActivity;
import com.bilibili.di.module.PageModule;
import com.bilibili.di.scope.PerActivity;
import com.bilibili.ui.test.activity.ScrollGradientActivity;
import com.bilibili.ui.test.activity.StatusWithPictureActivity;

import dagger.Component;

/**
 * Created by jiayiyang on 17/3/23.
 */

@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, PageModule.class})
@PerActivity
public interface ActivityComponent {

    void inject(NewsActivity newsActivity);

    void inject(ToolbarBehaviorActivity toolbarBehaviorActivity);

    void inject(StatusWithPictureActivity statusWithPictureActivity);

    void inject(ScrollGradientActivity scrollGradientActivity);

    void inject(TestApiActivity testApiActivity);

    void inject(TestNoBaseActivity testNoBaseActivity);

    void inject(TestNoBaseMvpActivity testNoBaseMvpActivity);

    void inject(MainActivity mainActivity);

}
