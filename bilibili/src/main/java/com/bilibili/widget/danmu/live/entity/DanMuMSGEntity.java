package com.bilibili.widget.danmu.live.entity;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by czp on 17-5-24.
 */
public class DanMuMSGEntity extends JSONEntity {
    /**
     * A JSONArray with key 'info'.
     * <p>Many meaning of data in this part is unknown.
     * <p>Maybe you need to get data manual like:
     * <pre>
     *     String s = danMuMSGEntity.info.getJSONArray(2).getString(1);
     * </pre>
     */
    public JSONArray info;  //info中有许多内容含义不明

    public Integer getDanMuPool() {
        return info.getJSONArray(0).getInteger(0);
    }

    public Integer getDanMuMode() {
        return info.getJSONArray(0).getInteger(1);
    }

    public Integer getDanMuColor() {
        return info.getJSONArray(0).getInteger(3);
    }

    /**
     * Get Unix timestamp, it is not Java timestamp!
     *
     * @return Unix timestamp
     */
    //Unix时间戳
    public Long getTimestamp() {
        return info.getJSONArray(0).getLong(4);
    }

    public String getDanMuContent() {
        return info.getString(1);
    }

    public Integer getSenderId() {
        return info.getJSONArray(2).getInteger(0);
    }

    public String getSenderNick() {
        return info.getJSONArray(2).getString(1);
    }

    public Boolean getSenderIsGuard() {
        return info.getJSONArray(2).getBoolean(2);
    }

    public Boolean getSenderIsVIP() {
        return info.getJSONArray(2).getBoolean(3);
    }

    public Integer getSenderLevel() {
        return info.getJSONArray(4).getInteger(0);
    }

    public String getSenderRank() {
        return info.getJSONArray(4).getString(3);
    }
}
