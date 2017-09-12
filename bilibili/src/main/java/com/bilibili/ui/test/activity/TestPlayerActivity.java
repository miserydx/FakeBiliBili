package com.bilibili.ui.test.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TableLayout;

import com.bilibili.R;
import com.common.base.IBaseActivity;
import com.team.ijkplayer.application.Settings;
import com.team.ijkplayer.widget.media.AndroidMediaController;
import com.team.ijkplayer.widget.media.IjkVideoView;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Android_ZzT on 17/8/7.
 */

public class TestPlayerActivity extends SupportActivity implements IBaseActivity {

//    private static final String URL = "http://txy.live-play.acgvideo.com/live-txy/874149/live_6837108_8470533.flv?wsSecret=af1827a677bb24d0ede358cb06fe5302&wsTime=1504598358";
//    private static final String URL = "http://upos-hz-mirrorks3.acgvideo.com/upgcxcode/83/52/23025283/23025283-1-64.flv?um_deadline=1504604797&platform=android&rate=377325&oi=1875901889&um_sign=27f0e8dae6396a8c204e647e50b53033&gen=playurl&os=ks3";
//    private static final String URL = "http://27.221.106.8/vg3/0/df/19953379-1-hd.mp4?expires=1504606200&platform=android&ssig=4kneKdPXaw6tdsS-4w0gKw&oi=1875901889&nfa=xUeqF7Ik01EZo/PlAaYcYg==&dynamic=1&hfa=2073919470&hfb=NzUxMjI5MWJlMDBjMDY0YTQxNjFjMTJiYWE0MjEwYmQ=";
    private static final String URL = "http://txy.live-play.acgvideo.com/live-txy/954299/live_26541399_9681153.flv?wsSecret=7daf286920781957d3f31bd404d70adb&wsTime=1505024796";

    @BindView(R.id.video_view)
    IjkVideoView mVideoView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.hud_view)
    TableLayout mHudView;

    private AndroidMediaController mMediaController;

    private Settings mSettings;

    private boolean mBackPressed;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_player;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initViewAndEvent() {
        mSettings = new Settings(this);

        //init IjkPlayer
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

//        setSupportActionBar(mToolBar);
//        mToolBar.setTitle("TestIjkPlayer");

        //init controller
        mMediaController = new AndroidMediaController(this, false);
//        ActionBar actionBar = getSupportActionBar();
//        mMediaController.setSupportActionBar(actionBar);

        //init videoView
        Log.d("zzt","video view --->" + mVideoView);
        mVideoView.setMediaController(mMediaController);
//        mVideoView.setHudView(mHudView);
        mVideoView.setVideoPath(URL);
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                mVideoView.start();
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        mBackPressed = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
