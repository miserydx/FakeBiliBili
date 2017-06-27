package com.bilibili.ui.live;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.live.LiveCommon;
import com.bilibili.model.bean.live.LiveRecommend;
import com.bilibili.ui.live.viewbinder.BannerItemViewBinder;
import com.bilibili.ui.live.viewbinder.FooterItemViewBinder;
import com.bilibili.ui.live.viewbinder.LiveListItemViewBinder;
import com.bilibili.ui.live.viewbinder.NavigatorItemViewBinder;
import com.bilibili.ui.live.viewbinder.RecommendedLiveListItemViewBinder;
import com.bumptech.glide.Glide;
import com.common.base.BaseMvpFragment;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Android_ZzT on 17/6/18.
 */

public class LiveFragment extends BaseMvpFragment<LivePresenter> implements LiveContract.View {

    public static final String TAG = "LiveFragment";

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private MultiTypeAdapter mAdapter;

    private Items mItems;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        mItems = new Items();
        mAdapter = new MultiTypeAdapter();
        //banner
        mAdapter.register(LiveCommon.class, new BannerItemViewBinder());
        //navigator
        mAdapter.register(NavigatorItemViewBinder.NavigatorItem.class, new NavigatorItemViewBinder());
        //recommend-live
        mAdapter.register(LiveRecommend.Recommend_data.class, new RecommendedLiveListItemViewBinder());
        //live
        mAdapter.register(LiveCommon.Partitions.class, new LiveListItemViewBinder());
        //footer
        mAdapter.register(FooterItemViewBinder.FooterItem.class, new FooterItemViewBinder());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color._bkgd__gray_very_light));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Glide.with(getContext()).pauseRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getContext()).resumeRequests();
                }
            }
        });
       mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               mPresenter.loadData();
           }
       });
    }

    @Override
    public void onDataUpdated(Items items) {
        mItems.clear();
        mItems.addAll(items);
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }
}
