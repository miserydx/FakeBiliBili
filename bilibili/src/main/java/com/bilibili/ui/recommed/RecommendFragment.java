package com.bilibili.ui.recommed;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.ui.recommed.viewbinder.RecommendIndexItemBinder;
import com.bumptech.glide.Glide;
import com.common.base.BaseMvpFragment;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

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

    private MultiTypeAdapter mAdapter;
    private Items items = new Items();

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
                mPresenter.loadData();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_main));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecommendIndexItemDecoration(spanSizeLookup));
        mAdapter = new MultiTypeAdapter();
        //register item
        mAdapter.register(AppIndex.class, new RecommendIndexItemBinder());
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
    }

    @Override
    public void onDataUpdated(Items items) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        this.items = items;
        mAdapter.setItems(this.items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }
}