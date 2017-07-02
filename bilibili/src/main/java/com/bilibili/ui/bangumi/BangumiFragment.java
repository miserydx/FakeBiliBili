package com.bilibili.ui.bangumi;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.BangumiIndexPage;
import com.common.base.BaseMvpFragment;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by miserydx on 17/6/29.
 */

public class BangumiFragment extends BaseMvpFragment<BangumiPresenter> implements BangumiContract.View {

    private static int SPAN_COUNT = 3;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private MultiTypeAdapter mAdapter;
    private Items items = new Items();

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
                        || o instanceof BangumiHomeBinder.BangumiHome) ? SPAN_COUNT : 1;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new BangumiIndexItemDecoration(spanSizeLookup));
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(BangumiIndexFollowBinder.BangumiIndexFollow.class, new BangumiIndexFollowBinder());
        mAdapter.register(BangumiHomeBinder.BangumiHome.class, new BangumiHomeBinder());
        mAdapter.register(BangumiIndexPage.Foot.class, new BangumiIndexPageFootBinder(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateData(Items items) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        this.items = items;
        mAdapter.setItems(this.items);
        mAdapter.notifyDataSetChanged();
    }
}
