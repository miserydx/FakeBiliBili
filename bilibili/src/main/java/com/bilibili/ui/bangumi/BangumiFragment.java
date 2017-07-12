package com.bilibili.ui.bangumi;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.bangumi.BangumiIndexPage;
import com.bilibili.ui.bangumi.viewbinder.BangumiHomeBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexFollowBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexPageFootBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiIndexRecommendBinder;
import com.bilibili.ui.bangumi.viewbinder.BangumiRecommendDetailBinder;
import com.common.base.BaseMvpFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

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

    private MultiTypeAdapter mAdapter;
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
                mPresenter.loadData();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object o = items.get(position);
                return (o instanceof BangumiIndexPage.Foot
                        || o instanceof BangumiIndexFollowBinder.BangumiIndexFollow
                        || o instanceof BangumiHomeBinder.BangumiHome)
                        || o instanceof BangumiIndexRecommendBinder.BangumiIndexRecommend ? SPAN_COUNT : 1;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new BangumiIndexItemDecoration(spanSizeLookup));
        mAdapter = new MultiTypeAdapter();
        //register item
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow.class, new BangumiIndexFollowBinder());
        mAdapter.register(BangumiHomeBinder.BangumiHome.class, new BangumiHomeBinder());
        mAdapter.register(BangumiIndexRecommendBinder.BangumiIndexRecommend.class, new BangumiIndexRecommendBinder());
        mAdapter.register(BangumiIndexPage.Recommend.class, new BangumiRecommendDetailBinder());
        mAdapter.register(BangumiIndexPage.Foot.class, new BangumiIndexPageFootBinder());
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
