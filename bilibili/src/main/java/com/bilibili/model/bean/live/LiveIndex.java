package com.bilibili.model.bean.live;

import com.bilibili.model.bean.common.Cover;
import com.bilibili.model.bean.common.Owner;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by miserydx on 18/3/12.
 */

public class LiveIndex {
    private int room_id;

    private String title;

    private String cover;

    private int mid;

    private String uname;

    private String face;

    private String m_face;

    private int background_id;

    private int attention;

    private int is_attention;

    private int online;

    private int create;

    private String create_at;

    private int sch_id;

    private String status;

    private String area;

    private int area_id;

    private int area_v2_id;

    private int area_v2_parent_id;

    private String area_v2_name;

    private String area_v2_parent_name;

    private Schedule schedule;

    private Meta meta;

    private String cmt;

    private int cmt_port;

    private int cmt_port_goim;

    private List<Recommend> recommend;

//    private List<Toplist> toplist;

    private int isvip;

    private int opentime;

    private String prepare;

    private int isadmin;

    private List<Hot_word> hot_word;

//    private List<Roomgifts> roomgifts ;

//    private List<Ignore_gift> ignore_gift ;

    private int msg_mode;

    private int msg_color;

    private int msg_length;

    private int master_level;

    private int master_level_color;

    private int broadcast_type;

//    private List<Activity_gift> activity_gift;

    private int check_version;

    private int activity_id;

//    private List<Event_corner> event_corner;

    private int guard_level;

//    private Guard_info guard_info;

    private int guard_notice;

    private int guard_tip_flag;

    private int new_year_ceremony;

    private String special_gift_gif;

    private int show_room_id;

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_id() {
        return this.room_id;
    }

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

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getMid() {
        return this.mid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return this.uname;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return this.face;
    }

    public void setM_face(String m_face) {
        this.m_face = m_face;
    }

    public String getM_face() {
        return this.m_face;
    }

    public void setBackground_id(int background_id) {
        this.background_id = background_id;
    }

    public int getBackground_id() {
        return this.background_id;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getAttention() {
        return this.attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public int getIs_attention() {
        return this.is_attention;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getOnline() {
        return this.online;
    }

    public void setCreate(int create) {
        this.create = create;
    }

    public int getCreate() {
        return this.create;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCreate_at() {
        return this.create_at;
    }

    public void setSch_id(int sch_id) {
        this.sch_id = sch_id;
    }

    public int getSch_id() {
        return this.sch_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
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

    public void setArea_v2_id(int area_v2_id) {
        this.area_v2_id = area_v2_id;
    }

    public int getArea_v2_id() {
        return this.area_v2_id;
    }

    public void setArea_v2_parent_id(int area_v2_parent_id) {
        this.area_v2_parent_id = area_v2_parent_id;
    }

    public int getArea_v2_parent_id() {
        return this.area_v2_parent_id;
    }

    public void setArea_v2_name(String area_v2_name) {
        this.area_v2_name = area_v2_name;
    }

    public String getArea_v2_name() {
        return this.area_v2_name;
    }

    public void setArea_v2_parent_name(String area_v2_parent_name) {
        this.area_v2_parent_name = area_v2_parent_name;
    }

    public String getArea_v2_parent_name() {
        return this.area_v2_parent_name;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getCmt() {
        return this.cmt;
    }

    public void setCmt_port(int cmt_port) {
        this.cmt_port = cmt_port;
    }

    public int getCmt_port() {
        return this.cmt_port;
    }

    public void setCmt_port_goim(int cmt_port_goim) {
        this.cmt_port_goim = cmt_port_goim;
    }

    public int getCmt_port_goim() {
        return this.cmt_port_goim;
    }

    public void setRecommend(List<Recommend> recommend) {
        this.recommend = recommend;
    }

    public List<Recommend> getRecommend() {
        return this.recommend;
    }

    public void setIsvip(int isvip) {
        this.isvip = isvip;
    }

    public int getIsvip() {
        return this.isvip;
    }

    public void setOpentime(int opentime) {
        this.opentime = opentime;
    }

    public int getOpentime() {
        return this.opentime;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    public String getPrepare() {
        return this.prepare;
    }

    public void setIsadmin(int isadmin) {
        this.isadmin = isadmin;
    }

    public int getIsadmin() {
        return this.isadmin;
    }

    public void setHot_word(List<Hot_word> hot_word) {
        this.hot_word = hot_word;
    }

    public List<Hot_word> getHot_word() {
        return this.hot_word;
    }

//    public void setRoomgifts(List<Roomgifts> roomgifts) {
//        this.roomgifts = roomgifts;
//    }

//    public List<Roomgifts> getRoomgifts() {
//        return this.roomgifts;
//    }

//    public void setIgnore_gift(List<Ignore_gift> ignore_gift) {
//        this.ignore_gift = ignore_gift;
//    }

//    public List<Ignore_gift> getIgnore_gift() {
//        return this.ignore_gift;
//    }

    public void setMsg_mode(int msg_mode) {
        this.msg_mode = msg_mode;
    }

    public int getMsg_mode() {
        return this.msg_mode;
    }

    public void setMsg_color(int msg_color) {
        this.msg_color = msg_color;
    }

    public int getMsg_color() {
        return this.msg_color;
    }

    public void setMsg_length(int msg_length) {
        this.msg_length = msg_length;
    }

    public int getMsg_length() {
        return this.msg_length;
    }

    public void setMaster_level(int master_level) {
        this.master_level = master_level;
    }

    public int getMaster_level() {
        return this.master_level;
    }

    public void setMaster_level_color(int master_level_color) {
        this.master_level_color = master_level_color;
    }

    public int getMaster_level_color() {
        return this.master_level_color;
    }

    public void setBroadcast_type(int broadcast_type) {
        this.broadcast_type = broadcast_type;
    }

    public int getBroadcast_type() {
        return this.broadcast_type;
    }

//    public void setActivity_gift(List<Activity_gift> activity_gift) {
//        this.activity_gift = activity_gift;
//    }

//    public List<Activity_gift> getActivity_gift() {
//        return this.activity_gift;
//    }

    public void setCheck_version(int check_version) {
        this.check_version = check_version;
    }

    public int getCheck_version() {
        return this.check_version;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivity_id() {
        return this.activity_id;
    }

//    public void setEvent_corner(List<Event_corner> event_corner) {
//        this.event_corner = event_corner;
//    }

//    public List<Event_corner> getEvent_corner() {
//        return this.event_corner;
//    }

    public void setGuard_level(int guard_level) {
        this.guard_level = guard_level;
    }

    public int getGuard_level() {
        return this.guard_level;
    }

//    public void setGuard_info(Guard_info guard_info) {
//        this.guard_info = guard_info;
//    }

//    public Guard_info getGuard_info() {
//        return this.guard_info;
//    }

    public void setGuard_notice(int guard_notice) {
        this.guard_notice = guard_notice;
    }

    public int getGuard_notice() {
        return this.guard_notice;
    }

    public void setGuard_tip_flag(int guard_tip_flag) {
        this.guard_tip_flag = guard_tip_flag;
    }

    public int getGuard_tip_flag() {
        return this.guard_tip_flag;
    }

    public void setNew_year_ceremony(int new_year_ceremony) {
        this.new_year_ceremony = new_year_ceremony;
    }

    public int getNew_year_ceremony() {
        return this.new_year_ceremony;
    }

    public void setSpecial_gift_gif(String special_gift_gif) {
        this.special_gift_gif = special_gift_gif;
    }

    public String getSpecial_gift_gif() {
        return this.special_gift_gif;
    }

    public void setShow_room_id(int show_room_id) {
        this.show_room_id = show_room_id;
    }

    public int getShow_room_id() {
        return this.show_room_id;
    }

    public class Recommend {
        private Owner owner;

        private Cover cover;

        private String title;

        private int room_id;

        private int online;

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

        public void setOnline(int online) {
            this.online = online;
        }

        public int getOnline() {
            return this.online;
        }

    }


    public class Meta {
        private String[] tag;

        private String description;

        private int typeid;

        private Tag_ids tag_ids;

        private String cover;

        private String check_status;

        private int aid;

        public void setTag(String[] tag) {
            this.tag = tag;
        }

        public String[] getTag() {
            return this.tag;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public int getTypeid() {
            return this.typeid;
        }

        public void setTag_ids(Tag_ids tag_ids) {
            this.tag_ids = tag_ids;
        }

        public Tag_ids getTag_ids() {
            return this.tag_ids;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCover() {
            return this.cover;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getCheck_status() {
            return this.check_status;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getAid() {
            return this.aid;
        }

        public class Tag_ids {

            @SerializedName("0")
            private int id;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return this.id;
            }

        }

    }

    public class Schedule {
        private int cid;

        private int sch_id;

        private String title;

        private int mid;

//        private List<Manager> manager;

        private int start;

        private String start_at;

        private int aid;

        private int stream_id;

        private int online;

        private String status;

        private int meta_id;

        private int pending_meta_id;

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getCid() {
            return this.cid;
        }

        public void setSch_id(int sch_id) {
            this.sch_id = sch_id;
        }

        public int getSch_id() {
            return this.sch_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getMid() {
            return this.mid;
        }

//        public void setManager(List<Manager> manager) {
//            this.manager = manager;
//        }

//        public List<Manager> getManager() {
//            return this.manager;
//        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getStart() {
            return this.start;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getStart_at() {
            return this.start_at;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getAid() {
            return this.aid;
        }

        public void setStream_id(int stream_id) {
            this.stream_id = stream_id;
        }

        public int getStream_id() {
            return this.stream_id;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public int getOnline() {
            return this.online;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public void setMeta_id(int meta_id) {
            this.meta_id = meta_id;
        }

        public int getMeta_id() {
            return this.meta_id;
        }

        public void setPending_meta_id(int pending_meta_id) {
            this.pending_meta_id = pending_meta_id;
        }

        public int getPending_meta_id() {
            return this.pending_meta_id;
        }

    }

    public class Hot_word {
        private int id;

        private String words;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getWords() {
            return this.words;
        }

    }

}
