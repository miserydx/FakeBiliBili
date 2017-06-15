package com.bilibili.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiayiyang on 17/4/28.
 */

public class IndexResponse {

    private String title;

    private String cover;

    private String uri;

    private String param;

    @SerializedName("goto")
    private String _goto;

    private String desc;

    private int play;

    private int danmaku;

    private int idx;

    private int tid;

    private String tname;

    private Tag tag;

    private List<Dislike_reasons> dislike_reasons;

    private int ctime;

    private int duration;

    private int mid;

    private String name;

    private String face;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return this.cover;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }

    public void setGoto(String _goto) {
        this._goto = _goto;
    }

    public String getGoto() {
        return this._goto;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getPlay() {
        return this.play;
    }

    public void setDanmaku(int danmaku) {
        this.danmaku = danmaku;
    }

    public int getDanmaku() {
        return this.danmaku;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return this.idx;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {
        return this.tid;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setDislike_reasons(List<Dislike_reasons> dislike_reasons) {
        this.dislike_reasons = dislike_reasons;
    }

    public List<Dislike_reasons> getDislike_reasons() {
        return this.dislike_reasons;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getCtime() {
        return this.ctime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getMid() {
        return this.mid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return this.face;
    }

    class Dislike_reasons {
        private int reason_id;

        private String reason_name;

        public void setReason_id(int reason_id) {
            this.reason_id = reason_id;
        }

        public int getReason_id() {
            return this.reason_id;
        }

        public void setReason_name(String reason_name) {
            this.reason_name = reason_name;
        }

        public String getReason_name() {
            return this.reason_name;
        }

    }

    class Tag {
        private int tag_id;

        private String tag_name;

        private Count count;

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public int getTag_id() {
            return this.tag_id;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getTag_name() {
            return this.tag_name;
        }

        public void setCount(Count count) {
            this.count = count;
        }

        public Count getCount() {
            return this.count;
        }

        class Count {
            private int atten;

            public void setAtten(int atten) {
                this.atten = atten;
            }

            public int getAtten() {
                return this.atten;
            }

        }

    }
}
