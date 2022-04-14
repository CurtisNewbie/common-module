package com.curtisnewbie.common.util;

import java.math.*;

/**
 * Value Utils
 *
 * @author yongj.zhuang
 */
public final class ValueUtils {

    private ValueUtils() {
    }

    /**
     * Is value in between start and end
     */
    public static boolean inBetween(long val, long start, long end) {
        return val >= start && val <= end;
    }

    /**
     * Is value in between start and end
     */
    public static boolean inBetween(BigDecimal val, BigDecimal start, BigDecimal end) {
        return val.compareTo(start) >= 0 && val.compareTo(end) <= 0;
    }

    /**
     * Is value in between start and end
     */
    public static boolean inBetween(int val, int start, int end) {
        return val >= start && val <= end;
    }

}
