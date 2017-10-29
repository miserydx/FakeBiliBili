package com.bilibili.ui.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.ui.bangumi.BangumiFragmentKotlin;
import com.bilibili.ui.live.LiveFragment;
import com.bilibili.ui.recommed.RecommendFragment;
import com.bilibili.ui.region.RegionFragment;
import com.bilibili.ui.test.fragment.NewsPageFragment;
import com.common.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 首页主Fragment
 * Created by jiayiyang on 17/4/14.
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.top_menu_nav_ll)
    LinearLayout llTopMenuNavigation;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Inject
    BangumiFragmentKotlin mBangumiFragment;
    @Inject
    RegionFragment mRegionFragment;
    @Inject
    LiveFragment mLiveFragmet;
    @Inject
    RecommendFragment mRecommendFragment;

    private OnInteractionListener mListener;
    private MainPagerAdapter adapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    public interface OnInteractionListener {

        void toggleDrawer();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (MainActivity) activity;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        setHasOptionsMenu(true);
        ((SupportActivity) getActivity()).setSupportActionBar(mToolbar);
        ((SupportActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitles = getResources().getStringArray(R.array.sections);
        initChildFragment();
        adapter = new MainPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, mTitles);
    }

    @OnClick({R.id.top_menu_nav_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_menu_nav_ll:
                mListener.toggleDrawer();
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
        mFragments.add(mRegionFragment);
        mFragments.add(new NewsPageFragment());
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

}
