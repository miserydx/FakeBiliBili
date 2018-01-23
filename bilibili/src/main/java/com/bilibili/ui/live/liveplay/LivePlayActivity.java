package com.bilibili.ui.live.liveplay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import com.bilibili.App;
import com.bilibili.R;
import com.bilibili.ui.live.liveplay.fragment.LiveDanmuFragment;
import com.bilibili.ui.test.fragment.PlaceHolderFragment;
import com.bilibili.util.InflateUtil;
import com.bilibili.widget.danmu.live.LiveDanMuReceiver;
import com.bilibili.widget.video.LiveVideoPlayer;
import com.common.base.IBaseMvpActivity;
import com.team.ijkplayer.player.DXVideoView;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by miserydx on 17/12/20.
 */

public class LivePlayActivity extends SupportActivity implements IBaseMvpActivity<LivePlayPresenter>, LivePlayContract.View,
        DXVideoView.OnPreparedListener {

    public static String TAG = LivePlayActivity.class.getSimpleName();

    private static String TYPE_LIVE_URL = "type_live_url";
    private static String TYPE_LIVE_ROOM_ID = "type_live_room_id";

    public static void startActivity(Context context, String url, int roomId) {
        Intent intent = new Intent(context, LivePlayActivity. class);
        intent.putExtra(TYPE_LIVE_URL, url);
        intent.putExtra(TYPE_LIVE_ROOM_ID, roomId);
        context.startActivity(intent);
    }

    @Inject
    LivePlayPresenter mPresenter;
    @BindView(R.id.live_video_player)
    LiveVideoPlayer videoPlayer;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private LivePlayPagerAdapter adapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_play;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    public LivePlayPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViewAndEvent() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        String dynamicUrl = getIntent().getStringExtra(TYPE_LIVE_URL);
        int roomId = getIntent().getIntExtra(TYPE_LIVE_ROOM_ID, -1);
        initToolbar();
        videoPlayer.setOnPreparedListener(this);
        videoPlayer.setUp(dynamicUrl);
        videoPlayer.initDanmakuView();
        mTitles = getResources().getStringArray(R.array.live_play);
        initChildFragment();
        adapter = new LivePlayPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        connectDanmu(roomId);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) InflateUtil.inflate(getLayoutInflater(), R.layout.layout_live_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setPadding(0, com.common.util.StatusBarUtil.getStatusBarHeight(this), 0, 0);
        videoPlayer.setupToolbar(mToolbar);
    }

    private void initChildFragment() {
        mFragments.add(LiveDanmuFragment.newInstance());
        mFragments.add(new PlaceHolderFragment());
        mFragments.add(new PlaceHolderFragment());
        mFragments.add(new PlaceHolderFragment());
    }

    private void connectDanmu(int roomId) {
        try {
            LiveDanMuReceiver.getInstance().connect(roomId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        videoPlayer.playOrPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        videoPlayer.playOrPause();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        videoPlayer.release();
        try {
            LiveDanMuReceiver.getInstance().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (videoPlayer.onBackPressed()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.live_menu, menu);
        return true;
    }

    private class LivePlayPagerAdapter extends FragmentPagerAdapter {

        public LivePlayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
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
