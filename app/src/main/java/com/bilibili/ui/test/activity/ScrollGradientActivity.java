package com.bilibili.ui.test.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bilibili.R;
import com.bilibili.base.IBaseMvpActivity;
import com.bilibili.di.component.ActivityComponent;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.ui.test.adapter.MvpStructureAdapter;
import com.bilibili.util.StatusBarUtils;
import com.bilibili.ui.test.mvp.presenter.MvpStructurePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

public class ScrollGradientActivity extends SupportActivity implements IBaseMvpActivity<MvpStructurePresenter>, MvpStructureContract.View {

    private final String TAG = ScrollGradientActivity.class.getSimpleName();

    @Inject
    MvpStructurePresenter mPresenter;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_srl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.activity_main_rv)
    RecyclerView recyclerView;

    private MvpStructureAdapter mAdapter;
    private List<WeiXinJingXuanBean.NewsList> mList = new ArrayList<>();

    @Override
    public boolean setCustomStatusBar() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_gradient;
    }

    @Override
    public void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public MvpStructurePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View getPaddingNeedView() {
        return null;
    }

    @Override
    public void initViewAndEvent() {
        //自定义statusbar样式,与toolbar融合
        StatusBarUtils.setStatusBarMergeWithToolBar(mToolbar, this);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        mToolbar.setTitle("新闻");
        setSupportActionBar(mToolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MvpStructureAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

}
