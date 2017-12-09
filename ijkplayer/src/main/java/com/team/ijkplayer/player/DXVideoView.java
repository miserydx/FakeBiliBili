package com.team.ijkplayer.player;

import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by miserydx on 17/9/20.
 */

public class DXVideoView extends FrameLayout implements DXMediaManager.DXMediaPlayerListener, TextureView.SurfaceTextureListener {

    private static final String TAG = DXVideoView.class.getSimpleName();
    private static final int VIDEO_STATE_ERROR = -1;
    private static final int VIDEO_STATE_IDLE = 0;
    private static final int VIDEO_STATE_PREPARING = 1;
    private static final int VIDEO_STATE_PREPARED = 2;
    private static final int VIDEO_STATE_PLAYING = 3;
    private static final int VIDEO_STATE_PAUSED = 4;
    private static final int VIDEO_STATE_COMPLETED = 5;

    /**
     * 当前状态
     **/
    private int mCurrentState;

    /**
     * 播放地址
     **/
    private String mUrl;

    /**
     * 渲染View宽
     **/
    protected int textureWidth;

    /**
     * 渲染View高
     **/
    protected int textureHeight;

    /**
     * 渲染View
     **/
    private DXTextureView mTextureView;

    /**
     * 声音控制
     **/
    protected AudioManager mAudioManager;

    /**
     * 播放器OnPrepared回调
     */
    private OnPreparedListener mOnPreparedListener;

    /**
     * 播放器OnStart回调
     */
    private OnStartListener mOnStartListener;

    /**
     * 播放器OnPause回调
     */
    private OnPauseListener mOnPauseListener;

    //TODO 设置其他多媒体监听，当其他多媒体播放时，暂停

    public DXVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public DXVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DXVideoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        addRenderView();
        if (!isInEditMode()) {
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        }
        DXMediaManager.getInstance().setDXMediaPlayerListener(this);
    }

    /**
     * 启动参数设置
     *
     * @param url
     */
    public void setUp(String url) {
        mUrl = url;
    }

    /**
     * 播放暂停逻辑
     */
    public void playOrPause() {
        if (TextUtils.isEmpty(mUrl)) {
            return;
        }
        if (mCurrentState == VIDEO_STATE_IDLE || mCurrentState == VIDEO_STATE_COMPLETED) {
            mCurrentState = VIDEO_STATE_PREPARING;
            prepare();
        } else if (mCurrentState == VIDEO_STATE_PREPARED || mCurrentState == VIDEO_STATE_PAUSED) {
            mCurrentState = VIDEO_STATE_PLAYING;
            DXMediaManager.getInstance().play();
        } else if (mCurrentState == VIDEO_STATE_PLAYING) {
            mCurrentState = VIDEO_STATE_PAUSED;
            DXMediaManager.getInstance().pause();
        }
    }

    /**
     * 准备播放
     */
    private void prepare() {
        IjkDataSource ijkDataSource = new IjkDataSource();
        ijkDataSource.setUrl(mUrl);
        ijkDataSource.setMapHeadData(null);
        DXMediaManager.getInstance().prepare(ijkDataSource);
    }

    /**
     * 设置RenderView
     */
    private void addRenderView() {
        mTextureView = new DXTextureView(getContext());
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        mTextureView.setSurfaceTextureListener(this);
//        FrameLayout videoContainer = new FrameLayout(getContext());
//        videoContainer.
        addView(mTextureView, lp);
//        addView(videoContainer, lp);
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        mOnPreparedListener = listener;
    }

    public void setOnStartListener(OnStartListener listener) {
        mOnStartListener = listener;
    }

    public void setOnPauseListener(OnPauseListener listener) {
        mOnPauseListener = listener;
    }

    public void release() {
        DXMediaManager.getInstance().release();
    }

    @Override
    public void onPrepared() {
        mCurrentState = VIDEO_STATE_PREPARED;
        if (mOnPreparedListener != null) {
            mOnPreparedListener.onPrepared();
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height) {
        Log.d(TAG, "onVideoSizeChanged");
        //根据视频宽高比重置播放器大小
        mTextureView.setVideoSize(new Point(width, height));
        textureWidth = width;
        textureHeight = height;
    }

    @Override
    public void onCompletion() {
        Log.d(TAG, "onCompletion");
        mCurrentState = VIDEO_STATE_COMPLETED;
    }

    @Override
    public void onError(int what, int extra) {
        Log.d(TAG, "onError what : " + what + " & extra : " + extra);
    }

    @Override
    public void onInfo(int what, int extra) {
        Log.d(TAG, "onInfo what : " + what + " & extra : " + extra);
    }

    @Override
    public void onBufferingUpdate(int i) {
        Log.d(TAG, "onBufferingUpdate i:" + i);
    }

    @Override
    public void onSeekComplete() {
        Log.d(TAG, "onSeekComplete");
    }

    @Override
    public void onStart() {
        if (mOnStartListener != null) {
            mOnStartListener.onVideoStart();
        }
    }

    @Override
    public void onPause() {
        if (mOnPauseListener != null) {
            mOnPauseListener.onVideoPause();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        DXMediaManager.getInstance().setDisplay(new Surface(surface));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        DXMediaManager.getInstance().setDisplay(null);
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(int width, int height);
    }

    public interface OnCompletionListener {
        void onCompletion();
    }

    public interface OnErrorListener {
        void onError(int what, int extra);
    }

    public interface OnInfoListener {
        void onInfo(int what, int extra);
    }

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(int i);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete();
    }

    public interface OnStartListener {
        void onVideoStart();
    }

    public interface OnPauseListener {
        void onVideoPause();
    }
}
