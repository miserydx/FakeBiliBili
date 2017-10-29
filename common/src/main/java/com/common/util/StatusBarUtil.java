package com.common.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 状态栏设置工具类
 * Created by jiayiyang on 17/4/19.
 */

public class StatusBarUtil {

    private static int statusBarHeight = 0;

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    颜色
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        setColor(activity, color, null);
    }

    /**
     * 为带有DrawerLayout的页面设置状态栏颜色
     *
     * @param activity    需要设置的acitity
     * @param color       颜色
     * @param paddingView DrawerLayout布局中的containerView
     */
    public static void setColorForDrawerLayout(Activity activity, @ColorInt int color, View paddingView) {
        setColor(activity, color, paddingView);
    }

    private static void setColor(Activity activity, @ColorInt int color, View paddingView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4及以上 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            //生成一个状态栏大小的矩形
            View statusBarView = createStatusBarView(activity, color);
            //添加 statusBarView 到布局中
            content.addView(statusBarView, 0);
            if (paddingView == null) {
                paddingView = content.getChildAt(1);
            }
            paddingView.setPadding(0, StatusBarUtil.getStatusBarHeight(activity), 0, 0);
        }
    }

    /**
     * 设置顶部statusBar与顶部View同背景
     *
     * @param activity 需要设置的activity
     * @param topView  顶部需要设置padding的view
     */
    public static void setStatusBarMergeWithTopView(Activity activity, final View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int statusBarHeight = getStatusBarHeight(activity);
            topView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    topView.setPadding(0, statusBarHeight, 0, 0);
                    topView.getLayoutParams().height = topView.getHeight() + statusBarHeight;
                    topView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    public static int getStatusBarHeight(Activity activity) {
        if (statusBarHeight != 0) {
            return statusBarHeight;
        } else {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return statusBarHeight;
    }

    /**
     * 隐藏状态栏
     */
    public static void hideStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示状态栏
     */
    public static void showStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
