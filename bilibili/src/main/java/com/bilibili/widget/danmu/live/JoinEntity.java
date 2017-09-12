package com.bilibili.widget.danmu.live;

import java.util.Random;

/**
 * Created by czp on 17-5-24.
 */
class JoinEntity {
    public int roomid;
    public long uid;

    public JoinEntity(int roomid) {
        this.roomid = roomid;
        uid = (long) (1e14 + 2e14 * new Random().nextDouble());
    }
}
