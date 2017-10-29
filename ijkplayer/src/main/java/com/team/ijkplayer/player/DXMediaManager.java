package com.team.ijkplayer.player;

import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by miserydx on 17/9/25.
 */

public class DXMediaManager implements IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnCompletionListener, IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnErrorListener, IMediaPlayer.OnBufferingUpdateListener {

    public static final int MESSAGE_PREPARE = 0;
    public static final int MESSAGE_SET_DISPLAY = 1;
    public static final int MESSAGE_RELEASE = 2;
    public static final int MESSAGE_START = 3;
    public static final int MESSAGE_PAUSE = 4;

    private static String TAG = DXMediaManager.class.getSimpleName();

    private static DXMediaManager instance;

    /**
     * IjkPlayer
     **/
    private IjkMediaPlayer mMediaPlayer;

    /**
     * 播放器线程
     **/
    private HandlerThread mMediaHandlerThread;

    /**
     * 播放器线程Handler
     **/
    private MediaHandler mMediaHandler;

    /**
     * 主线程Handler
     **/
    private Handler mainThreadHandler;

    /**
     * 播放器监听回调
     **/
    private DXMediaPlayerListener mListener;

    private DXMediaManager() {
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler(mMediaHandlerThread.getLooper());
        mainThreadHandler = new Handler();
    }

    public static synchronized DXMediaManager getInstance() {
        if (instance == null) {
            instance = new DXMediaManager();
        }
        return instance;
    }

    public void setDXMediaPlayerListener(DXMediaPlayerListener listener) {
        mListener = listener;
    }

    /**
     * 播放器设置surface
     *
     * @param surface 播放器绘制需要的surface
     */
    public void setDisplay(Surface surface) {
        Message m = new Message();
        m.what = MESSAGE_SET_DISPLAY;
        m.obj = surface;
        mMediaHandler.sendMessage(m);
    }

    /**
     * 准备播放
     *
     * @param ijkDataSource
     */
    public void prepare(IjkDataSource ijkDataSource) {
        Message m = new Message();
        m.what = MESSAGE_PREPARE;
        m.obj = ijkDataSource;
        mMediaHandler.sendMessage(m);
    }

    /**
     * 调用播放器播放
     */
    public void play() {
        mMediaHandler.sendEmptyMessage(MESSAGE_START);
    }

    /**
     * 调用播放器暂停
     */
    public void pause() {
        mMediaHandler.sendEmptyMessage(MESSAGE_PAUSE);
    }

    /**
     * 准备播放时初始化IjkPlayer
     *
     * @param msg
     */
    private void initIjkPlayer(Message msg) {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
            mMediaPlayer = new IjkMediaPlayer();
            mMediaPlayer.setDataSource(((IjkDataSource) msg.obj).getUrl(), ((IjkDataSource) msg.obj).getMapHeadData());
            //设置监听
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnInfoListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnSeekCompleteListener(this);

            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);

            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        mMediaHandler.sendEmptyMessage(MESSAGE_RELEASE);
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onPrepared();
                }
            }
        });
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, final int width, final int height, final int sar_num, final int sar_den) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onVideoSizeChanged(width, height);
                }
            }
        });
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onCompletion();
                }
            }
        });
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, final int what, final int extra) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onError(what, extra);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, final int what, final int extra) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onInfo(what, extra);
                }
            }
        });
        return true;
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, final int i) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onBufferingUpdate(i);
                }
            }
        });
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onSeekComplete();
                }
            }
        });
    }

    private class MediaHandler extends Handler {

        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_PREPARE:
                    Log.d(TAG, "VideoPlayer : MESSAGE_PREPARE");
                    initIjkPlayer(msg);
                    break;
                case MESSAGE_SET_DISPLAY:
                    Log.d(TAG, "VideoPlayer : MESSAGE_SET_DISPLAY");
                    if (msg.obj == null) {
                        mMediaPlayer.setDisplay(null);
                    } else {
                        Surface surface = (Surface) msg.obj;
                        if (surface.isValid()) {
                            mMediaPlayer.setSurface(surface);
                        }
                    }
                    break;
                case MESSAGE_START:
                    Log.d(TAG, "VideoPlayer : MESSAGE_START");
                    mMediaPlayer.start();
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onStart();
                        }
                    });
                    break;
                case MESSAGE_PAUSE:
                    Log.d(TAG, "VideoPlayer : MESSAGE_PAUSE");
                    mMediaPlayer.pause();
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onPause();
                        }
                    });
                    break;
                case MESSAGE_RELEASE:
                    Log.d(TAG, "VideoPlayer : MESSAGE_RELEASE");
                    mMediaPlayer.release();
                    break;
            }
        }
    }

    interface DXMediaPlayerListener {
        void onPrepared();

        void onVideoSizeChanged(int width, int height);

        void onCompletion();

        void onError(int what, int extra);

        void onInfo(int what, int extra);

        void onBufferingUpdate(int i);

        void onSeekComplete();

        void onStart();

        void onPause();
    }

}
