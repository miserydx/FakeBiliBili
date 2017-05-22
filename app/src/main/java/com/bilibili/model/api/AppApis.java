package com.bilibili.model.api;

import com.bilibili.model.api.annotation.NeedSign;
import com.bilibili.model.bean.SearchHotResponse;
import com.bilibili.model.bean.IndexResponse;
import com.bilibili.model.bean.RegionShowResponse;
import com.bilibili.model.bean.RegionResponse;
import com.bilibili.model.bean.ResultList;
import com.bilibili.model.bean.ResultObject;
import com.bilibili.model.bean.SplashResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiayiyang on 17/3/26.
 */

public interface AppApis {

    String HOST = "http://app.bilibili.com";
    String CHANNEL = "baidu";
    String VER = "4082600596548893087";

    /**
     *
     * 获取Splash背景图
     * ver参数为背景版本,存本地下次请求作为参数
     * http://app.bilibili.com/x/v2/splash?mobi_app=android&build=502000&channel=baidu&width=1080&height=1920&ver=4082600596548893087
     */
    @GET("/x/v2/splash")
    Observable<ResultList<SplashResponse>> getSplash(@Query("mobi_app") String mobi_app,
                                                     @Query("build") String build,
                                                     @Query("channel") String channel,
                                                     @Query("width") int width,
                                                     @Query("height") int height,
                                                     @Query("ver") String ver
    );

    /**
     * 推荐数据，每次请求都不一样，b站逻辑为请求到新的便保存到本地，进入app即初次展示推荐页面时，数据请求失败会加载本地存储的数据
     * 下拉刷新将数据集合添加到集合前列,上拉加载将数据集合添加到集合末尾
     * http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=502000&idx=1493277505&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1493362805000&sign=91c7aa61728e8299df5755%20d106babbfd
     */
    @GET("/x/feed/index")
    @NeedSign
    Observable<ResultList<IndexResponse>> getIndex(@Query("appkey") String appkey,
                                                   @Query("build") String build,
                                                   @Query("idx") String idx,
                                                   @Query("mobi_app") String mobi_app,
                                                   @Query("network") String network,
                                                   @Query("platform") String platform,
                                                   @Query("pull") String pull,
                                                   @Query("ts") String ts
    );

    /**
     * 获取分区
     * http://app.bilibili.com/x/v2/region?build=503000
     * http://app.bilibili.com/x/v2/region?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493689159000&ver=188703795824240229&sign=89ec282fcc4f06d8d3812dbc9f8456a9
     */
    @GET("/x/v2/region")
    Observable<ResultList<RegionResponse>> getRegion(@Query("build") String build);

    /**
     * 获取分区列表
     * http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493711039000&sign=01cfab07e67d3520363d82636296dc8b
     */
    @GET("/x/v2/show/region")
    @NeedSign
    Observable<ResultList<RegionShowResponse>> getRegionShow(@Query("appkey") String appkey,
                                                             @Query("build") String build,
                                                             @Query("mobi_app") String mobi_app,
                                                             @Query("platform") String platform,
                                                             @Query("ts") String ts
    );

    /**
     * 获取搜索热词
     * http://app.bilibili.com/x/v2/search/hot?appkey=1d8b6e7d45233436&build=502000&limit=50&mobi_app=android&platform=android&ts=1493968467000&sign=ee58e9b75ed786d077a04a1121ca%20a575
     */
    @GET("/x/v2/search/hot")
    @NeedSign
    Observable<ResultObject<SearchHotResponse>> getSerchHot(@Query("appkey") String appkey,
                                                            @Query("build") String build,
                                                            @Query("limit") int limit,
                                                            @Query("mobi_app") String mobi_app,
                                                            @Query("platform") String platform,
                                                            @Query("ts") String ts
    );

}
