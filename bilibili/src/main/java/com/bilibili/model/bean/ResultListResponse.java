package com.bilibili.model.bean;

import java.util.List;

/**
 * Created by jiayiyang on 17/5/2.
 */

public class ResultListResponse<T> {

    private int code;

    private String message;

    private List<T> result;

    private int ttl;

    private String ver;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return result;
    }

    public void setData(List<T> data) {
        this.result = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
