package com.bilibili.model.api;

import com.bilibili.model.api.annotation.NeedSign;
import com.bilibili.model.bean.AppSearchHot;
import com.bilibili.model.bean.AppSplash;
import com.bilibili.model.bean.DataListResponse;
import com.bilibili.model.bean.DataObjectResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    Observable<DataListResponse<AppSplash>> getSplash(@Query("mobi_app") String mobi_app,
                                                      @Query("build") String build,
                                                      @Query("channel") String channel,
                                                      @Query("width") int width,
                                                      @Query("height") int height,
                                                      @Query("ver") String ver
    );

    /**
     * 获取搜索热词
     * http://app.bilibili.com/x/v2/search/hot?appkey=1d8b6e7d45233436&build=502000&limit=50&mobi_app=android&platform=android&ts=1493968467000&sign=ee58e9b75ed786d077a04a1121ca%20a575
     */
    @GET("/x/v2/search/hot")
    @NeedSign
    Observable<DataObjectResponse<AppSearchHot>> getSerchHot(@Query("appkey") String appkey,
                                                             @Query("build") String build,
                                                             @Query("limit") int limit,
                                                             @Query("mobi_app") String mobi_app,
                                                             @Query("platform") String platform,
                                                             @Query("ts") String ts
    );

}
