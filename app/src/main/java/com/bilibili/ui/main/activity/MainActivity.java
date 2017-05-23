package com.bilibili.ui.main.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bilibili.R;
import com.bilibili.base.IBaseMvpActivity;
import com.bilibili.di.component.ActivityComponent;
import com.bilibili.ui.main.fragment.MainFragment;
import com.bilibili.ui.main.mvp.contract.MainContract;
import com.bilibili.ui.main.mvp.presenter.MainPresenter;
import com.bilibili.util.StatusBarUtil;

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
    public void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
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
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    // Handle the camera action
                } else if (id == R.id.nav_gallery) {

                } else if (id == R.id.nav_slideshow) {

                } else if (id == R.id.nav_manage) {

                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //content布局随抽屉布局移动
                //获取屏幕的宽高
//                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
//                mLinearLayout.layout(mNavigationView.getRight(), 0, mNavigationView.getRight() +  display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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
