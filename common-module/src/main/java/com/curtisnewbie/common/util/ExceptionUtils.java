package com.curtisnewbie.common.util;

import java.util.concurrent.*;

/**
 * Exception Utils
 *
 * @author yongj.zhuang
 */
public final class ExceptionUtils {

    private ExceptionUtils() {

    }

    /**
     * Wrap the callable, and throws IllegalStateException if necessary
     */
    public static <V> V throwIfError(Callable<V> callable) {
        try {
            return callable.call();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Wrap the Runnable, and throws IllegalStateException if necessary
     */
    public static void throwIfError(ThrowableRunnable r) {
        try {
            r.run();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Return IllegalStateException with a formatted message
     */
    public static IllegalStateException illegalState(Throwable t, String pattern, Object... args) {
        return new IllegalStateException(String.format(pattern, args), t);
    }

    /**
     * Return IllegalStateException with a formatted message
     */
    public static IllegalStateException illegalState(String pattern, Object... args) {
        return new IllegalStateException(String.format(pattern, args));
    }

    /**
     * Return IllegalArgumentException with a formatted message
     */
    public static IllegalArgumentException illegalArgument(String pattern, Object... args) {
        throw new IllegalArgumentException(String.format(pattern, args));
    }

    /**
     * Return IllegalArgumentException with a formatted message
     */
    public static IllegalArgumentException illegalArgument(Throwable t, String pattern, Object... args) {
        throw new IllegalArgumentException(String.format(pattern, args), t);
    }
}
