package com.common.util;

import java.math.BigDecimal;

/**
 * Created by miserydx on 17/7/5.
 */

public class StringUtil {

    /**
     * 数字过大转换为以万、亿为单位的字符串，保留小数点后一位
     * 12345 => 1.2万
     * @param num
     * @return
     */
    public static String numberToWord(int num) {
        String word;
        if (num >= 10000 && num < 100000000) {
            double d = (double) num / 10000;
            BigDecimal bd = new BigDecimal(d);
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
            word = bd.doubleValue() + "万";
        } else if (num >= 100000000) {
            double d = (double) num / 100000000;
            BigDecimal bd = new BigDecimal(d);
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
            word = bd.doubleValue() + "亿";
        } else {
            word = String.valueOf(num);
        }
        return word;
    }

    /**
     * 秒 转换为 时:分:秒 格式
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}
