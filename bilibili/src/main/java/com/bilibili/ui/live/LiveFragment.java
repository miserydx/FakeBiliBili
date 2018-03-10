package com.bilibili.ui.live;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.common.Partition;
import com.bilibili.model.bean.live.LiveAllList;
import com.bilibili.model.bean.live.LiveHeader;
import com.bilibili.model.event.LivePartialRefreshEvent;
import com.bilibili.ui.live.viewbinder.FooterItemViewBinder;
import com.bilibili.ui.live.viewbinder.LiveHeaderItemViewBinder;
import com.bilibili.ui.live.viewbinder.LiveItemViewBinder;
import com.bilibili.ui.live.viewbinder.LiveRecommendItemViewBinder;
import com.bilibili.ui.live.viewbinder.PartitionItemViewBinder;
import com.bilibili.ui.live.viewbinder.RecommendBannerItemViewBinder;
import com.bilibili.ui.live.viewbinder.RefreshItemViewBinder;
import com.bilibili.widget.adapter.CommonAdapter;
import com.common.base.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/6/18.
 */

public class LiveFragment extends BaseMvpFragment<LivePresenter> implements LiveContract.View {

    public static final String TAG = LiveFragment.class.getSimpleName();

    private static int SPAN_COUNT = 2;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter mAdapter;

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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = mAdapter.getItems().get(position);
                return o instanceof LiveAllList.Recommend_data.Lives ||
                        o instanceof LiveAllList.Lives ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_main));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new LiveIndexItemDecoration());
        mAdapter = new CommonAdapter();
        //header
        mAdapter.register(LiveHeader.class, new LiveHeaderItemViewBinder());
        //recommend
        mAdapter.register(LiveAllList.Recommend_data.Lives.class, new LiveRecommendItemViewBinder());
        //recommend banner
        mAdapter.register(LiveAllList.Recommend_data.Banner_data.class, new RecommendBannerItemViewBinder());
        //common
        mAdapter.register(Partition.class, new PartitionItemViewBinder(mContext));
        mAdapter.register(LiveAllList.Lives.class, new LiveItemViewBinder());
        //refresh
        mAdapter.register(RefreshItemViewBinder.RefreshItem.class, new RefreshItemViewBinder());
        //footer
        mAdapter.register(FooterItemViewBinder.FooterItem.class, new FooterItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.theme_color_primary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
    }

    @Override
    public void onDataUpdated(Items items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void showLoadFailed() {
        mAdapter.showLoadFailed();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(LivePartialRefreshEvent event) {

    }
}
