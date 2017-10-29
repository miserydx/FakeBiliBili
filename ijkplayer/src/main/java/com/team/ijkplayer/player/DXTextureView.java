package com.team.ijkplayer.player;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by miserydx on 17/9/25.
 */

public class DXTextureView extends TextureView implements IRenderView {

    public static final String TAG = DXTextureView.class.getSimpleName();

    protected Point mVideoSize;

    public DXTextureView(Context context) {
        super(context);
        init();
    }

    public DXTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DXTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mVideoSize = new Point(0, 0);
    }

    public void setVideoSize(Point videoSize){
        if (videoSize != null && !mVideoSize.equals(videoSize)) {
            this.mVideoSize = videoSize;
            requestLayout();
        }
    }

    @Override
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            super.setRotation(rotation);
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewRotation = (int) getRotation();

        //当控件旋转90°的时候，交换宽和高
        if (viewRotation == 90 || viewRotation == 270) {
            int tempMeasureSpec = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }

        //视频的宽和高
        int videoWidth = mVideoSize.x;
        int videoHeight = mVideoSize.y;

        //控件的宽和高，如果是UNSPECIFIED模式，返回视频的宽和高
        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);

        if (videoWidth > 0 && videoHeight > 0) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // 尺寸是固定的 应用于match_parent或精确的数值
                width = widthSpecSize;
                height = heightSpecSize;

                // 调整控件的宽高比与视频的宽高比一致
//                if (videoWidth * height < width * videoHeight) {
//                    // 如果控件的width超过了视频比例，以height为基准，通过视频的宽高比重新换算width
//                    width = height * videoWidth / videoHeight;
//                } else if(videoWidth * height > width * videoHeight){
//                    // 如果控件的height超过了视频比例，以width为基准，通过视频的宽高比重新换算height
//                    height = width * videoHeight / videoWidth;
//                }
                if(videoHeight > videoWidth){
                    width = height * videoWidth / videoHeight;
                }

            } else if(widthSpecMode == MeasureSpec.EXACTLY) {
                //如果只有width是精确值，height根据比率换算得出
                width = widthSpecSize;
                height = width * videoHeight / videoWidth;

                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // 如果height超出了控件测量得出的height，那么视频不能完整显示在控件内
                    // 按照控件测量的height重新换算width，让视频能够在控件内完整显示
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
            } else if(heightSpecMode == MeasureSpec.EXACTLY){
                //如果只有height是精确值，width根据比率换算得出
                height = heightSpecSize;
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // 如果width超出了控件测量得出的width，那么视频不能完整显示在控件内
                    // 按照控件测量的width重新换算height，让视频能够在控件内完整显示
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            } else {
                // 如果width和height都不是精确值，那么先对他们赋值视频的width和height，再根据测量值和宽高比进行调整
                width = videoWidth;
                height = videoHeight;

                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        }

        setMeasuredDimension(width, height);
    }
}
