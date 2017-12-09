package com.bilibili.widget.bottombar;

import android.support.annotation.DrawableRes;

/**
 * Created by miserydx on 17/12/6.
 */

public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
