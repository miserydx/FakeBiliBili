package com.bilibili.widget.danmu.live.entity;

/**
 * Created by czp on 17-5-24.
 */
public class WelcomeEntity extends JSONEntity {
    public WelcomeEntityData data;
    public Integer roomid;

    public class WelcomeEntityData {
        public Integer uid;
        public String uname;
        public Boolean isadmin;
        public Boolean vip;
    }
}
