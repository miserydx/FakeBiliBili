package com.bilibili.widget.danmu.live.entity;

/**
 * Created by czp on 17-6-23.
 */
public class UserInfoEntity {
    public static final String SUCCESS = "REPONSE_OK";
    public static final String NO_LOGIN = "-101";

    //code 可能是数字也可能是字符串
    public String code;
    public String msg;
    //data 可能是空的 JSONArray 也可能是 JSONObject, 当返回的 Entity 中 data 为 null 时说明其为空 JSONArray
    public UserInfoEntityData data;

    public class UserInfoEntityData {
        public String uname;
        public String face;
        public Integer silver;
        public Integer gold;
        public Integer achieve;
        public Boolean vip;
        public Boolean svip;
        public Integer user_level;
        public Integer user_next_level;
        public Integer user_intimacy;
        public Integer user_next_intimacy;
        public String user_level_rank;
        public Integer billCoin;
    }
}
