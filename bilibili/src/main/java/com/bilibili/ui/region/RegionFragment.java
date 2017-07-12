package com.bilibili.ui.region;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.region.AppRegionShow;
import com.bilibili.ui.region.viewbinder.RegionBannerItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionBodyItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionFooterItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionHeaderItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionPartitionItemViewBinder;
import com.common.base.BaseMvpFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public class RegionFragment extends BaseMvpFragment<RegionPresenter> implements RegionContract.View {

    public static final String TAG = RegionFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private Items mItems;

    private MultiTypeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_region;
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
                Object item = mItems.get(position);
                return (item instanceof RegionHeaderItemViewBinder.RegionHeader
                        ||item instanceof AppRegionShow.Partition
                        || item instanceof AppRegionShow.Banner
                        || item instanceof RegionFooterItemViewBinder.RegionFooter) ? SPAN_COUNT : 1;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RegionIndexItemDecoration(spanSizeLookup));
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_main));

        mItems = new Items();
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(RegionHeaderItemViewBinder.RegionHeader.class, new RegionHeaderItemViewBinder());
        mAdapter.register(AppRegionShow.Partition.class, new RegionPartitionItemViewBinder());
        mAdapter.register(AppRegionShow.Body.class, new RegionBodyItemViewBinder());
        mAdapter.register(AppRegionShow.Banner.class, new RegionBannerItemViewBinder());
        mAdapter.register(RegionFooterItemViewBinder.RegionFooter.class, new RegionFooterItemViewBinder());
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
        mItems.clear();
        mItems.addAll(items);
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }


}
