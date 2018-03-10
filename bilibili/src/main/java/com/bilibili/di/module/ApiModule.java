package com.bilibili.di.module;

import com.bilibili.di.scope.GlobalApis;
import com.bilibili.model.api.ApiHelper;
import com.bilibili.model.api.ApiLiveApis;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.api.RecommendApis;
import com.bilibili.model.api.RegionApis;
import com.bilibili.model.api.WeChatApis;
import com.bilibili.model.api.ZhihuApis;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Module
public class ApiModule {

    //Create Api
    @GlobalApis
    @Provides
    LiveApis provideLiveService(@Named("LiveApis") Retrofit retrofit) {
        return retrofit.create(LiveApis.class);
    }

    @GlobalApis
    @Provides
    RecommendApis provideRecommendService(@Named("RecommendApi") Retrofit retrofit) {
        return retrofit.create(RecommendApis.class);
    }

    @GlobalApis
    @Provides
    AppApis provideAppService(@Named("AppApi") Retrofit retrofit) {
        return retrofit.create(AppApis.class);
    }

    @GlobalApis
    @Provides
    BangumiApis provideBangumiService(@Named("BangumiApi") Retrofit retrofit) {
        return retrofit.create(BangumiApis.class);
    }

    @GlobalApis
    @Provides
    RegionApis provideRegionService(@Named("RegionApi") Retrofit retrofit) {
        return retrofit.create(RegionApis.class);
    }

    @GlobalApis
    @Provides
    ApiLiveApis provideApiLiveService(@Named("ApiLiveApi") Retrofit retrofit) {
        return retrofit.create(ApiLiveApis.class);
    }

    //Test Api
    @GlobalApis
    @Provides
    ZhihuApis provideZhihuService(@Named("ZhihuApi") Retrofit retrofit) {
        return retrofit.create(ZhihuApis.class);
    }

    @GlobalApis
    @Provides
    WeChatApis provideWeChatService(@Named("WeChatApi") Retrofit retrofit) {
        return retrofit.create(WeChatApis.class);
    }

    //Create Retrofit
    @GlobalApis
    @Provides
    @Named("LiveApis")
    Retrofit provideLiveRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, LiveApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("RecommendApi")
    Retrofit provideRecommendRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, RecommendApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("AppApi")
    Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, AppApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("BangumiApi")
    Retrofit provideBangumiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BangumiApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("RegionApi")
    Retrofit provideRegionRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, RegionApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("ApiLiveApi")
    Retrofit provideApiLiveRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ApiLiveApis.HOST);
    }

    //Test Api
    @GlobalApis
    @Provides
    @Named("ZhihuApi")
    Retrofit provideZhihuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApis.HOST);
    }

    @GlobalApis
    @Provides
    @Named("WeChatApi")
    Retrofit provideWechatRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WeChatApis.HOST);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, String url) {
        return builder.baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @GlobalApis
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @GlobalApis
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @GlobalApis
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                String sign = ApiHelper.getSign(oldRequest.url());
                //添加sign参数
                HttpUrl.Builder newBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter(ApiHelper.PARAM_SIGN, sign);
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(newBuilder.build())
                        .build();
                return chain.proceed(newRequest);
            }
        };
        //设置拦截器
        builder.addInterceptor(interceptor);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

}
