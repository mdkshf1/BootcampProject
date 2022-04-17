package com.bootcampproject.utils;

import java.util.Date;

public class CommonUtils {
    public static boolean validateToken(Date d1, Date d2,Long duration)
    {
        Long time = d2.getTime()-d1.getTime();
        long diffSeconds = time / 1000 % 60;
        long diffMinutes = time / (60 * 1000) % 60;
        return diffMinutes>duration;
    }
}
