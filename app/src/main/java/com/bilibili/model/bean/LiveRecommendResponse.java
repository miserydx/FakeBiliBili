package com.bilibili.model.bean;

import java.util.List;

/**
 * Created by jiayiyang on 17/5/5.
 */

public class LiveRecommendResponse {

    private Recommend_data recommend_data;

    public void setRecommend_data(Recommend_data recommend_data) {
        this.recommend_data = recommend_data;
    }

    public Recommend_data getRecommend_data() {
        return this.recommend_data;
    }

    class Recommend_data {
        private List<Lives> lives;

        private Partition partition;

        private List<Banner_data> banner_data;

        public void setLives(List<Lives> lives) {
            this.lives = lives;
        }

        public List<Lives> getLives() {
            return this.lives;
        }

        public void setPartition(Partition partition) {
            this.partition = partition;
        }

        public Partition getPartition() {
            return this.partition;
        }

        public void setBanner_data(List<Banner_data> banner_data) {
            this.banner_data = banner_data;
        }

        public List<Banner_data> getBanner_data() {
            return this.banner_data;
        }

        class Partition {
            private int id;

            private String name;

            private String area;

            private Sub_icon sub_icon;

            private int count;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return this.id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getArea() {
                return this.area;
            }

            public void setSub_icon(Sub_icon sub_icon) {
                this.sub_icon = sub_icon;
            }

            public Sub_icon getSub_icon() {
                return this.sub_icon;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getCount() {
                return this.count;
            }

            class Sub_icon {
                private String src;

                private String height;

                private String width;

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getSrc() {
                    return this.src;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getHeight() {
                    return this.height;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getWidth() {
                    return this.width;
                }

            }

        }

        class Lives {
            private Owner owner;

            private Cover cover;

            private int room_id;

            private int check_version;

            private int online;

            private String area;

            private int area_id;

            private String title;

            private String playurl;

            private String accept_quality;

            private int broadcast_type;

            private int is_tv;

            public void setOwner(Owner owner) {
                this.owner = owner;
            }

            public Owner getOwner() {
                return this.owner;
            }

            public void setCover(Cover cover) {
                this.cover = cover;
            }

            public Cover getCover() {
                return this.cover;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getRoom_id() {
                return this.room_id;
            }

            public void setCheck_version(int check_version) {
                this.check_version = check_version;
            }

            public int getCheck_version() {
                return this.check_version;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getOnline() {
                return this.online;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getArea() {
                return this.area;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getArea_id() {
                return this.area_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getPlayurl() {
                return this.playurl;
            }

            public void setAccept_quality(String accept_quality) {
                this.accept_quality = accept_quality;
            }

            public String getAccept_quality() {
                return this.accept_quality;
            }

            public void setBroadcast_type(int broadcast_type) {
                this.broadcast_type = broadcast_type;
            }

            public int getBroadcast_type() {
                return this.broadcast_type;
            }

            public void setIs_tv(int is_tv) {
                this.is_tv = is_tv;
            }

            public int getIs_tv() {
                return this.is_tv;
            }

        }

        class Banner_data {
            private Owner owner;

            private Cover cover;

            private int room_id;

            private int check_version;

            private int online;

            private String area;

            private int area_id;

            private String title;

            private String playurl;

            private String accept_quality;

            private int broadcast_type;

            private int is_tv;

            public void setOwner(Owner owner) {
                this.owner = owner;
            }

            public Owner getOwner() {
                return this.owner;
            }

            public void setCover(Cover cover) {
                this.cover = cover;
            }

            public Cover getCover() {
                return this.cover;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getRoom_id() {
                return this.room_id;
            }

            public void setCheck_version(int check_version) {
                this.check_version = check_version;
            }

            public int getCheck_version() {
                return this.check_version;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getOnline() {
                return this.online;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getArea() {
                return this.area;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getArea_id() {
                return this.area_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getPlayurl() {
                return this.playurl;
            }

            public void setAccept_quality(String accept_quality) {
                this.accept_quality = accept_quality;
            }

            public String getAccept_quality() {
                return this.accept_quality;
            }

            public void setBroadcast_type(int broadcast_type) {
                this.broadcast_type = broadcast_type;
            }

            public int getBroadcast_type() {
                return this.broadcast_type;
            }

            public void setIs_tv(int is_tv) {
                this.is_tv = is_tv;
            }

            public int getIs_tv() {
                return this.is_tv;
            }

        }

        class Cover {
            private String src;

            private int height;

            private int width;

            public void setSrc(String src) {
                this.src = src;
            }

            public String getSrc() {
                return this.src;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getHeight() {
                return this.height;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getWidth() {
                return this.width;
            }

        }

        class Owner {
            private String face;

            private int mid;

            private String name;

            public void setFace(String face) {
                this.face = face;
            }

            public String getFace() {
                return this.face;
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

        }

    }

}
