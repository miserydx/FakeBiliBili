package com.bilibili.ui.test.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.bilibili.R;
import com.common.base.BaseFragment;
import com.common.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jiayiyang on 17/4/14.
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private NewsPagerAdapter adapter;
    private List<BaseMvpFragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"直播", "推荐", "追番", "分区", "动态", "发现"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {
        mFragments.add(new NewsPageFragment2());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        mFragments.add(new NewsPageFragment());
        adapter = new NewsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
