package com.bilibili.widget.danmu.live.entity;

import com.alibaba.fastjson.JSONArray;

/**
 * Server response entity for sending DanMu.
 * Created by czp on 17-6-6.
 */
public class DanMuResponseEntity {
    public static final int NO_LOGIN = -101;
    public static final int WRONG_PARAM = -400;
    public static final int OUT_OF_LENGTH = -500;
    public static final int SUCCESS = 0;

    /**
     * Result code of sending DanMu.
     */
    public Integer code;

    /**
     * Server message.
     */
    public String msg;

    /**
     * Unknown field, it is empty JSONArray in common.
     */
    public JSONArray data;

    /**
     * To JSON string.
     */
    @Override
    public String toString() {
        return JSONArray.toJSONString(this);
    }
}
