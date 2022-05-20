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
     * Run the Runnable safely
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
     * Call if condition returns true
     */
    public static void callIf(Callable callable, Supplier<Boolean> condition) throws Exception {
        Assert.notNull(condition, "condition == null");
        Assert.notNull(callable, "condition == null");

        Boolean cond = condition.get();
        if (cond != null && cond)
            callable.call();
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

}
