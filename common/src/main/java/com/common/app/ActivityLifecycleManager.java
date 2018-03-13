package com.common.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.common.base.BaseView;
import com.common.base.IBaseActivity;
import com.common.base.IBaseMvpActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiayiyang on 17/5/9.
 */

public final class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {

    private static final String EXTRA_ACTIVITY_BEAN = "extra_activity_bean";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        boolean isIBaseActivity = activity instanceof IBaseActivity;
        boolean isIBaseMvpActivity = activity instanceof IBaseMvpActivity;

        //IBaseActivity 项目中的 Activity 都应实现 IBaseActivity
        if (isIBaseActivity) {
            IBaseActivity iActivity = (IBaseActivity) activity;
            //加载布局
            activity.setContentView(LayoutInflater.from(activity).inflate(iActivity.getLayoutId(), null));
            //创建变量保存实体
            ActivityBean bean = new ActivityBean();
            //绑定布局
            Unbinder unbinder = ButterKnife.bind(activity);
            bean.setUnbinder(unbinder);
            //保存变量
            activity.getIntent().putExtra(EXTRA_ACTIVITY_BEAN, bean);
            //Dagger注入
            iActivity.initInject();
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
            //加载数据
            if (activity instanceof IBaseMvpActivity) {
                IBaseMvpActivity mvpActivity = (IBaseMvpActivity) activity;
                mvpActivity.getPresenter().loadData();
            }
        }
        //TODO 第三方的 Activity 如需做公共处理，可在此处添加

    }

    @Override
    public void onActivityStarted(Activity activity) {

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
                if (iActivity.getPresenter() != null) {
                    iActivity.getPresenter().detachView();
                }
            }
        }
    }

}
