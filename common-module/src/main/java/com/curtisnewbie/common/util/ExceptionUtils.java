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
    public static IllegalStateException throwIllegalState(String pattern, Object... args) throws IllegalStateException {
        throw new IllegalStateException(String.format(pattern, args));
    }

    /**
     * Throw IllegalArgumentException with a formatted message
     */
    public static void throwIllegalArgument(String pattern, Object... args) throws IllegalArgumentException {
        throw new IllegalArgumentException(String.format(pattern, args));
    }
}
