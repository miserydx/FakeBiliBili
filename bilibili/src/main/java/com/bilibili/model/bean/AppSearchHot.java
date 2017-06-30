package com.bilibili.model.bean;

/**
 * Created by jiayiyang on 17/5/5.
 */

public class AppSearchHot {

    private String trackid;

    private java.util.List<List> list;

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }

    public String getTrackid() {
        return this.trackid;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public java.util.List<List> getList() {
        return this.list;
    }

    class List {
        private String keyword;

        private String status;

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

    }
}
