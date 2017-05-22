package com.bilibili.util;

import com.bilibili.model.api.WeChatApis;
import com.bilibili.model.api.ZhihuApis;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiayiyang on 17/4/10.
 */

public class RetrofitHelper {

    private static WeChatApis weChatApis;
    private static ZhihuApis zhihuApis;

    public static WeChatApis getWeChatApis() {
        if(weChatApis == null) {
            weChatApis = createWeChatService();
        }

        return weChatApis;

    }

    public static ZhihuApis getZhihuApis() {
        if(zhihuApis == null) {
            zhihuApis = createZhihuService();
        }
        return zhihuApis;
    }

    private static WeChatApis createWeChatService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(WeChatApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WeChatApis.class);
    }

    private static ZhihuApis createZhihuService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(ZhihuApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ZhihuApis.class);
    }

}
