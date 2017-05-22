package com.bilibili.app;

import android.util.Log;

import com.bilibili.model.api.AppApis;
import com.bilibili.model.api.BangumiApis;
import com.bilibili.model.api.LiveApis;
import com.bilibili.model.api.annotation.NeedSign;

import java.lang.reflect.Method;

/**
 * Created by jiayiyang on 17/4/27.
 */

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();

    public static final String PARAM_SIGN = "sign";

    private static final String APP_KEY = "c1b107428d337928";
    private static final String SECRET_KEY = "ea85624dfcf12d7cc7b2b3a94fac1f2c";
    private static final String BUILD = "50300";
    private static final String MOBI_APP = "android";
    private static final String PLATFORM = "android";
    private static final String DEVICE = "android";
    private static final String SCALE = "xxhdpi";

    private static final String BANGUMI_HOST = "bangumi.bilibili.com";
    private static final String APP_HOST = "app.bilibili.com";
    private static final String LIVE_HOST = "live.bilibili.com";

    public static String getAppKey() {
        return APP_KEY;
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static String getBUILD() {
        return BUILD;
    }

    public static String getMobiApp() {
        return MOBI_APP;
    }

    public static String getPLATFORM() {
        return PLATFORM;
    }

    public static String getDevice() {
        return DEVICE;
    }

    public static String getScale() {
        return SCALE;
    }

    public static boolean needSigned(String host) {
        Class clz = getClass(host);
        if (clz == null) {
            return false;
        }
        for (Method method : clz.getMethods()) {
            NeedSign needSign = method.getAnnotation(NeedSign.class);
            return (needSign != null);
        }
        return false;
    }

    private static Class getClass(String host) {
        Class clz = null;
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
