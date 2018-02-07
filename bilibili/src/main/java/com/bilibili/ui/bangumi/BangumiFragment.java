package com.bilibili.ui.bangumi;

import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.bangumi.BangumiIndexFall;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bilibili.ui.bangumi.viewbinder.BangumiDividerBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiHomeBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexFallBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexFollowBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexPageFootBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexRecommendBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiRecommendDetailBinder;
import com.bilibili.widget.recyclerview.CommonAdapter;
import com.common.base.BaseMvpFragment;
import com.common.widget.adapter.base.BaseAdapterWrapper;

import butterknife.BindView;
import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/6/29.
 */

public class BangumiFragment extends BaseMvpFragment<BangumiPresenter> implements BangumiContract.View {

    public static final String TAG = BangumiFragment.class.getSimpleName();

    private static int SPAN_COUNT = 3;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bangumi;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        mRefreshLayout.setColorSchemeResources(R.color.theme_color_primary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mAdapter.getItems().size() == 0) {
                    mPresenter.loadData();
                } else {
                    mPresenter.pullToRefresh();
                }
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = mAdapter.getItems().get(position);
                return o instanceof BangumiIndexPage.Recommend ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new BangumiIndexItemDecoration());
        mAdapter = new CommonAdapter();
        //register item
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow.class, new BangumiIndexFollowBinder());
        mAdapter.register(BangumiHomeBinder.BangumiHome.class, new BangumiHomeBinder());
        mAdapter.register(BangumiIndexRecommendBinder.BangumiIndexRecommend.class, new BangumiIndexRecommendBinder());
        mAdapter.register(BangumiIndexPage.Recommend.class, new BangumiRecommendDetailBinder());
        mAdapter.register(BangumiIndexPage.Foot.class, new BangumiIndexPageFootBinder());
        mAdapter.register(BangumiIndexFall.class, new BangumiIndexFallBinder());
        mAdapter.register(BangumiDividerBinder.BangumiDivider.class, new BangumiDividerBinder());
        //other settings
        mAdapter.setOnLoadMoreListener(new CommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        mAdapter.setOnClickRetryListener(new BaseAdapterWrapper.OnClickRetryListener() {
            @Override
            public void onClickRetry() {
                mPresenter.loadMore();
            }
        });
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDataUpdated(Items items, int state) {
        switch (state) {
            case BangumiPresenter.STATE_INITIAL:
                mAdapter.setItems(items);
                mAdapter.notifyDataSetChanged();
                break;
            case BangumiPresenter.STATE_REFRESHING:
                Items temp = new Items();
                temp.addAll(mAdapter.getItems().subList(items.size(), mAdapter.getItems().size()));
                items.addAll(temp);
                mAdapter.setItems(items);
                mAdapter.notifyDataSetChanged();
                break;
            case BangumiPresenter.STATE_LOAD_MORE:
                if (items.size() == 0) {
                    mAdapter.showNoMore();
                    return;
                }
                mAdapter.addItems(items);
                mRecyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0f, 0f, 0));//停止recyclerView滑动
                mAdapter.loadMoreComplete();
                break;
        }
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void showLoadMoreError() {
        mAdapter.showFailToLoadMore();
    }

    @Override
    public void showLoadFailed() {
        mAdapter.showLoadFailed();
    }
}