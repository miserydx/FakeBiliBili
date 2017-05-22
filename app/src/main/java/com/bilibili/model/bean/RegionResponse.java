package com.bilibili.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiayiyang on 17/5/2.
 */

public class RegionResponse {

    private int tid;

    private int reid;

    private String name;

    private String logo;

    @SerializedName("goto")
    private String _goto;

    private String param;

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {
        return this.tid;
    }

    public void setReid(int reid) {
        this.reid = reid;
    }

    public int getReid() {
        return this.reid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setGoto(String _goto) {
        this._goto = _goto;
    }

    public String getGoto() {
        return this._goto;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }

}
