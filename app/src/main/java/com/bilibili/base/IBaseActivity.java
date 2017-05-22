package com.bilibili.base;

import android.view.View;

import com.bilibili.di.component.ActivityComponent;

/**
 * Created by jiayiyang on 17/5/8.
 */

public interface IBaseActivity {

    /**
     * 获取布局id
     * @return
     */
    int getLayoutId();

    /**
     * 获取需要设置padding的view,padding值为默认构造statusBar的高度
     * @return 非DrawerLayout页面返回null即可
     */
    View getPaddingNeedView();

    /**
     * 设置是否自定义statusbar
     * @return true自定义,false按照默认封装的状态栏样式
     */
    boolean setCustomStatusBar();

    /**
     * 初始化dagger注入
     */
    void initInject(ActivityComponent activityComponent);

    void initViewAndEvent();

}
