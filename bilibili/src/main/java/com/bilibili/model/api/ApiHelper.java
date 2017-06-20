package com.bilibili.model.api;

import android.util.Log;

import com.bilibili.model.api.annotation.NeedSign;

import java.lang.reflect.Method;

import retrofit2.http.GET;

/**
 * Created by jiayiyang on 17/4/27.
 */

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();

    public static final String PARAM_SIGN = "sign";

    public static final String APP_KEY = "c1b107428d337928";
    public static final String SECRET_KEY = "ea85624dfcf12d7cc7b2b3a94fac1f2c";
    public static final String BUILD = "50300";
    public static final String MOBI_APP = "android";
    public static final String PLATFORM = "android";
    public static final String DEVICE = "android";
    public static final String SCALE = "xxhdpi";

    public static final String BANGUMI_HOST = "bangumi.bilibili.com";
    public static final String APP_HOST = "app.bilibili.com";
    public static final String LIVE_HOST = "live.bilibili.com";

    public static boolean needSigned(String host, String path) {
        boolean needSigned = false;
        Class clz = getClass(host);
        if (clz == null) {
            return needSigned;
        }
        for (Method method : clz.getMethods()) {
            NeedSign needSign = method.getAnnotation(NeedSign.class);
            GET get = method.getAnnotation(GET.class);
            if (needSign != null && get.value().equals(path)) {
                needSigned = true;
            }
        }
        return needSigned;
    }

    private static Class getClass(String host) {
        Class clz;
        try {
            switch (host) {
                case APP_HOST:
                    clz = AppApis.class;
                    break;

                case BANGUMI_HOST:
                    clz = BangumiApis.class;
                    break;

                case LIVE_HOST:
                    clz = LiveApis.class;
                    break;

                default:
                    throw new Exception("unrecorded host");
            }
        } catch (Exception e) {
            Log.d(TAG, "e : " + e.getMessage());
            return null;
        }
        return clz;
    }

}
