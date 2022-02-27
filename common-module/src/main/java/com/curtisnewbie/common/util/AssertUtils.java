package com.curtisnewbie.common.util;


import com.curtisnewbie.common.exceptions.UnrecoverableException;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for assertion
 * <p>
 * For condition that does not match the rule, a {@link UnrecoverableException} is thrown
 * </p>
 * <p>
 * Almost the same as {@link ValidUtils}, except that the {@link RuntimeException} is used
 * </p>
 *
 * @author yongjie.zhuang
 */
public final class AssertUtils {

    private static final String DEFAULT_NON_NULL_OR_EMPTY_MSG = "Required parameters cannot be null or empty";
    private static final String DEFAULT_ILLEGAL_PARAMETERS_MSG = "Invalid parameters";

    private AssertUtils() {

    }

    /**
     * Assert array not null or empty
     */
    public static <T> void notEmpty(T[] c, ErrorType errorType) {
        nonNull(c, errorType);
        isFalse(c.length == 0, errorType);
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
        isTrue(StringUtils.hasText(text), errMsg);
    }

    /**
     * Assert has text
     */
    public static void hasText(String text, String errMsgPattern, Object... args) {
        isTrue(StringUtils.hasText(text), String.format(errMsgPattern, args));
    }

    /**
     * Assert has text
     */
    public static void hasText(String text, ErrorType errorType) {
        isTrue(StringUtils.hasText(text), errorType);
    }

    /**
     * Assert not null
     */
    public static <T> void notNull(T t) {
        nonNull(t);
    }

    /**
     * Assert not null
     */
    public static <T> void notNull(T t, String errMsg) {
        nonNull(t, errMsg);
    }

    /**
     * Assert not null
     */
    public static <T> void notNull(T t, ErrorType errorType) {
        nonNull(t, errorType);
    }

    /**
     * Assert not null
     */
    public static <T> void notNull(T t, String errMsgPattern, Object... args) {
        nonNull(t, errMsgPattern, args);
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
    public static <T> void nonNull(T t, String errMsgPattern, Object... args) {
        isFalse(t == null, String.format(errMsgPattern, args));
    }

    /**
     * Assert not null
     */
    public static <T> void nonNull(T t, String errMsg) {
        isFalse(t == null, errMsg);
    }

    /**
     * Assert not null
     */
    public static <T> void nonNull(T t, ErrorType errorType) {
        isFalse(t == null, errorType);
    }

    /**
     * Assert is null
     */
    public static <T> void isNull(T t) {
        isTrue(t == null, DEFAULT_ILLEGAL_PARAMETERS_MSG);
    }

    /**
     * Assert is null
     */
    public static <T> void isNull(T t, String errMsg) {
        isTrue(t == null, errMsg);
    }

    /**
     * Assert is null
     */
    public static <T> void isNull(T t, ErrorType errorType) {
        isTrue(t == null, errorType);
    }

    /**
     * Assert equals
     */
    public static <T> void equals(T t, T v, String errMsg) {
        isTrue(Objects.equals(t, v), errMsg);
    }

    /**
     * Assert equals
     */
    public static <T> void equals(T t, T v, ErrorType errorType) {
        isTrue(Objects.equals(t, v), errorType);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(short t, short v, ErrorType errorType) {
        isTrue(t == v, errorType);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(short t, short v, String errMsg) {
        isTrue(t == v, errMsg);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(short t, short v) {
        equals(t, v, DEFAULT_ILLEGAL_PARAMETERS_MSG);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(int t, int v, ErrorType errorType) {
        isTrue(t == v, errorType);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(int t, int v, String errMsg) {
        isTrue(t == v, errMsg);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(int t, int v) {
        equals(t, v, DEFAULT_ILLEGAL_PARAMETERS_MSG);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(long t, long v) {
        equals(t, v, DEFAULT_ILLEGAL_PARAMETERS_MSG);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(long t, long v, String errMsg) {
        isTrue(t == v, errMsg);
    }

    /**
     * Assert equals
     */
    public static <T, V> void equals(long t, long v, ErrorType errorType) {
        isTrue(t == v, errorType);
    }

    /**
     * Assert not equals
     */
    public static <T, V> void notEquals(T t, V v, String errMsg) {
        isFalse(Objects.equals(t, v), errMsg);
    }

    /**
     * Assert not equals
     */
    public static <T, V> void notEquals(T t, V v, ErrorType errorType) {
        isFalse(Objects.equals(t, v), errorType);
    }

    /**
     * Assert is true
     */
    public static void isTrue(boolean result, ErrorType errorType) {
        if (!result) throw new UnrecoverableException(errorType.getMsg(), errorType.getCode());
    }

    /**
     * Assert is true
     */
    public static void isTrue(boolean result, String errMsg) {
        if (!result) throw new UnrecoverableException(errMsg);
    }

    /**
     * Assert is true
     */
    public static void isTrue(boolean result, String errMsgPattern, Object... args) {
        isTrue(result, String.format(errMsgPattern, args));
    }

    /**
     * Assert is false
     */
    public static void isFalse(boolean result, String errMsg) {
        if (result) throw new UnrecoverableException(errMsg);
    }

    /**
     * Assert is false
     */
    public static void isFalse(boolean result, ErrorType errorType) {
        if (result) throw new UnrecoverableException(errorType.getMsg(), errorType.getCode());
    }

    /**
     * Assert is false
     */
    public static void isFalse(boolean result, String errMsgPattern, Object... args) {
        isFalse(result, String.format(errMsgPattern, args));
    }

    /**
     * Assert value in between start and end
     */
    public static void inBetween(int val, int start, int end, String errMsg) {
        isTrue(val >= start && val <= end, errMsg);
    }

    /**
     * Assert value in between start and end
     */
    public static void inBetween(int val, int start, int end, ErrorType errorType) {
        isTrue(val >= start && val <= end, errorType);
    }

    /**
     * Assert value in between start (inclusive) and end (inclusive)
     */
    public static void inBetween(long val, long start, long end, String errMsg) {
        isTrue(val >= start && val <= end, errMsg);
    }

    /**
     * Assert value in between start (inclusive) and end (inclusive)
     */
    public static void inBetween(long val, long start, long end, ErrorType errorType) {
        isTrue(val >= start && val <= end, errorType);
    }

    /**
     * Assert value not in between start and end
     */
    public static void notInBetween(long val, long start, long end, String errMsg) {
        isTrue(val < start || val > end, errMsg);
    }

    /**
     * Assert value not in between start and end
     */
    public static void notInBetween(long val, long start, long end, ErrorType errorType) {
        isTrue(val < start || val > end, errorType);
    }

    /**
     * Assert value not in between start and end
     */
    public static void notInBetween(int val, int start, int end, String errMsg) {
        isTrue(val < start || val > end, errMsg);
    }

    /**
     * Assert value not in between start and end
     */
    public static void notInBetween(int val, int start, int end, ErrorType errorType) {
        isTrue(val < start || val > end, errorType);
    }
}
