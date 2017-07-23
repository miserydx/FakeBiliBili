package com.bilibili.widget.recyclerview;

/**
 * Created by Android_ZzT on 17/7/23.
 */

public class BaseFooterItem {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_LOAD_FAIL = 3;

    public int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
