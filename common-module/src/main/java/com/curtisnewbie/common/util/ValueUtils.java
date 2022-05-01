package com.curtisnewbie.common.util;

import java.math.*;
import java.util.Objects;

/**
 * Value Utils
 *
 * @author yongj.zhuang
 */
public final class ValueUtils {

    private ValueUtils() {
    }

    /**
     * Return true if none is matched (case ignored)
     */
    public static boolean equalsNoneIgnoreCase(String o, String first, String... theRest) {
        return !equalsAnyIgnoreCase(o, first, theRest);
    }

    /**
     * Return true if any is matched (case ignored)
     */
    public static boolean equalsAnyIgnoreCase(String o, String first, String... theRest) {
        if (o == null)
            return false;

        if (o.equalsIgnoreCase(first))
            return true;

        for (String t : theRest) {
            if (o.equalsIgnoreCase(t))
                return true;
        }
        return false;
    }

    /**
     * Return true if none is matched
     */
    public static boolean equalsNone(Object o, Object first, Object... theRest) {
        return !equalsAny(o, first, theRest);
    }

    /**
     * Return true if any is matched
     */
    public static boolean equalsAny(Object o, Object first, Object... theRest) {
        if (Objects.equals(o, first))
            return true;

        for (Object t : theRest) {
            if (Objects.equals(o, t))
                return true;
        }
        return false;
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
