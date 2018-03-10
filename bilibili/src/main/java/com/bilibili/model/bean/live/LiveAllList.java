package com.bilibili.model.bean.live;

import com.bilibili.model.bean.common.Banner;
import com.bilibili.model.bean.common.Cover;
import com.bilibili.model.bean.common.EntranceIcon;
import com.bilibili.model.bean.common.Owner;
import com.bilibili.model.bean.common.Partition;

import java.util.List;

/**
 * Created by miserydx on 18/3/3.
 */

public class LiveAllList {

    private List<Banner> banner;

    private List<EntranceIcon> entranceIcons;

    private List<Partitions> partitions;

    private Star_show star_show;

    private Recommend_data recommend_data;

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

    public void setStar_show(Star_show star_show) {
        this.star_show = star_show;
    }

    public Star_show getStar_show() {
        return this.star_show;
    }

    public void setRecommend_data(Recommend_data recommend_data) {
        this.recommend_data = recommend_data;
    }

    public Recommend_data getRecommend_data() {
        return this.recommend_data;
    }

    public class Partitions {
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


    }

    public class Star_show {
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

    }

    public class Recommend_data {
        private Partition partition;

        private List<Banner_data> banner_data;

        private List<Lives> lives;

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

        public void setLives(List<Lives> lives) {
            this.lives = lives;
        }

        public List<Lives> getLives() {
            return this.lives;
        }

        public class Banner_data {
            private Cover cover;

            private String title;

            private int is_clip;

            private New_cover new_cover;

            private String new_title;

            private String new_router;

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

            public void setIs_clip(int is_clip) {
                this.is_clip = is_clip;
            }

            public int getIs_clip() {
                return this.is_clip;
            }

            public void setNew_cover(New_cover new_cover) {
                this.new_cover = new_cover;
            }

            public New_cover getNew_cover() {
                return this.new_cover;
            }

            public void setNew_title(String new_title) {
                this.new_title = new_title;
            }

            public String getNew_title() {
                return this.new_title;
            }

            public void setNew_router(String new_router) {
                this.new_router = new_router;
            }

            public String getNew_router() {
                return this.new_router;
            }

            public class New_cover {
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

        }

        public class Lives {
            private Owner owner;

            private Cover cover;

            private int room_id;

            private int check_version;

            private int online;

            private String area;

            private int area_id;

            private String title;

            private String playurl;

            private int[] accept_quality_v2;

            private int current_quality;

            private String accept_quality;

            private int broadcast_type;

            private int is_tv;

            private String corner;

            private String pendent;

            private int area_v2_id;

            private String area_v2_name;

            private int area_v2_parent_id;

            private String area_v2_parent_name;

            private String data_behavior_id;

            private String data_source_id;

            public void setOwner(Owner owner){
                this.owner = owner;
            }
            public Owner getOwner(){
                return this.owner;
            }
            public void setCover(Cover cover){
                this.cover = cover;
            }
            public Cover getCover(){
                return this.cover;
            }
            public void setRoom_id(int room_id){
                this.room_id = room_id;
            }
            public int getRoom_id(){
                return this.room_id;
            }
            public void setCheck_version(int check_version){
                this.check_version = check_version;
            }
            public int getCheck_version(){
                return this.check_version;
            }
            public void setOnline(int online){
                this.online = online;
            }
            public int getOnline(){
                return this.online;
            }
            public void setArea(String area){
                this.area = area;
            }
            public String getArea(){
                return this.area;
            }
            public void setArea_id(int area_id){
                this.area_id = area_id;
            }
            public int getArea_id(){
                return this.area_id;
            }
            public void setTitle(String title){
                this.title = title;
            }
            public String getTitle(){
                return this.title;
            }
            public void setPlayurl(String playurl){
                this.playurl = playurl;
            }
            public String getPlayurl(){
                return this.playurl;
            }
            public void setAccept_quality_v2(int[] accept_quality_v2){
                this.accept_quality_v2 = accept_quality_v2;
            }
            public int[] getAccept_quality_v2(){
                return this.accept_quality_v2;
            }
            public void setCurrent_quality(int current_quality){
                this.current_quality = current_quality;
            }
            public int getCurrent_quality(){
                return this.current_quality;
            }
            public void setAccept_quality(String accept_quality){
                this.accept_quality = accept_quality;
            }
            public String getAccept_quality(){
                return this.accept_quality;
            }
            public void setBroadcast_type(int broadcast_type){
                this.broadcast_type = broadcast_type;
            }
            public int getBroadcast_type(){
                return this.broadcast_type;
            }
            public void setIs_tv(int is_tv){
                this.is_tv = is_tv;
            }
            public int getIs_tv(){
                return this.is_tv;
            }
            public void setCorner(String corner){
                this.corner = corner;
            }
            public String getCorner(){
                return this.corner;
            }
            public void setPendent(String pendent){
                this.pendent = pendent;
            }
            public String getPendent(){
                return this.pendent;
            }
            public void setArea_v2_id(int area_v2_id){
                this.area_v2_id = area_v2_id;
            }
            public int getArea_v2_id(){
                return this.area_v2_id;
            }
            public void setArea_v2_name(String area_v2_name){
                this.area_v2_name = area_v2_name;
            }
            public String getArea_v2_name(){
                return this.area_v2_name;
            }
            public void setArea_v2_parent_id(int area_v2_parent_id){
                this.area_v2_parent_id = area_v2_parent_id;
            }
            public int getArea_v2_parent_id(){
                return this.area_v2_parent_id;
            }
            public void setArea_v2_parent_name(String area_v2_parent_name){
                this.area_v2_parent_name = area_v2_parent_name;
            }
            public String getArea_v2_parent_name(){
                return this.area_v2_parent_name;
            }
            public void setData_behavior_id(String data_behavior_id){
                this.data_behavior_id = data_behavior_id;
            }
            public String getData_behavior_id(){
                return this.data_behavior_id;
            }
            public void setData_source_id(String data_source_id){
                this.data_source_id = data_source_id;
            }
            public String getData_source_id(){
                return this.data_source_id;
            }

        }

    }

    public class Lives {
        private int roomid;

        private int uid;

        private String title;

        private String uname;

        private int online;

        private String user_cover;

        private int user_cover_flag;

        private String system_cover;

        private boolean show_cover;

        private String link;

        private String face;

        private int parent_id;

        private String parent_name;

        private int area_id;

        private String area_name;

        private String web_pendent;

        private Cover_size cover_size;

        private String play_url;

        private int[] accept_quality_v2;

        private int current_quality;

        private String accept_quality;

        private int broadcast_type;

        private int is_tv;

        private String corner;

        private String pendent;

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public int getRoomid() {
            return this.roomid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUname() {
            return this.uname;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public int getOnline() {
            return this.online;
        }

        public void setUser_cover(String user_cover) {
            this.user_cover = user_cover;
        }

        public String getUser_cover() {
            return this.user_cover;
        }

        public void setUser_cover_flag(int user_cover_flag) {
            this.user_cover_flag = user_cover_flag;
        }

        public int getUser_cover_flag() {
            return this.user_cover_flag;
        }

        public void setSystem_cover(String system_cover) {
            this.system_cover = system_cover;
        }

        public String getSystem_cover() {
            return this.system_cover;
        }

        public void setShow_cover(boolean show_cover) {
            this.show_cover = show_cover;
        }

        public boolean getShow_cover() {
            return this.show_cover;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return this.link;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getFace() {
            return this.face;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getParent_id() {
            return this.parent_id;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public String getParent_name() {
            return this.parent_name;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public int getArea_id() {
            return this.area_id;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getArea_name() {
            return this.area_name;
        }

        public void setWeb_pendent(String web_pendent) {
            this.web_pendent = web_pendent;
        }

        public String getWeb_pendent() {
            return this.web_pendent;
        }

        public void setCover_size(Cover_size cover_size) {
            this.cover_size = cover_size;
        }

        public Cover_size getCover_size() {
            return this.cover_size;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public String getPlay_url() {
            return this.play_url;
        }

        public void setAccept_quality_v2(int[] accept_quality_v2) {
            this.accept_quality_v2 = accept_quality_v2;
        }

        public int[] getAccept_quality_v2() {
            return this.accept_quality_v2;
        }

        public void setCurrent_quality(int current_quality) {
            this.current_quality = current_quality;
        }

        public int getCurrent_quality() {
            return this.current_quality;
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

        public void setCorner(String corner) {
            this.corner = corner;
        }

        public String getCorner() {
            return this.corner;
        }

        public void setPendent(String pendent) {
            this.pendent = pendent;
        }

        public String getPendent() {
            return this.pendent;
        }

        public class Cover_size {
            private int height;

            private int width;

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

    }

}
