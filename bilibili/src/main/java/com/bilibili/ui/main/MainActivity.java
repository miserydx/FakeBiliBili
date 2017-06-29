package com.bilibili.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bilibili.App;
import com.bilibili.R;
import com.common.base.IBaseMvpActivity;
import com.common.util.StatusBarUtil;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity implements IBaseMvpActivity<MainPresenter>, MainContract.View, MainFragment.OnInteractionListener {

    @Inject
    MainPresenter mPresenter;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.main_nav_view)
    NavigationView mNavigationView;

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
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragment mainFragment = new MainFragment();
        loadRootFragment(R.id.main_container, mainFragment);
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    @Override
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
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
}
