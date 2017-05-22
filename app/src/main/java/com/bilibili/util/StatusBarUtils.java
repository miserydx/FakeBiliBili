package com.bilibili.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by jiayiyang on 17/4/19.
 */

public class StatusBarUtils {

    private static int statusBarHeight = 0;

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

    public static void  setStatusBarMergeWithToolBar(final Toolbar toolbar, Activity activity) {
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
            toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    toolbar.setPadding(0, statusBarHeight, 0, 0);
                    toolbar.getLayoutParams().height = toolbar.getHeight() + statusBarHeight;
                    toolbar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }
}
