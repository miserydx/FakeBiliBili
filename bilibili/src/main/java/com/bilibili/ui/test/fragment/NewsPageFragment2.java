package com.bilibili.ui.test.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.adapter.NewsItemViewBinder;
import com.bilibili.ui.test.mvp.contract.NewsContract;
import com.bilibili.ui.test.mvp.presenter.NewsPresenter;
import com.common.base.BaseMvpFragment;
import com.common.widget.adapter.DefaultAdapterWrapper;

import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsPageFragment2 extends BaseMvpFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private DefaultAdapterWrapper adapterWrapper;

    private MultiTypeAdapter mAdapter;

    private Items items;

    private int refreshCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_news;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        items = new Items();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MultiTypeAdapter();
        adapterWrapper = new DefaultAdapterWrapper(mAdapter);
        mAdapter.register(WeiXinJingXuanBean.NewsList.class, new NewsItemViewBinder(getContext()));
        adapterWrapper.setOnLoadMoreListener(new DefaultAdapterWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        mRecyclerView.setAdapter(adapterWrapper);
        adapterWrapper.showLoading();
    }

    @Override
    public void updateData(List<WeiXinJingXuanBean.NewsList> list) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        refreshCount++;
        if (refreshCount == 2) {
            list.clear();
            adapterWrapper.showLoadFailed();
        } else {
            items.clear();
            items.addAll(list);
            mAdapter.setItems(items);
            adapterWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadMore(List<WeiXinJingXuanBean.NewsList> list) {
        items.addAll(list);
        adapterWrapper.loadMoreComplete();
//        adapterWrapper.showNoMore();
    }

    @Override
    public void setRefreshing() {
        mRefreshLayout.setRefreshing(true);
    }
}
