package com.bilibili.model.event;

/**
 * Created by miserydx on 17/12/19.
 */

public class TabSelectedEvent {

    private int position;

    public TabSelectedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
