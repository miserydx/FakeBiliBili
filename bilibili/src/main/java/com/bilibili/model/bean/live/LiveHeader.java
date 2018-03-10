package com.bilibili.model.bean.live;

import com.bilibili.model.bean.common.Banner;

import java.util.List;

/**
 * Created by miserydx on 18/3/9.
 */

public class LiveHeader {

    private List<Banner> banner;

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
}
