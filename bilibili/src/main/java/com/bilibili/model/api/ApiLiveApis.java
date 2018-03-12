package com.bilibili.model.api;

import com.bilibili.model.bean.DataObjectResponse;
import com.bilibili.model.bean.live.LiveAllList;
import com.bilibili.model.bean.live.LiveIndex;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by miserydx on 18/3/3.
 */

public interface ApiLiveApis {

    String HOST = "http://api.live.bilibili.com";

    /**
     *
     * http://api.live.bilibili.com/room/v1/AppIndex/getAllList?_device=android&_hwid=GipPLBx9THkfKEpyDnJKek0vFiAQdTVEIRAjTixQaFhvDTQCMldgBD0O&appkey=1d8b6e7d45233436&build=519000&device=android&mobi_app=android&platform=android&scale=xhdpi&src=bili&trace_id=20180228170800004&ts=1519808884&version=5.19.0.519000&sign=36a418975dbbd41a34b8d91bc266b006
     */
    @GET("/room/v1/AppIndex/getAllList")
    Observable<DataObjectResponse<LiveAllList>> getAllList(@Query("_device") String _device,
                                                           @Query("appkey") String appkey,
                                                           @Query("build") String build,
                                                           @Query("device") String device,
                                                           @Query("mobi_app") String mobi_app,
                                                           @Query("platform") String platform,
                                                           @Query("scale") String scale,
                                                           @Query("src") String src,
                                                           @Query("trace_id") String trace_id,
                                                           @Query("ts") String ts,
                                                           @Query("version") String version
    );

    /**
     *
     * http://api.live.bilibili.com/AppRoom/index?_device=android&_hwid=GipPLBx9THkfKEpyDnJKek0vFiAQdTVEIRAjTixQaFhvDTQCMldgBD0O&access_key=490410145f1259ae5414fc2bcbb24c0c&appkey=1d8b6e7d45233436&build=519000&buld=519000&jumpFrom=24000&mobi_app=android&platform=android&room_id=1365604&scale=xhdpi&src=bili&trace_id=20180312162600036&ts=1520843196&version=5.19.0.519000&sign=f00d43d2d8f9aa08d23155427ccd6002
     */
    @GET("/AppRoom/index")
    Observable<DataObjectResponse<LiveIndex>> getLiveIndex(@Query("_device") String _device,
                                                           @Query("appkey") String appkey,
                                                           @Query("build") String build,
                                                           @Query("device") String device,
                                                           @Query("mobi_app") String mobi_app,
                                                           @Query("platform") String platform,
                                                           @Query("room_id") int room_id,
                                                           @Query("scale") String scale,
                                                           @Query("src") String src,
                                                           @Query("trace_id") String trace_id,
                                                           @Query("ts") String ts,
                                                           @Query("version") String version
    );

}
