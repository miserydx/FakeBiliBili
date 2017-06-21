package com.bilibili.ui.test.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.adapter.NewsItemViewBinder;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.bilibili.ui.test.mvp.presenter.MvpStructurePresenter;
import com.common.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsPageFragment extends BaseMvpFragment<MvpStructurePresenter> implements MvpStructureContract.View {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private MultiTypeAdapter mAdapter;
    private List<WeiXinJingXuanBean.NewsList> mData = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_news;
    }

    @Override
    protected void initInject(){
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(WeiXinJingXuanBean.NewsList.class, new NewsItemViewBinder(getContext()));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        mData.clear();
        mData.addAll(list);
        mAdapter.setItems(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshing() {
        mRefreshLayout.setRefreshing(true);
    }
}
