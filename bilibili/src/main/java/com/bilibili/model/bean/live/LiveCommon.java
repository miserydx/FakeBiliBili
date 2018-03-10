package com.bilibili.model.bean.live;

import com.bilibili.model.bean.common.Banner;
import com.bilibili.model.bean.common.Cover;
import com.bilibili.model.bean.common.EntranceIcon;
import com.bilibili.model.bean.common.Entrance_icon;
import com.bilibili.model.bean.common.Owner;
import com.bilibili.model.bean.common.Partition;

import java.util.List;

/**
 * Created by jiayiyang on 17/5/5.
 */

public class LiveCommon {

    private List<Banner> banner;

    private List<EntranceIcon> entranceIcons;

    private List<Partitions> partitions;

    private List<Navigator> navigator;

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Banner> getBanner() {
        return this.banner;
    }

    public void setEntranceIcons(List<EntranceIcon> entranceIcons) {
        this.entranceIcons = entranceIcons;
    }

    public List<EntranceIcon> getEntranceIcons() {
        return this.entranceIcons;
    }

    public void setPartitions(List<Partitions> partitions) {
        this.partitions = partitions;
    }

    public List<Partitions> getPartitions() {
        return this.partitions;
    }

    public void setNavigator(List<Navigator> navigator) {
        this.navigator = navigator;
    }

    public List<Navigator> getNavigator() {
        return this.navigator;
    }

    public static class Navigator {
        private int id;

        private String name;

        private Entrance_icon entrance_icon;

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

        public void setEntrance_icon(Entrance_icon entrance_icon) {
            this.entrance_icon = entrance_icon;
        }

        public Entrance_icon getEntrance_icon() {
            return this.entrance_icon;
        }

    }

    public static class Partitions {
        private Partition partition;

        private List<Lives> lives;

        public void setPartition(Partition partition) {
            this.partition = partition;
        }

        public Partition getPartition() {
            return this.partition;
        }

        public void setLives(List<Lives> lives) {
            this.lives = lives;
        }

        public List<Lives> getLives() {
            return this.lives;
        }

        public static class Lives {
            private Owner owner;

            private Cover cover;

            private String title;

            private int room_id;

            private int check_version;

            private int online;

            private String area;

            private int area_id;

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

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
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

    }

}
