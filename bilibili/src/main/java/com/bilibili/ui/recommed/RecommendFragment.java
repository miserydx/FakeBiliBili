package com.bilibili.ui.recommed;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.ui.recommed.viewbinder.RecommendBannerItemViewBinder;
import com.bilibili.ui.recommed.viewbinder.RecommendIndexItemBinder;
import com.bilibili.widget.adapter.CommonAdapter;
import com.common.base.BaseMvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;

/**
 * Created by miserydx on 17/7/6.
 */

public class RecommendFragment extends BaseMvpFragment<RecommendPresenter> implements RecommendContract.View {

    public static final String TAG = RecommendFragment.class.getSimpleName();

    private static int SPAN_COUNT = 2;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
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
                pullToRefresh();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = mAdapter.getItems().get(position);
                return o instanceof AppIndex ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_main));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecommendIndexItemDecoration());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                //列表中LastVisibleItem为倒数第二行时，加载更多
                if (manager.findLastVisibleItemPosition() + SPAN_COUNT >= manager.getItemCount()
                        && !mAdapter.isLoading()
                        && !mAdapter.isLoadFailed()) {
                    mPresenter.loadMore(((AppIndex) mAdapter.getItems().get(mAdapter.getItems().size() - 1)).getIdx() - 1);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter = new CommonAdapter();
        //register item
        mAdapter.register(AppIndex.class, new RecommendIndexItemBinder());
        mAdapter.register(RecommendBannerItemViewBinder.Banner.class, new RecommendBannerItemViewBinder());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDataUpdated(@NonNull Items items, int state) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        switch (state) {
            case RecommendPresenter.STATE_INITIAL:
                mAdapter.addItems(items);
                break;
            case RecommendPresenter.STATE_REFRESHING:
                Items temp = new Items();
                temp.addAll(mAdapter.getItems());
                items.addAll(temp);
                mAdapter.setItems(items);
                mAdapter.notifyDataSetChanged();
                break;
            case RecommendPresenter.STATE_LOAD_MORE:
                mAdapter.addItems(items);
                mRecyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0f, 0f, 0));//停止recyclerView滑动
                break;
        }
    }

    private void pullToRefresh() {
        int idx = 0;
        List items = mAdapter.getItems();
        if (items.get(0) instanceof RecommendBannerItemViewBinder.Banner) {
            idx = ((RecommendBannerItemViewBinder.Banner) items.get(0)).getIdx() + 1;
        } else if (items.get(0) instanceof AppIndex) {
            idx = ((AppIndex) items.get(0)).getIdx() + 1;
        }
        mPresenter.pullToRefresh(idx);
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void showLoadFailed() {
        mAdapter.showLoadFailed();
    }

    @OnClick(R.id.load_more_tv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_more_tv:
                mRefreshLayout.setRefreshing(true);
                pullToRefresh();
                break;
        }
    }

}