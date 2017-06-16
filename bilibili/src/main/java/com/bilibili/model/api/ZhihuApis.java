package com.bilibili.model.api;


import com.bilibili.model.bean.HotListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by yangjiayi on 2017/3/25.
 * 知乎Apis
 */
public interface ZhihuApis {

    String HOST = "http://news-at.zhihu.com/api/4/";

    /**
     * 热门日报
     */
    @GET("news/hot")
    Observable<HotListBean> getHotList();
}
