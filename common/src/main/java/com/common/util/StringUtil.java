package com.common.util;

import java.math.BigDecimal;

/**
 * Created by miserydx on 17/7/5.
 */

public class StringUtil {

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

}
