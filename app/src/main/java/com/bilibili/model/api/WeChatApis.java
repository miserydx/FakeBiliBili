package com.bilibili.model.api;

import com.bilibili.model.bean.WeiXinJingXuanBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiayiyang on 17/3/26.
 * 微信Apis
 */

public interface WeChatApis {

    String HOST = "https://api.tianapi.com/";

    String KEY = "4c5bfb3c533be3e1edfd4a9f24a84926";

    /**
     * 微信精选
     * @param key API密钥 必填
     * @param num 指定返回数量，最大50 必填
     * @param page 翻页，每页输出数量跟随num参数
     * @return
     */
    @GET("wxnew")
    Observable<WeiXinJingXuanBean> getWeiXinJingXuan(@Query("key") String key, @Query("num") int num, @Query("page") int page);

    @GET("wxnew")
    Call<WeiXinJingXuanBean> getWeiXinJingXuanWithoutRxjava(@Query("key") String key, @Query("num") int num, @Query("page") int page);

}
