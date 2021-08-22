package com.curtisnewbie.common.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Objects;

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

    /**
     * Get epoch time of start of the given localDate
     */
    public static long startTimeOf(LocalDate ld) {
        Objects.requireNonNull(ld);
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
    }

    /**
     * Get epoch time of the localDatetime
     */
    public static long getEpochTime(LocalDateTime ldt) {
        Objects.requireNonNull(ldt);
        return ldt.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * Convert LocalDateTime to Date
     */
    public static Date dateOf(LocalDateTime ldt) {
        Objects.requireNonNull(ldt);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Convert Date to LocalDate
     */
    public static LocalDate localDateOf(Date date) {
        Objects.requireNonNull(date);
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Convert epoch milliseconds to LocalDate
     */
    public static LocalDate localDateOf(long epochTime) {
        return Instant.ofEpochMilli(epochTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
