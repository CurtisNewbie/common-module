package com.curtisnewbie.common.util;

import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class for 'running' methods
 *
 * @author yongjie.zhuang
 */
public final class Runner {

    private Runner() {

    }

    /**
     * Run the Runnable safely, exception is handled by the consumer or simply muted
     */
    public static void runSafely(final ThrowableRunnable r, final Consumer<Exception> consumer) {
        if (r == null)
            return;

        try {
            r.run();
        } catch (Exception e) {
            if (consumer != null)
                consumer.accept(e);
        }
    }

    /**
     * Run the Runnable safely, the exception is muted
     */
    public static void runSafely(final ThrowableRunnable r) {
        runSafely(r, null);
    }

    /**
     * Run if condition returns true, null is considered as false
     */
    public static void callIf(ThrowableRunnable runnable, Supplier<Boolean> condition) throws Exception {
        Assert.notNull(condition, "condition == null");
        Assert.notNull(runnable, "runnable == null");

        Boolean cond = condition.get();
        if (cond != null && cond)
            runnable.run();
    }

    /**
     * Run if condition returns true
     */
    public static void runIf(Runnable runnable, Supplier<Boolean> condition) {
        Assert.notNull(condition, "condition == null");
        Assert.notNull(runnable, "runnable == null");

        Boolean cond = condition.get();
        if (cond != null && cond)
            runnable.run();
    }

    /**
     * Try call the Callable, and return the result
     * <p>
     * When exception is thrown, this method will wrap the caught exception with an IllegalStateException, and rethrow it
     * <p>
     * If the Callable itself is null, null is returned
     */
    public static <T> T tryCall(Callable<T> c) {
        if (c == null) return null;
        try {
            return c.call();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Run the runnable
     * <p>
     * When exception is thrown, this method will wrap the caught exception with an IllegalStateException, and rethrow it
     */
    public static void tryRun(ThrowableRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
