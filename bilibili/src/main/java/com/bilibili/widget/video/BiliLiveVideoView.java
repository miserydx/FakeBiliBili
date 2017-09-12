package com.bilibili.widget.video;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bilibili.R;
import com.bilibili.ui.live.liveplay.LiveDanMuMsgCallback;
import com.bilibili.widget.danmu.live.LiveDanMuReceiver;
import com.bilibili.widget.danmu.live.entity.DanMuMSGEntity;
import com.common.util.OrientationUtil;
import com.common.util.SizeUtil;
import com.common.util.StatusBarUtil;
import com.common.util.SystemUtil;
import com.team.ijkplayer.widget.media.AndroidMediaController;
import com.team.ijkplayer.widget.media.IjkVideoView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by miserydx on 17/9/8.
 */
//TODO 现在是简易测试版，下一步重构播放器，分离UI与MediaPayer，弹幕系统外置，处理MediaPlayer和socket连接的生命周期（资源释放）
public class BiliLiveVideoView extends FrameLayout {

    private IjkVideoView mVideoView;

    private AndroidMediaController mMediaController;

    private boolean isControlViewShowing = false;

    private FrameLayout bgControlTop;

    private RelativeLayout bgControlBottom;

    private ImageView ivPlaceholder;

    private DanmakuView danmuView;

    private DanmakuContext danmakuContext;

    private ViewTreeObserver.OnGlobalLayoutListener portraitGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.d("misery","portraitGlobalLayoutListener");
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)getLayoutParams();
            lp.height = SystemUtil.dp2px(getContext(), 200);
            setLayoutParams(lp);
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener landScapeGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.d("misery","landScapeGlobalLayoutListener");
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(lp);
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    };

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    public BiliLiveVideoView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BiliLiveVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BiliLiveVideoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_live_video, this);
        ImageView ivFullScreen = (ImageView) view.findViewById(R.id.iv_fullscreen);
        mVideoView = (IjkVideoView) view.findViewById(R.id.video_view);
        bgControlTop = (FrameLayout) view.findViewById(R.id.bg_control_top);
        bgControlBottom = (RelativeLayout) view.findViewById(R.id.bg_control_bottom);
        ivPlaceholder = (ImageView) view.findViewById(R.id.iv_placeholder);
        danmuView = (DanmakuView) view.findViewById(R.id.danmu_view);
        ivFullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OrientationUtil.getScreenOrientation((Activity) getContext()) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        mVideoView.setOnVideoControlListener(new IjkVideoView.OnVideoControlListener() {
            @Override
            public void onTouchEvent(MotionEvent ev) {
                if (isControlViewShowing) {
                    isControlViewShowing = false;
                    bgControlTop.setVisibility(GONE);
                    bgControlBottom.setVisibility(GONE);
                    StatusBarUtil.hideStatusBar((Activity) getContext());
                } else {
                    isControlViewShowing = true;
                    bgControlTop.setVisibility(VISIBLE);
                    bgControlBottom.setVisibility(VISIBLE);
                    StatusBarUtil.showStatusBar((Activity) getContext());
                }
            }
        });
    }

    public void init(Toolbar toolBar, ActionBar actionBar, String url, final String roomId) {
        //init鬼畜小电视
        ((AnimationDrawable) ivPlaceholder.getDrawable()).start();

        //init IjkPlayer
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

//        setSupportActionBar(mToolBar);
//        toolBar.setTitle("TestIjkPlayer");

        //init controller
        mMediaController = new AndroidMediaController(getContext(), false);
//        ActionBar actionBar = getSupportActionBar();
//        mMediaController.setSupportActionBar(actionBar);

        //init videoView
        mVideoView.setMediaController(mMediaController);
//        mVideoView.setHudView(mHudView);
        mVideoView.setVideoPath(url);
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                StatusBarUtil.hideStatusBar((Activity) getContext());
                ((AnimationDrawable) ivPlaceholder.getDrawable()).stop();
                ivPlaceholder.setVisibility(View.GONE);
                mVideoView.setVisibility(View.VISIBLE);
                mVideoView.start();
                connectDanmu(roomId);
            }
        });
        danmuView.enableDanmakuDrawingCache(true);//打开绘图缓存，提升绘制效率
        danmuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                try {
                    danmuView.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuContext.setDuplicateMergingEnabled(true);//设置合并重复弹幕
        danmuView.prepare(parser, danmakuContext);
    }

    public void onConfigurationChanged(final Configuration newConfig) {
        resizeVideoLayout(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT);
    }


    /**
     * 调整视频布局大小
     *
     * @param isPortrait 是否竖屏
     */
    private void resizeVideoLayout(final boolean isPortrait) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isPortrait) {
//                    getViewTreeObserver().addOnGlobalLayoutListener(portraitGlobalLayoutListener);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)getLayoutParams();
                    lp.height = SystemUtil.dp2px(getContext(), 200);
                    setLayoutParams(lp);
                } else {
//                    getViewTreeObserver().addOnGlobalLayoutListener(landScapeGlobalLayoutListener);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    setLayoutParams(lp);
                }
            }
        });
    }

    /**
     * 连接弹幕
     */
    private void connectDanmu(final String roomId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveDanMuReceiver liveDanMuReceiver =
                        new LiveDanMuReceiver("http://live.bilibili.com/" + roomId)
                                .setPrintDebugInfo(true)
                                .addCallback(new LiveDanMuMsgCallback() {
                                    @Override
                                    public void onDanMuMSGPackage(final DanMuMSGEntity danMuMSGEntity) {
                                        super.onDanMuMSGPackage(danMuMSGEntity);
                                        Observable.just(1)
                                                .subscribeOn(Schedulers.newThread()) // 指定 subscribe() 发生在 IO 线程
                                                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                                                .subscribe(new Consumer<Integer>() {
                                                    @Override
                                                    public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                                                        addDanmu(danMuMSGEntity);
                                                    }
                                                });
                                    }
                                });
                try {
                    liveDanMuReceiver.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 添加弹幕
     */
    public void addDanmu(DanMuMSGEntity danmu) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmu.getDanMuContent();
        danmaku.textSize = SizeUtil.sp2px(12 * 1.0f);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmuView.getCurrentTime());
        danmuView.addDanmaku(danmaku);
    }

}
