package com.bilibili.ui.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.event.TabSelectedEvent;
import com.bilibili.model.event.ToggleDrawerEvent;
import com.bilibili.ui.bangumi.BangumiFragment;
import com.bilibili.ui.live.LiveFragment;
import com.bilibili.ui.recommed.RecommendFragment;
import com.bilibili.ui.test.fragment.PlaceHolderFragment;
import com.common.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页主Fragment
 * Created by jiayiyang on 17/4/14.
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Inject
    LiveFragment mLiveFragmet;
    @Inject
    RecommendFragment mRecommendFragment;
    @Inject
    BangumiFragment mBangumiFragment;

    private MainPagerAdapter adapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        setUpToolBar(mToolbar);
        mTitles = getResources().getStringArray(R.array.home_sections);
        initChildFragment();
        adapter = new MainPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
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
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_game_center:
                //TODO
                break;
            case R.id.action_download:
                //TODO
                break;
            case R.id.action_search:
                //TODO
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initChildFragment() {
        mFragments.add(mLiveFragmet);
        mFragments.add(mRecommendFragment);
        mFragments.add(mBangumiFragment);
        mFragments.add(new PlaceHolderFragment());
        mFragments.add(new PlaceHolderFragment());
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TabSelectedEvent event){
        if(event.getPosition() == MainActivity.FIRST){
            setUpToolBar(mToolbar);
        }
    }

}
