package com.bilibili.model.api;

import com.bilibili.model.bean.BangumiIndexFall;
import com.bilibili.model.bean.ResultListResponse;
import com.bilibili.model.bean.BangumiIndexPage;
import com.bilibili.model.bean.ResultObjectResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiayiyang on 17/3/26.
 */

public interface BangumiApis {

    String HOST = "http://bangumi.bilibili.com";

    /**
     * https://bangumi.bilibili.com/appindex/follow_index_page?appkey=1d8b6e7d45233436&build=502000&mobi_app=android&platform=android&ts=1493967208000&sign=3eff79d895af9cf800016%20fe8f6bc6ce0
     */
    @GET("/appindex/follow_index_page")
    Observable<ResultObjectResponse<BangumiIndexPage>> getIndexPage(@Query("appkey") String appkey,
                                                                    @Query("build") String build,
                                                                    @Query("mobi_app") String mobi_app,
                                                                    @Query("platform") String platform,
                                                                    @Query("ts") String ts
    );

    /**
     * https://bangumi.bilibili.com/appindex/follow_index_fall?appkey=1d8b6e7d45233436&build=509000&cursor=0&mobi_app=android&platform=android&ts=1499937514&sign=2dae626fed99d43abbc9d09cfd124641
     */
    @GET("/appindex/follow_index_fall")
    Observable<ResultListResponse<BangumiIndexFall>> getIndexFall(@Query("appkey") String appkey,
                                                                  @Query("build") String build,
                                                                  @Query("cursor") long cursor,
                                                                  @Query("mobi_app") String mobi_app,
                                                                  @Query("platform") String platform,
                                                                  @Query("ts") String ts
    );

}
