package com.bilibili.ui.test.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bilibili.R;
import com.common.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private NewsPagerAdapter adapter;
    private List<NewsPageFragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"直播", "推荐", "追番", "分区", "动态", "发现"};

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {
        for (String s : mTitles) {
            mFragments.add(new NewsPageFragment());
        }
        adapter = new NewsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, mTitles);
    }

    private class NewsPagerAdapter extends FragmentPagerAdapter {

        public NewsPagerAdapter(FragmentManager fm) {
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
