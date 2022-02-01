package com.curtisnewbie.common.util;


import com.curtisnewbie.common.exceptions.UnrecoverableMsgEmbeddedException;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for assertion
 * <p>
 * For condition that does not match the rule, a {@link com.curtisnewbie.common.exceptions.UnrecoverableMsgEmbeddedException}
 * is thrown
 * </p>
 * <p>
 * Almost the same as {@link ValidUtils}, except that the {@link RuntimeException} is used
 * </p>
 *
 * @author yongjie.zhuang
 */
public final class AssertUtils {

    private static final String DEFAULT_NON_NULL_OR_EMPTY_MSG = "Required parameters cannot be null or empty";

    private AssertUtils() {

    }

    /**
     * Assert array not null or empty
     */
    public static <T> void notEmpty(T[] c, String msg) {
        nonNull(c);
        isFalse(c.length == 0, msg);
    }

    /**
     * Assert array not null or empty
     */
    public static <T> void notEmpty(T[] c) {
        notEmpty(c, DEFAULT_NON_NULL_OR_EMPTY_MSG);
    }

    /**
     * Assert collection not null or empty
     */
    public static void notEmpty(Collection<?> c) {
        nonNull(c);
        isFalse(c.isEmpty(), DEFAULT_NON_NULL_OR_EMPTY_MSG);
    }

    /**
     * Assert has text
     */
    public static void hasText(String text) {
        hasText(text, DEFAULT_NON_NULL_OR_EMPTY_MSG);
    }

    /**
     * Assert has text
     */
    public static void hasText(String text, String errMsg) {
        if (!StringUtils.hasText(text)) {
            throw new UnrecoverableMsgEmbeddedException(errMsg);
        }
    }

    /**
     * Assert not null
     */
    public static <T> void nonNull(T t) {
        nonNull(t, DEFAULT_NON_NULL_OR_EMPTY_MSG);
    }

    /**
     * Assert not null
     */
    public static <T> void nonNull(T t, String errMsg) {
        isFalse(t == null, errMsg);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(T t, V v, String errMsg) {
        isTrue(Objects.equals(t, v), errMsg);
    }

    /**
     * Assert not equals
     */
    public static <T, V> void notEquals(T t, V v, String errMsg) {
        isFalse(Objects.equals(t, v), errMsg);
    }

    /**
     * Assert is true
     */
    public static void isTrue(boolean result, String errMsg) {
        if (!result)
            throw new UnrecoverableMsgEmbeddedException(errMsg);
    }

    /**
     * Assert is false
     */
    public static void isFalse(boolean result, String errMsg) {
        if (result)
            throw new UnrecoverableMsgEmbeddedException(errMsg);
    }

    /**
     * Assert value in between start and end
     */
    public static void inBetween(int val, int start, int end, String errMsg) {
        isTrue(val >= start && val <= end, errMsg);
    }

    /**
     * Assert value in between start (inclusive) and end (inclusive)
     */
    public static void inBetween(long val, long start, long end, String errMsg) {
        isTrue(val >= start && val <= end, errMsg);
    }

    /**
     * Assert value not in between start and end
     */
    public static void notInBetween(long val, long start, long end, String errMsg) {
        isTrue(val < start || val > end, errMsg);
    }
}
