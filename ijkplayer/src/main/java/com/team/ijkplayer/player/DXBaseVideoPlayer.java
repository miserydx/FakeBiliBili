package com.team.ijkplayer.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.team.ijkplayer.R;
import com.team.ijkplayer.utils.StatusBarUtil;
import com.team.ijkplayer.utils.Utils;

/**
 * Created by miserydx on 17/9/20.
 */

public abstract class DXBaseVideoPlayer extends DXVideoView implements View.OnClickListener {

    private static final String TAG = DXBaseVideoPlayer.class.getSimpleName();

    /**
     * 控制View的状态
     */
    private int mControViewVisible = GONE;

    /**
     * 是否为全屏
     */
    private boolean isFullScreen;

    /**
     * 播放器父容器
     **/
    private ViewGroup mParent;

    /**
     * 在父容器中的索引
     **/
    private int mIndexInParent;

    /**
     * 全屏容器ID
     **/
    private int fullScreenContainerID = NO_ID;

    /**
     * 播放器布局参数
     **/
    private ViewGroup.LayoutParams mLayoutParams;

    //TODO 设置其他多媒体监听，当其他多媒体播放时，暂停

    public DXBaseVideoPlayer(@NonNull Context context) {
        super(context);
    }

    public DXBaseVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DXBaseVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStart() {
        super.onStart();
        setOnClickListener(this);
    }

    /**
     * 进入全屏状态
     */
    public void startWindowFullscreen() {
        isFullScreen = true;
        mControViewVisible = GONE;
        StatusBarUtil.hideStatusBar((Activity) getContext());

        ViewGroup fullScreenContainer;
        if (getFullscreenContainer() != null) {
            fullScreenContainer = getFullscreenContainer();
        } else {
            fullScreenContainer = new FrameLayout(getContext());
        }

        //记录信息
        mParent = ((ViewGroup) getParent());
        mIndexInParent = mParent.indexOfChild(this);
        mLayoutParams = getLayoutParams();

        //将播放器从当前容器中移出
        mParent.removeView(this);
        /*设置可见属性为GONE，避免当前父布局为ConstraintLayout时removeView并没有移除
           mVariableDimensionsWidgets集合中对应DXVideoPlayer的ConstraintWidget，所导致onMeasure中出现的
           LayoutParams类型转换错误异常*/
        mParent.setVisibility(GONE);

        //将全屏播放器放到全屏容器之中
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        fullScreenContainer.addView(this, 0, layoutParams);

        //如果容器没有id，设置一个ID给容器
        fullScreenContainerID = fullScreenContainer.getId();
        if (fullScreenContainerID == NO_ID) {
            fullScreenContainerID = R.id.fullscreen_container_id;
            fullScreenContainer.setId(fullScreenContainerID);
        }

        //将全屏容器添加到contentView中
        ViewGroup contentView = getContentView();
        FrameLayout.LayoutParams contentViewLp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        );
        contentView.addView(fullScreenContainer, contentViewLp);

//        /****/
//        FrameLayout frameLayout = (FrameLayout)getParent();
//        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //根据视频宽高大小判断是否旋转
        if (textureWidth > textureHeight) {
            ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private ViewGroup getContentView() {
        return (ViewGroup) (Utils.scanForActivity(getContext())).findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * 退出全屏状态
     */
    public void quitWindowFullscreen() {
        isFullScreen = false;
        mControViewVisible = GONE;
        StatusBarUtil.hideStatusBar((Activity) getContext());

        //获取全屏容器
        ViewGroup fullScreenContainer = (ViewGroup) getContentView().findViewById(fullScreenContainerID);

        //移除全屏容器
        if (fullScreenContainer != null) {
            fullScreenContainer.removeView(this);
            getContentView().removeView(fullScreenContainer);
        }

        //将播放器添加到原来的容器中
        mParent.addView(this, mIndexInParent, mLayoutParams);
        mParent.setVisibility(VISIBLE);

//        /****/
//        FrameLayout frameLayout = (FrameLayout)getParent();
//        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(getContext(), 200)));

        //竖屏
        ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 是否全屏状态
     */
    public boolean isFullScreen() {
        return isFullScreen;
    }

    @Override
    public void onClick(View v) {
        if (isFullScreen()) {
            if (mControViewVisible == GONE) {
                onFullScreenControlViewShow();
                mControViewVisible = VISIBLE;
            } else {
                onFullScreenControlViewHide();
                mControViewVisible = GONE;
            }
        } else {
            if (mControViewVisible == GONE) {
                onNormalControlViewShow();
                mControViewVisible = VISIBLE;
            } else {
                onNormalControlViewHide();
                mControViewVisible = GONE;
            }
        }
    }

    protected abstract ViewGroup getFullscreenContainer();

    protected abstract void onNormalControlViewShow();

    protected abstract void onNormalControlViewHide();

    protected abstract void onFullScreenControlViewShow();

    protected abstract void onFullScreenControlViewHide();

}
