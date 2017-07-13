package com.bilibili.ui.recommed;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.ui.recommed.viewbinder.RecommendBannerItemViewBinder;
import com.bilibili.ui.recommed.viewbinder.RecommendIndexItemBinder;
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
                mPresenter.pullToRefresh();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = items.get(position);
                return o instanceof RecommendBannerItemViewBinder.Banner ? SPAN_COUNT : 1;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_main));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecommendIndexItemDecoration(spanSizeLookup));
        mAdapter = new MultiTypeAdapter();
        //register item
        mAdapter.register(AppIndex.class, new RecommendIndexItemBinder());
        mAdapter.register(RecommendBannerItemViewBinder.Banner.class, new RecommendBannerItemViewBinder());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                //列表中LastVisibleItem为倒数第二行时，加载更多
                if (manager.findLastVisibleItemPosition() + 3 >= manager.getItemCount()) {
                    mPresenter.loadMore();
                }
            }
        });
    }

    @Override
    public void onDataUpdated(Items items, int state) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        switch (state) {
            case RecommendPresenter.STATE_INITIAL:
                this.items = items;
                mAdapter.setItems(this.items);
                mAdapter.notifyDataSetChanged();
                break;
            case RecommendPresenter.STATE_REFRESHING:
                Items temp = new Items();
                temp.addAll(this.items);
                this.items = items;
                this.items.addAll(temp);
                mAdapter.setItems(this.items);
                mAdapter.notifyItemRangeChanged(0, items.size());
                break;
            case RecommendPresenter.STATE_LOAD_MORE:
                int position = this.items.size();
                this.items.addAll(items);
                mAdapter.setItems(this.items);
                mAdapter.notifyItemInserted(position);
                break;
        }
    }

    @Override
    public void onRefreshingStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }
}