package com.curtisnewbie.common.util;

import java.util.function.Consumer;

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

}
