package com.bilibili.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.model.event.BaseSwitchToolBarMenuEvent;
import com.bilibili.model.event.SwitchMainMenuEvent;
import com.bilibili.model.event.SwitchRegionMenuEvent;
import com.bilibili.model.event.ToggleDrawerEvent;
import com.bilibili.ui.region.RegionFragment;
import com.bilibili.widget.bottombar.TabEntity;
import com.bilibili.ui.test.fragment.PlaceHolderFragment;
import com.bilibili.widget.bottombar.BottomBar;
import com.bilibili.widget.bottombar.CustomTabEntity;
import com.common.base.IBaseMvpActivity;
import com.common.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements IBaseMvpActivity<MainPresenter>, MainContract.View {

    @Inject
    MainPresenter mPresenter;
    @Inject
    MainFragment mainFragment;
    @Inject
    RegionFragment regionFragment;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.main_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.main_nav_view)
    NavigationView mNavigationView;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles;
    private int[] mIconSelectIds = {
            R.drawable.ic_home_selected, R.drawable.ic_category_selected,
            R.drawable.ic_dynamic_selected, R.drawable.ic_communicate_selected};
    private int[] mIconUnselectIds = {
            R.drawable.ic_home_unselected, R.drawable.ic_category_unselected,
            R.drawable.ic_dynamic_unselected, R.drawable.ic_communicate_unselected};

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public MainPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewAndEvent() {
        StatusBarUtil.setColorForDrawerLayout(this, getResources().getColor(R.color.theme_color_primary), mFrameLayout);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //隐藏NavigationView右侧滚动条
        NavigationMenuView navigationMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
        mTitles = getResources().getStringArray(R.array.main_sections);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBottomBar.setTabEntities(mTabEntities);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                Fragment curFragment = mFragments[position];
                BaseSwitchToolBarMenuEvent event = null;
                if (curFragment instanceof MainFragment) {
                    event = new SwitchMainMenuEvent();
                } else if (curFragment instanceof RegionFragment) {
                    event = new SwitchRegionMenuEvent();
                }
                if (event != null) {
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments[0] = mainFragment;
        mFragments[1] = regionFragment;
        mFragments[2] = new PlaceHolderFragment();
        mFragments[3] = new PlaceHolderFragment();
        loadMultipleRootFragment(R.id.main_container, 0,
                mFragments[0],
                mFragments[1],
                mFragments[2],
                mFragments[3]);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ToggleDrawerEvent event) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
