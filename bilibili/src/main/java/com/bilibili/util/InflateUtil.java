package com.bilibili.util;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by miserydx on 17/10/24.
 */

public class InflateUtil {

    public static View inflate(LayoutInflater inflater, int resId){
        return inflater.inflate(resId, null);
    }

}
