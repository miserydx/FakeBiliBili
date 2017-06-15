package com.bilibili.model.api;

import com.bilibili.model.api.annotation.NeedSign;
import com.bilibili.model.bean.LiveAreasResponse;
import com.bilibili.model.bean.LiveCommonResponse;
import com.bilibili.model.bean.LiveRecommendResponse;
import com.bilibili.model.bean.ResultList;
import com.bilibili.model.bean.ResultObject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiayiyang on 17/5/2.
 */

public interface LiveApis {

    String HOST = "http://live.bilibili.com";

    /**
     *
     * http://live.bilibili.com/AppNewIndex/recommend?_device=android&appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&scale=xxhdpi&ts=1493717114000&sign=5fdd7bf967d249b4d5ee6de8cf5fe72d
     */
    @GET("/AppNewIndex/recommend")
    @NeedSign
    Observable<ResultObject<LiveRecommendResponse>> getRecommend(@Query("_device") String _device,
                                                                 @Query("appkey") String appkey,
                                                                 @Query("build") String build,
                                                                 @Query("mobi_app") String mobi_app,
                                                                 @Query("platform") String platform,
                                                                 @Query("scale") String scale,
                                                                 @Query("ts") String ts
    );

    /**
     *
     * http://live.bilibili.com/AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&scale=xxhdpi&ts=1493717114000&sign=5fdd7bf967d249b4d5ee6de8cf5fe72d
     */
    @GET("/AppNewIndex/common")
    @NeedSign
    Observable<ResultObject<LiveCommonResponse>> getCommon(@Query("_device") String _device,
                                                           @Query("appkey") String appkey,
                                                           @Query("build") String build,
                                                           @Query("mobi_app") String mobi_app,
                                                           @Query("platform") String platform,
                                                           @Query("scale") String scale,
                                                           @Query("ts") String ts
    );

    /**
     *
     * http://live.bilibili.com/AppIndex/areas?_device=android&appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&scale=xxhdpi&ts=1493965934000&sign=5527d741%20d5d5cbdb02feaa47127eb360
     */
    @GET("/AppIndex/areas")
    @NeedSign
    Observable<ResultList<LiveAreasResponse>> getAreas(@Query("_device") String _device,
                                                       @Query("appkey") String appkey,
                                                       @Query("build") String build,
                                                       @Query("mobi_app") String mobi_app,
                                                       @Query("platform") String platform,
                                                       @Query("scale") String scale,
                                                       @Query("ts") String ts
    );
}
