package com.bilibili.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bilibili.R;
import com.bilibili.base.BaseView;
import com.bilibili.base.IBaseActivity;
import com.bilibili.base.IBaseMvpActivity;
import com.bilibili.di.component.ActivityComponent;
import com.bilibili.di.component.DaggerActivityComponent;
import com.bilibili.di.module.ActivityModule;
import com.bilibili.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiayiyang on 17/5/9.
 */

final class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {

    private static final String EXTRA_ACTIVITY_BEAN = "extra_activity_bean";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        boolean isIBaseActivity = activity instanceof IBaseActivity;
        boolean isIBaseMvpActivity = activity instanceof IBaseMvpActivity;

        //IBaseActivity 项目中的 Activity 都应实现 IBaseActivity
        if (isIBaseActivity) {
            IBaseActivity iActivity = (IBaseActivity) activity;
            //加载布局
            View contentView = LayoutInflater.from(activity).inflate(iActivity.getLayoutId(), null);
            activity.setContentView(contentView);
            //创建变量保存实体
            ActivityBean bean = new ActivityBean();
            //依赖注入
            Unbinder unbinder = ButterKnife.bind(activity);
            bean.setUnbinder(unbinder);
            //保存变量
            activity.getIntent().putExtra(EXTRA_ACTIVITY_BEAN, bean);
            //设置透明状态栏
            if (iActivity.setCustomStatusBar()) {
                setTransparentStatusBar(activity, contentView);
            }

            iActivity.initInject(getActivityComponent(activity));

            //IBaseMvpActivity 如果是 MVP Activity 则实现 IBaseMvpActivity
            if (isIBaseMvpActivity) {
                IBaseMvpActivity mvpActivity = (IBaseMvpActivity) activity;
                BaseView view = (BaseView) mvpActivity;
                if (mvpActivity.getPresenter() != null) {
                    mvpActivity.getPresenter().attachView(view);
                }
            }

            //TODO 如有其它特殊 Activity，定义新接口继承IBaseActivity，并在此处理

            //初始化
            iActivity.initViewAndEvent();
        }

        //TODO 第三方的 Activity 如需做公共处理，可在此处添加
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof IBaseMvpActivity) {
            IBaseMvpActivity mvpActivity = (IBaseMvpActivity) activity;
            mvpActivity.getPresenter().loadData();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof IBaseMvpActivity) {
            IBaseMvpActivity mvpActivity = (IBaseMvpActivity) activity;
            mvpActivity.getPresenter().releaseData();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof IBaseActivity) {
            ActivityBean bean = activity.getIntent().getParcelableExtra(EXTRA_ACTIVITY_BEAN);
            bean.getUnbinder().unbind();
            if (activity instanceof IBaseMvpActivity) {
                IBaseMvpActivity iActivity = (IBaseMvpActivity) activity;
                //presenter.detach
                if (iActivity.getPresenter() != null) {
                    iActivity.getPresenter().detachView();
                }
            }
        }
    }

    /**
     * 使用透明状态栏,在原来状态栏的位置添加一个大小相同的矩形View
     */
    private void setTransparentStatusBar(Activity activity, View contentView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4及以上 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            //生成一个状态栏大小的矩形
            View statusBarView = createStatusBarView(activity, activity.getResources().getColor(R.color.theme_color_primary));
            // 添加 statusBarView 到布局中
            content.addView(statusBarView, 0);
            if (activity instanceof IBaseActivity && ((IBaseActivity) activity).getPaddingNeedView() != null) {
                contentView = ((IBaseActivity) activity).getPaddingNeedView();
            }
            contentView.setPadding(0, StatusBarUtils.getStatusBarHeight(activity), 0, 0);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param color 状态栏颜色值
     * @return 状态栏矩形条
     */
    private View createStatusBarView(Context context, int color) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    private ActivityComponent getActivityComponent(Activity activity) {
        return DaggerActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .activityModule(new ActivityModule(activity))
                .build();
    }

}
