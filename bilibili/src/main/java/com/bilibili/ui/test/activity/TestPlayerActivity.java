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

    private static final String URL = "http://wsvc.acgvideo.com/container/dsp/0/4b/4bcfe11b4fcc53e37fdf828af0290dc6_56_0.mp4?wsTime=1502188754&wsSecret2=8d1f37a891abdc288de61188aaec32ff&oi=2071070392&rate=0";

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

        setSupportActionBar(mToolBar);
        mToolBar.setTitle("TestIjkPlayer");

        //init controller
        mMediaController = new AndroidMediaController(this, false);
        ActionBar actionBar = getSupportActionBar();
        mMediaController.setSupportActionBar(actionBar);

        //init videoView
        Log.d("zzt","video view --->" + mVideoView);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);
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
