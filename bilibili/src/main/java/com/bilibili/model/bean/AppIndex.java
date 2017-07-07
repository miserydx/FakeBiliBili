package com.bilibili.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiayiyang on 17/4/28.
 */

public class AppIndex {

    private String title;

    private String cover;

    private String uri;

    private String param;

    @SerializedName("goto")
    private String _goto;

    private String desc;

    private int play;

    private int danmaku;

    private int reply;

    private int favorite;

    private int coin;

    private int share;

    private int idx;

    private int tid;

    private String tname;

    private List<Dislike_reasons> dislike_reasons;

    private int ctime;

    private int duration;

    private int mid;

    private String name;

    private String face;

    private String request_id;

    private int src_id;

    private boolean is_ad_loc;

    private String client_ip;

    private int ad_index;

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

    public String get_goto() {
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

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getReply() {
        return this.reply;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getFavorite() {
        return this.favorite;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return this.coin;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getShare() {
        return this.share;
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

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setSrc_id(int src_id) {
        this.src_id = src_id;
    }

    public int getSrc_id() {
        return this.src_id;
    }

    public void setIs_ad_loc(boolean is_ad_loc) {
        this.is_ad_loc = is_ad_loc;
    }

    public boolean getIs_ad_loc() {
        return this.is_ad_loc;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getClient_ip() {
        return this.client_ip;
    }

    public void setAd_index(int ad_index) {
        this.ad_index = ad_index;
    }

    public int getAd_index() {
        return this.ad_index;
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
