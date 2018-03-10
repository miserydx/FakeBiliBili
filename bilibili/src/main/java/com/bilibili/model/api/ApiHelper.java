package com.bilibili.model.api;

import android.util.Log;

import com.common.util.MD5Utils;
import com.common.util.ScreenUtil;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * Created by jiayiyang on 17/4/27.
 */

public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();
    private static final String SECRET_KEY = "ea85624dfcf12d7cc7b2b3a94fac1f2c";

    public static final String PARAM_SIGN = "sign";

    public static final String APP_KEY = "c1b107428d337928";
    public static final String BUILD = "51900";
    public static final String MOBI_APP = "android";
    public static final String PLATFORM = "android";
    public static final String DEVICE = "android";
    public static final String SCALE = ScreenUtil.getDensityString();
    public static final String NETWORK_WIFI = "wifi";
    public static final String SRC = "bili";
    public static final String VERSION = "5.19.0.519000";

    public static String getSign(HttpUrl url) {
        //拼接参数(按顺序)+SecretKey
        Set<String> set = url.queryParameterNames();
        StringBuilder queryParams = new StringBuilder();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String str = it.next();
            queryParams.append(str);
            queryParams.append("=");
            queryParams.append(url.queryParameter(str));
            if (it.hasNext()) {
                queryParams.append("&");
            }
        }
        queryParams.append(SECRET_KEY);
        String orignSign = queryParams.toString();
        //进行MD5加密
        String sign = "";
        try {
            sign = MD5Utils.getMD5(orignSign).trim();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "sign encryption failed : " + e.getMessage());
        }
        return sign;
    }

    public static String getTraceId() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
        SimpleDateFormat df2 = new SimpleDateFormat("s", Locale.getDefault());
        StringBuilder s = new StringBuilder();
        s.append(df.format(new Date()));
        s.append("000");
        s.append(df2.format(new Date()));
        return s.toString();
    }

}
