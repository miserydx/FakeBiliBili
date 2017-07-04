package com.bilibili.model.bean.bangumi;

import java.util.List;

/**
 * Created by jiayiyang on 17/5/5.
 */

public class BangumiIndexPage {

    private List<Ad> ad;

    private Recommend_cn recommend_cn;

    private Recommend_jp recommend_jp;

    public void setAd(List<Ad> ad) {
        this.ad = ad;
    }

    public List<Ad> getAd() {
        return this.ad;
    }

    public void setRecommend_cn(Recommend_cn recommend_cn) {
        this.recommend_cn = recommend_cn;
    }

    public Recommend_cn getRecommend_cn() {
        return this.recommend_cn;
    }

    public void setRecommend_jp(Recommend_jp recommend_jp) {
        this.recommend_jp = recommend_jp;
    }

    public Recommend_jp getRecommend_jp() {
        return this.recommend_jp;
    }

    public class Ad {
        private String img;

        private int index;

        private String link;

        private String title;

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return this.img;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

    }

    public class Recommend_cn {
        private List<Foot> foot;

        private List<Recommend> recommend;

        public void setFoot(List<Foot> foot) {
            this.foot = foot;
        }

        public List<Foot> getFoot() {
            return this.foot;
        }

        public void setRecommend(List<Recommend> recommend) {
            this.recommend = recommend;
        }

        public List<Recommend> getRecommend() {
            return this.recommend;
        }

    }

    public class Recommend_jp {
        private List<Foot> foot;

        private List<Recommend> recommend;

        public void setFoot(List<Foot> foot) {
            this.foot = foot;
        }

        public List<Foot> getFoot() {
            return this.foot;
        }

        public void setRecommend(List<Recommend> recommend) {
            this.recommend = recommend;
        }

        public List<Recommend> getRecommend() {
            return this.recommend;
        }

    }

    public class Foot {
        private String cover;

        private double cursor;

        private String desc;

        private int id;

        private int is_new;

        private String link;

        private String onDt;

        private String title;

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCover() {
            return this.cover;
        }

        public void setCursor(double cursor) {
            this.cursor = cursor;
        }

        public double getCursor() {
            return this.cursor;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public int getIs_new() {
            return this.is_new;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setOnDt(String onDt) {
            this.onDt = onDt;
        }

        public String getOnDt() {
            return this.onDt;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

    }

    public class Recommend {
        private String badge;

        private String cover;

        private String favourites;

        private int is_auto;

        private int is_finish;

        private int is_started;

        private int last_time;

        private String newest_ep_index;

        private int pub_time;

        private int season_id;

        private int season_status;

        private String title;

        private int total_count;

        private int watching_count;

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCover() {
            return this.cover;
        }

        public void setFavourites(String favourites) {
            this.favourites = favourites;
        }

        public String getFavourites() {
            return this.favourites;
        }

        public void setIs_auto(int is_auto) {
            this.is_auto = is_auto;
        }

        public int getIs_auto() {
            return this.is_auto;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public int getIs_finish() {
            return this.is_finish;
        }

        public void setIs_started(int is_started) {
            this.is_started = is_started;
        }

        public int getIs_started() {
            return this.is_started;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public int getLast_time() {
            return this.last_time;
        }

        public void setNewest_ep_index(String newest_ep_index) {
            this.newest_ep_index = newest_ep_index;
        }

        public String getNewest_ep_index() {
            return this.newest_ep_index;
        }

        public void setPub_time(int pub_time) {
            this.pub_time = pub_time;
        }

        public int getPub_time() {
            return this.pub_time;
        }

        public void setSeason_id(int season_id) {
            this.season_id = season_id;
        }

        public int getSeason_id() {
            return this.season_id;
        }

        public void setSeason_status(int season_status) {
            this.season_status = season_status;
        }

        public int getSeason_status() {
            return this.season_status;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getTotal_count() {
            return this.total_count;
        }

        public void setWatching_count(int watching_count) {
            this.watching_count = watching_count;
        }

        public int getWatching_count() {
            return this.watching_count;
        }

    }

}
