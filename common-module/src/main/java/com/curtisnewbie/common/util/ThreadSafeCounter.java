package com.curtisnewbie.common.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * Thread-Safe Counter
 *
 * @author yongj.zhuang
 */
public final class ThreadSafeCounter {

    /** counter */
    private final AtomicLong counter = new AtomicLong(0);

    /** +1 */
    public long incr() {
        return counter.incrementAndGet();
    }

    /** -1 */
    public long decr() {
        return counter.decrementAndGet();
    }

    /** Get count */
    public long get() {
        return counter.get();
    }

    /** Wait until count <= 0 */
    public static void waitTilFinished(final ThreadSafeCounter counter) {
        while (counter.get() > 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Wait until count <= 0, when count <= 0, it will wait extra duration */
    public static void waitTilFinished(final ThreadSafeCounter counter, final TimeUnit timeUnit, long extraWait) {
        waitTilFinished(counter);

        try {
            Thread.sleep(timeUnit.toMillis(extraWait));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
