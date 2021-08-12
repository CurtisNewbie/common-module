package com.curtisnewbie.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils for date
 *
 * @author yongjie.zhuang
 */
public final class DateUtils {

    private DateUtils() {
    }

    /** dd/MM/yyyy HH:mm */
    public static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

    /**
     * Format date using given pattern
     */
    public static String format(Date d, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    /**
     * Format date using pattern {@link #DD_MM_YYYY_HH_MM}
     */
    public static String format(Date d) {
        return format(d, DD_MM_YYYY_HH_MM);
    }

    /**
     * Get SimpleDateFormat with pattern {@link #DD_MM_YYYY_HH_MM}
     */
    public static SimpleDateFormat getDefSimpleDateFormat() {
        return new SimpleDateFormat(DD_MM_YYYY_HH_MM);
    }

}
