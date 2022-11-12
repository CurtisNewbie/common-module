package com.curtisnewbie.common.util;

import org.springframework.util.Assert;

import java.lang.reflect.*;
import java.math.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.*;

/**
 * Value Utils
 *
 * @author yongj.zhuang
 */
public final class ValueUtils {

    private ValueUtils() {
    }

    /**
     * Return true if any is matched (case ignored)
     * <p>
     * null value is not allowed, and will always return false
     */
    public static boolean equalsAnyIgnoreCase(String o, String[] matched) {
        if (o == null || matched == null) return false;
        for (String t : matched)
            if (o.equalsIgnoreCase(t)) return true;
        return false;
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
        if (o == null) return false;
        if (o.equalsIgnoreCase(first)) return true;

        for (String t : theRest)
            if (o.equalsIgnoreCase(t)) return true;

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
        if (Objects.equals(o, first)) return true;
        for (Object t : theRest) {
            if (Objects.equals(o, t)) return true;
        }
        return false;
    }

    /**
     * Return true if any is matched
     * <p>
     * null value is not allowed, and will always return false
     */
    public static boolean equalsAny(Object o, Object[] targets) {
        if (o == null || targets == null) return false;
        for (Object t : targets) {
            if (Objects.equals(o, t)) return true;
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

    /**
     * Set value, if value is null the default value is used
     */
    public static <T> void setIfNull(Consumer<T> setter, T value, T defaultVal) {
        Assert.notNull(setter, "setter == null");

        final T toBeSet = value != null ? value : defaultVal;
        setter.accept(toBeSet);
    }

    /**
     * Call setter with the value returned by the supplier if the value is null
     */
    public static <T> void setIfValNull(Consumer<T> setter, T value, Supplier<T> valueSupplier) {
        setIfTrue(setter, value == null, valueSupplier);
    }

    /**
     * Call setter with the given value returned by the valueSupplier if the condition return true
     */
    public static <T> void setIfTrue(Consumer<T> setter, boolean useSetter, Supplier<T> valueSupplier) {
        Assert.notNull(setter, "setter == null");
        Assert.notNull(valueSupplier, "valueSupplier == null");

        if (useSetter)
            setter.accept(valueSupplier.get());
    }

    /**
     * Check if the object is 'empty' by looking all of its fields
     *
     * @param o        the object examined
     * @param excluded fields to be excluded (not examined)
     */
    public static boolean isEmptyObject(Object o, String... excluded) {
        if (o == null)
            return true;

        final Set<String> exclSet = Arrays.stream(excluded).collect(Collectors.toSet());

        final Field[] fields = o.getClass().getDeclaredFields();
        for (final Field f : fields) {

            if (Modifier.isStatic(f.getModifiers()) || exclSet.contains(f.getName()))
                continue;

            f.setAccessible(true);

            try {
                if (f.get(o) != null)
                    return false;

            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("Unable to access field: %s", f), e);
            }
        }
        return true;
    }

}
