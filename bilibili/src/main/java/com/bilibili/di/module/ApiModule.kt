package com.bilibili.di.module

import com.bilibili.di.scope.GlobalApis
import com.bilibili.model.api.ApiHelper
import com.bilibili.model.api.AppApis
import com.bilibili.model.api.BangumiApis
import com.bilibili.model.api.LiveApis
import com.bilibili.model.api.RecommendApis
import com.bilibili.model.api.RegionApis
import com.bilibili.model.api.WeChatApis
import com.bilibili.model.api.ZhihuApis

import java.io.IOException
import java.util.concurrent.TimeUnit

import javax.inject.Named

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jiayiyang on 17/3/25.
 */

@Module
class ApiModule {

    //Create Api
    @GlobalApis
    @Provides
    internal fun provideLiveService(@Named("LiveApis") retrofit: Retrofit): LiveApis {
        return retrofit.create(LiveApis::class.java)
    }

    @GlobalApis
    @Provides
    internal fun provideRecommendService(@Named("RecommendApi") retrofit: Retrofit): RecommendApis {
        return retrofit.create(RecommendApis::class.java)
    }

    @GlobalApis
    @Provides
    internal fun provideAppService(@Named("AppApi") retrofit: Retrofit): AppApis {
        return retrofit.create(AppApis::class.java)
    }

    @GlobalApis
    @Provides
    internal fun provideBangumiService(@Named("BangumiApi") retrofit: Retrofit): BangumiApis {
        return retrofit.create(BangumiApis::class.java)
    }

    @GlobalApis
    @Provides
    internal fun provideRegionService(@Named("RegionApi") retrofit: Retrofit): RegionApis {
        return retrofit.create(RegionApis::class.java)
    }

    //Test Api
    @GlobalApis
    @Provides
    internal fun provideZhihuService(@Named("ZhihuApi") retrofit: Retrofit): ZhihuApis {
        return retrofit.create(ZhihuApis::class.java)
    }

    @GlobalApis
    @Provides
    internal fun provideWeChatService(@Named("WeChatApi") retrofit: Retrofit): WeChatApis {
        return retrofit.create(WeChatApis::class.java)
    }

    //Create Retrofit
    @GlobalApis
    @Provides
    @Named("LiveApis")
    internal fun provideLiveRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, LiveApis.HOST)
    }

    @GlobalApis
    @Provides
    @Named("RecommendApi")
    internal fun provideRecommendRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, RecommendApis.HOST)
    }

    @GlobalApis
    @Provides
    @Named("AppApi")
    internal fun provideAppRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, AppApis.HOST)
    }

    @GlobalApis
    @Provides
    @Named("BangumiApi")
    internal fun provideBangumiRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, BangumiApis.HOST)
    }

    @GlobalApis
    @Provides
    @Named("RegionApi")
    internal fun provideRegionRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, RegionApis.HOST)
    }

    //Test Api
    @GlobalApis
    @Provides
    @Named("ZhihuApi")
    internal fun provideZhihuRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, ZhihuApis.HOST)
    }

    @GlobalApis
    @Provides
    @Named("WeChatApi")
    internal fun provideWechatRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, WeChatApis.HOST)
    }

    private fun createRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient, url: String): Retrofit {
        return builder.baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @GlobalApis
    @Provides
    internal fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @GlobalApis
    @Provides
    internal fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @GlobalApis
    @Provides
    internal fun provideClient(builder: OkHttpClient.Builder): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val oldRequest = chain.request()
            val sign = ApiHelper.getSign(oldRequest.url())
            //添加sign参数
            val newBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter(ApiHelper.PARAM_SIGN, sign)
            val newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(newBuilder.build())
                    .build()
            chain.proceed(newRequest)
        }
        //设置拦截器
        builder.addInterceptor(interceptor)
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        return builder.build()
    }

}
