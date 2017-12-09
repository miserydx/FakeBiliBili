package com.bilibili.widget.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bilibili.R;
import com.bilibili.ui.live.liveplay.LiveDanMuMsgCallback;
import com.bilibili.util.CommonConsumer;
import com.bilibili.util.RxJavaUtil;
import com.bilibili.widget.danmu.live.LiveDanMuReceiver;
import com.bilibili.widget.danmu.live.entity.DanMuMSGEntity;
import com.common.util.SizeUtil;
import com.common.util.StatusBarUtil;
import com.common.widget.MaterialLoadingView;
import com.team.ijkplayer.player.DXBaseVideoPlayer;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by miserydx on 17/10/20.
 */

public class LiveVideoPlayer extends DXBaseVideoPlayer {

    /**
     * 烈焰弹幕使
     */
    private DanmakuView danmakuView;

    /**
     * 竖屏水印
     */
    private ImageView ivWaterMark;

    /**
     * 竖屏控制面板
     */
    private ConstraintLayout containerControl;

    /**
     * 鬼畜小电视
     */
    private ImageView ivPlaceholder;

    /**
     * 竖屏播放按钮
     */
    private ImageView ivStart;

    /**
     * 竖屏全屏按钮
     */
    private ImageView ivFullscreen;

    /**
     * LoadingView
     */
    private MaterialLoadingView loadingView;

    /**
     * 全屏容器
     */
    private ViewGroup mFullscreenContainer;

    /**
     * 全屏控制面板
     */
    private ConstraintLayout mFullscreencontainerControl;

    /**
     * 全屏后退按钮
     */
    private ImageView ivBack;

    /**
     * 全屏设置按钮
     */
    private ImageView ivFullscreenSetting;

    /**
     * 全屏退出全屏按钮
     */
    private ImageView quitFullscreenIv;

    private DanmakuContext danmakuContext;

    public LiveVideoPlayer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LiveVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LiveVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        danmakuView = new DanmakuView(context);
        addView(danmakuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initNormalView(context);
        initFullscreenView(context);
    }

    private void initNormalView(final Context context) {
        View.inflate(context, R.layout.layout_live_control, this);
        ivWaterMark = (ImageView) findViewById(R.id.water_mark_iv);
        containerControl = (ConstraintLayout) findViewById(R.id.control_container);
        ivPlaceholder = (ImageView) findViewById(R.id.placeholder_iv);
        ivStart = (ImageView) findViewById(R.id.start_iv);
        ivFullscreen = (ImageView) findViewById(R.id.fullscreen_iv);
        //鬼畜小电视ON
        ((AnimationDrawable) ivPlaceholder.getDrawable()).start();
        //监听事件
        ivStart.setOnClickListener(this);
        ivFullscreen.setOnClickListener(this);
    }

    private void initFullscreenView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mFullscreenContainer = (ViewGroup) inflater.inflate(R.layout.layout_live_fullscreen_container, null);
        mFullscreencontainerControl = (ConstraintLayout) mFullscreenContainer.findViewById(R.id.control_container);
        ivBack = (ImageView) mFullscreenContainer.findViewById(R.id.back_iv);
        ivFullscreenSetting = (ImageView) mFullscreenContainer.findViewById(R.id.fullscreen_setting_iv);
        quitFullscreenIv = (ImageView) mFullscreenContainer.findViewById(R.id.quit_fullscreen_iv);
        //监听事件
        ivBack.setOnClickListener(this);
        ivFullscreenSetting.setOnClickListener(this);
        quitFullscreenIv.setOnClickListener(this);
    }

    public void setupToolbar(Toolbar toolbar) {
        ConstraintSet constraintSet = new ConstraintSet();//新建一个ConstraintSet
        containerControl.addView(toolbar);
        constraintSet.clone(containerControl);
        constraintSet.constrainWidth(toolbar.getId(), ConstraintLayout.LayoutParams.MATCH_PARENT);
        constraintSet.constrainHeight(toolbar.getId(), toolbar.getLayoutParams().height);
        constraintSet.connect(toolbar.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(toolbar.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(toolbar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.applyTo(containerControl);
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        //鬼畜小电视OFF
        ((AnimationDrawable) ivPlaceholder.getDrawable()).stop();
        ivPlaceholder.setVisibility(View.GONE);
        //其他UI
        ivWaterMark.setVisibility(View.VISIBLE);
        setBackgroundResource(R.color.black);
        StatusBarUtil.hideStatusBar((Activity) getContext());
        playOrPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        ivStart.setImageResource(R.drawable.ic_tv_stop);
    }

    @Override
    public void onPause() {
        super.onPause();
        ivStart.setImageResource(R.drawable.ic_tv_play);
    }

    @Override
    protected ViewGroup getFullscreenContainer() {
        return mFullscreenContainer;
    }

    @Override
    protected void onNormalControlViewShow() {
        StatusBarUtil.showStatusBar((Activity) getContext());
        setViewVisibility(View.VISIBLE, containerControl);
    }

    @Override
    protected void onNormalControlViewHide() {
        StatusBarUtil.hideStatusBar((Activity) getContext());
        setViewVisibility(View.INVISIBLE, containerControl);
    }

    @Override
    protected void onFullScreenControlViewShow() {
        setViewVisibility(View.VISIBLE, mFullscreencontainerControl);
    }

    @Override
    protected void onFullScreenControlViewHide() {
        setViewVisibility(View.INVISIBLE, mFullscreencontainerControl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fullscreen_iv:
                setViewVisibility(View.INVISIBLE, containerControl);
                startWindowFullscreen();
                break;
            case R.id.start_iv:
                playOrPause();
                break;
            case R.id.fullscreen_setting_iv:
                //TODO PopupWindow
                break;
            case R.id.back_iv:
            case R.id.quit_fullscreen_iv:
                quitWindowFullscreen();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void setViewVisibility(int visibility, View... views) {
        for (View view : views) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 按返回键根据是否全屏返回是否拦截回退操作
     *
     * @return 是否拦截回退操作
     */
    public boolean onBackPressed() {
        if (isFullScreen()) {
            quitWindowFullscreen();
            return true;
        }
        return false;
    }

    public void initDanmakuView() {
        danmakuView.enableDanmakuDrawingCache(true);//打开绘图缓存，提升绘制效率
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                try {
                    danmakuView.start();
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
        danmakuView.prepare(new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        }, danmakuContext);
        registerDanmuCallback();
    }

    /**
     * 注册直播弹幕监听
     */
    private void registerDanmuCallback() {
        LiveDanMuReceiver.getInstance()
                .setPrintDebugInfo(true)
                .addCallback(new LiveDanMuMsgCallback() {
                    @Override
                    public void onDanMuMSGPackage(final DanMuMSGEntity danMuMSGEntity) {
                        super.onDanMuMSGPackage(danMuMSGEntity);
                        RxJavaUtil.runOnUiThread(new CommonConsumer() {
                            @Override
                            public void accept(Object o) throws Exception {
                                addDanmu(danMuMSGEntity);
                            }
                        });
                    }
                });
    }

    /**
     * 添加弹幕
     */
    public void addDanmu(DanMuMSGEntity danmu) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmu.getDanMuContent();
        danmaku.textSize = SizeUtil.sp2px(12 * 1.0f);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        danmakuView.addDanmaku(danmaku);
    }

    @Override
    public void release() {
        super.release();
    }
}
