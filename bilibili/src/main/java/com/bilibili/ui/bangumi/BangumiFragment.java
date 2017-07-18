package com.bilibili.ui.bangumi;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.bilibili.widget.recyclerview.BiliMultiTypeAdapter;
import com.common.base.BaseMvpFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

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

    private BiliMultiTypeAdapter mAdapter;
    private Items items = new Items();

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
                mPresenter.pullToRefresh();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = items.get(position);
                return o instanceof BangumiIndexPage.Recommend ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new BangumiIndexItemDecoration(spanSizeLookup));
        mAdapter = new BiliMultiTypeAdapter();
        //register item
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow.class, new BangumiIndexFollowBinder());
        mAdapter.register(BangumiHomeBinder.BangumiHome.class, new BangumiHomeBinder());
        mAdapter.register(BangumiIndexRecommendBinder.BangumiIndexRecommend.class, new BangumiIndexRecommendBinder());
        mAdapter.register(BangumiIndexPage.Recommend.class, new BangumiRecommendDetailBinder());
        mAdapter.register(BangumiIndexPage.Foot.class, new BangumiIndexPageFootBinder());
        mAdapter.register(BangumiIndexFall.class, new BangumiIndexFallBinder());
        mAdapter.register(BangumiDividerBinder.BangumiDivider.class, new BangumiDividerBinder());
        mAdapter.setFooterItemEnabled(true);
        mAdapter.setOnLoadMoreListener(new BiliMultiTypeAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Fresco.getImagePipeline().pause();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Fresco.getImagePipeline().resume();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    public void onDataUpdated(Items items, int state) {
        switch (state) {
            case BangumiPresenter.STATE_INITIAL:
                this.items = items;
                mAdapter.setItems(this.items);
                mAdapter.notifyDataSetChanged();
                break;
            case BangumiPresenter.STATE_REFRESHING:
                Items temp = new Items();
                temp.addAll(this.items.subList(items.size(), this.items.size() - 1));
                this.items = items;
                this.items.addAll(temp);
                mAdapter.setItems(this.items);
                mAdapter.notifyDataSetChanged();
                break;
            case BangumiPresenter.STATE_LOAD_MORE:
                if(items.size() == 0){
                    mAdapter.showNoMore();
                }
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

    @Override
    public void onDataUpdateError() {
        mAdapter.showFailToLoad();
    }
}
