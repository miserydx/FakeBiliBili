package com.bilibili.model.api;

import com.bilibili.model.api.annotation.NeedSign;
import com.bilibili.model.bean.recommend.AppIndex;
import com.bilibili.model.bean.DataListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android_ZzT on 17/7/7.
 */

public interface RecommendApis {

    String HOST = "http://app.bilibili.com";


    /**
     * 推荐数据，每次请求都不一样，b站逻辑为请求到新的便保存到本地，进入app即初次展示推荐页面时，数据请求失败会加载本地存储的数据
     * 下拉刷新将数据集合添加到集合前列,上拉加载将数据集合添加到集合末尾
     * http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=502000&idx=1493277505&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1493362805000&sign=91c7aa61728e8299df5755%20d106babbfd
     */
    @GET("/x/feed/index")
    @NeedSign
    Observable<DataListResponse<AppIndex>> getIndex(@Query("appkey") String appkey,
                                                    @Query("build") String build,
                                                    @Query("idx") String idx,
                                                    @Query("login_event") int login_event,
                                                    @Query("mobi_app") String mobi_app,
                                                    @Query("network") String network,
                                                    @Query("open_event") String open_event,
                                                    @Query("platform") String platform,
                                                    @Query("pull") boolean pull,
                                                    @Query("style") int style,
                                                    @Query("ts") String ts
    );
}
