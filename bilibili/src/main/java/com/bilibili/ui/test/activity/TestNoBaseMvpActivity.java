package com.bilibili.ui.test.activity;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.ui.test.mvp.presenter.NoBaseMvpPresenter;
import com.common.base.IBaseMvpActivity;
import com.common.util.StatusBarUtil;

import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

public class TestNoBaseMvpActivity extends SupportActivity implements IBaseMvpActivity<NoBaseMvpPresenter>, MvpStructureContract.View{

    @Inject
    NoBaseMvpPresenter mPresenter;


    @Override
    public NoBaseMvpPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_no_base;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public void initViewAndEvent() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color_primary));
    }

    @Override
    public void setRefreshing() {

    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {

    }
}
