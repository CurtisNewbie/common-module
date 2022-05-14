package com.curtisnewbie.common.util;

/**
 * Exception Utils
 *
 * @author yongj.zhuang
 */
public final class ExceptionUtils {

    private ExceptionUtils() {

    }

    /**
     * Throw IllegalStateException with a formatted message
     */
    public static void illegalStateException(String pattern, Object... args) {
        throw new IllegalStateException(String.format(pattern, args));
    }

    /**
     * Throw IllegalArgumentException with a formatted message
     */
    public static void illegalArgumentException(String pattern, Object... args) {
        throw new IllegalArgumentException(String.format(pattern, args));
    }
}
