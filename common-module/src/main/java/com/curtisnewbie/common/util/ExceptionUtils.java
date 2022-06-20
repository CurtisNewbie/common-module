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
    public static <V> V throwIfError(Callable<V> callable) throws IllegalStateException {
        try {
            return callable.call();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Wrap the Runnable, and throws IllegalStateException if necessary
     */
    public static void throwIfError(ThrowableRunnable r) throws IllegalStateException {
        try {
            r.run();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
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
