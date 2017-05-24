package com.bilibili.di.module;

import com.bilibili.app.ApiHelper;
import com.bilibili.di.scope.AppApi;
import com.bilibili.di.scope.BilibiliApi;
import com.bilibili.di.scope.LiveApi;
import com.bilibili.di.scope.WeChatApi;
import com.bilibili.di.scope.ZhihuApi;
import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.api.ZhihuApis;
import com.bilibili.model.api.WeChatApis;
import com.bilibili.util.MD5Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiayiyang on 17/3/25.
 */

@Module
public class ApiModule {

    //Create Api
    @Singleton
    @Provides
    LiveApis provideLiveServices(@LiveApi Retrofit retrofit) {
        return retrofit.create(LiveApis.class);
    }

    @Singleton
    @Provides
    AppApis provideAppService(@AppApi Retrofit retrofit) {
        return retrofit.create(AppApis.class);
    }

    @Singleton
    @Provides
    BangumiApis provideBilibiliService(@BilibiliApi Retrofit retrofit) {
        return retrofit.create(BangumiApis.class);
    }

    @Singleton
    @Provides
    ZhihuApis provideZhihuService(@ZhihuApi Retrofit retrofit) {
        return retrofit.create(ZhihuApis.class);
    }

    @Singleton
    @Provides
    WeChatApis provideWeChatService(@WeChatApi Retrofit retrofit) {
        return retrofit.create(WeChatApis.class);
    }

    //Create Retrofit
    @Singleton
    @Provides
    @LiveApi
    Retrofit provideLiveRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, LiveApis.HOST);
    }

    @Singleton
    @Provides
    @AppApi
    Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, AppApis.HOST);
    }

    @Singleton
    @Provides
    @BilibiliApi
    Retrofit provideBilibiliRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BangumiApis.HOST);
    }

    @Singleton
    @Provides
    @ZhihuApi
    Retrofit provideZhihuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApis.HOST);
    }

    @Singleton
    @Provides
    @WeChatApi
    Retrofit provideWechatRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WeChatApis.HOST);
    }

    Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, String url) {
        return builder.baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                if(!ApiHelper.needSigned(oldRequest.url().host(), oldRequest.url().encodedPath())){
                    return chain.proceed(oldRequest);
                }
                //拼接参数(按顺序)+SecretKey
                Set set = oldRequest.url().queryParameterNames();
                StringBuilder queryParams = new StringBuilder();
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    String str = it.next();
                    queryParams.append(str);
                    queryParams.append("=");
                    queryParams.append(oldRequest.url().queryParameter(str));
                    if(it.hasNext()){
                        queryParams.append("&");
                    }
                }
                queryParams.append(ApiHelper.getSecretKey());
                String orignSign = queryParams.toString();
                //进行MD5加密
                String sign = "";
                try{
                    sign = MD5Utils.getMD5(orignSign).trim();
                }catch (NoSuchAlgorithmException e){

                }
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
