package com.bilibili.util;

import com.bilibili.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android_ZzT on 17/7/9.
 */

public class ResourceManager {

    private static Map<String, Integer> regionResMap = new HashMap<>();

    static {
        regionResMap.put("番剧区", R.mipmap.ic_category_bangumi);
        regionResMap.put("动画区", R.mipmap.ic_category_anim);
        regionResMap.put("国创区", R.mipmap.ic_category_guo_chuang);
        regionResMap.put("音乐区", R.mipmap.ic_category_music);
        regionResMap.put("舞蹈区", R.mipmap.ic_category_dance);
        regionResMap.put("游戏区", R.mipmap.ic_category_game);
        regionResMap.put("鬼畜区", R.mipmap.ic_category_gui_chu);
        regionResMap.put("生活区", R.mipmap.ic_category_life);
        regionResMap.put("科技区", R.mipmap.ic_category_tech);
        regionResMap.put("活动中心", R.mipmap.ic_category_t75);
        regionResMap.put("时尚区", R.mipmap.ic_category_fashion);
        regionResMap.put("广告区", R.mipmap.ic_category_ad);
        regionResMap.put("娱乐区", R.mipmap.ic_category_entertain);
        regionResMap.put("电视剧区", R.mipmap.ic_category_tv_play);
        regionResMap.put("电影区", R.mipmap.ic_category_movie);

    }

    public static int getRegionIconByTitle(String title) {
        return regionResMap.get(title);
    }
}
