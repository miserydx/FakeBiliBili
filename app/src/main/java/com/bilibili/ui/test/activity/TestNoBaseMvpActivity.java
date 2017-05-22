package com.bilibili.ui.test.activity;

import android.view.View;

import com.bilibili.R;
import com.bilibili.base.IBaseMvpActivity;
import com.bilibili.di.component.ActivityComponent;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.ui.test.mvp.presenter.NoBaseMvpPresenter;

import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

public class TestNoBaseMvpActivity extends SupportActivity implements IBaseMvpActivity<NoBaseMvpPresenter>, MvpStructureContract.View{

    @Inject
    NoBaseMvpPresenter mPresenter;

    @Override
    public void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public NoBaseMvpPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_no_base;
    }

    @Override
    public View getPaddingNeedView() {
        return null;
    }

    @Override
    public boolean setCustomStatusBar() {
        return false;
    }

    @Override
    public void initViewAndEvent() {

    }

    @Override
    public void setRefreshing() {

    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {

    }
}
