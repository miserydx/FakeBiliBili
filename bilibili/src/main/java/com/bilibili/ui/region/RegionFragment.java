package com.bilibili.ui.region;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.bean.region.AppRegionShow;
import com.bilibili.model.event.TabSelectedEvent;
import com.bilibili.model.event.ToggleDrawerEvent;
import com.bilibili.ui.main.MainActivity;
import com.bilibili.ui.region.viewbinder.RegionBannerItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionBodyItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionFooterItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionHeaderItemViewBinder;
import com.bilibili.ui.region.viewbinder.RegionPartitionItemViewBinder;
import com.bilibili.widget.adapter.CommonAdapter;
import com.bilibili.widget.textview.AlwaysCenterTextView;
import com.common.base.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;

/**
 * Created by Android_ZzT on 17/7/6.
 */

public class RegionFragment extends BaseMvpFragment<RegionPresenter> implements RegionContract.View {

    public static final String TAG = RegionFragment.class.getSimpleName();

    private static final int SPAN_COUNT = 2;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    AlwaysCenterTextView tvTitle;
    @BindView(R.id.rv_region)
    RecyclerView mRecyclerView;

    private CommonAdapter mAdapter;

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
        tvTitle.setText(getString(R.string.section_region));
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = mAdapter.getItems().get(position);
                return item instanceof AppRegionShow.Body ? 1 : SPAN_COUNT;
            }
        };
        layoutManager.setSpanSizeLookup(spanSizeLookup);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RegionIndexItemDecoration());
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_main));
        mAdapter = new CommonAdapter();
        mAdapter.register(RegionHeaderItemViewBinder.RegionHeader.class, new RegionHeaderItemViewBinder());
        mAdapter.register(AppRegionShow.Partition.class, new RegionPartitionItemViewBinder());
        mAdapter.register(AppRegionShow.Body.class, new RegionBodyItemViewBinder());
        mAdapter.register(AppRegionShow.Banner.class, new RegionBannerItemViewBinder());
        mAdapter.register(RegionFooterItemViewBinder.RegionFooter.class, new RegionFooterItemViewBinder());
        mAdapter.setScrollSaveStrategyEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onDataUpdated(Items items) {
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadFailed() {
        mAdapter.showLoadFailed();
    }

    @OnClick({R.id.ll_top_menu_nav})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top_menu_nav:
                ToggleDrawerEvent event = new ToggleDrawerEvent();
                EventBus.getDefault().post(event);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.region, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_download:
                //TODO
                break;
            case R.id.action_search:
                //TODO
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TabSelectedEvent event){
        if(event.getPosition() == MainActivity.SECOND){
            setUpToolBar(mToolbar);
        }
    }

}
