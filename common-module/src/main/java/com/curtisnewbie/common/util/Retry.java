package com.curtisnewbie.common.util;

import lombok.extern.slf4j.*;
import org.springframework.lang.*;
import org.springframework.util.*;

import java.util.function.*;

/**
 * Class that facilitates retry (on exception)
 *
 * @author yongj.zhuang
 */
@Slf4j
public final class Retry {

    private final Runnable r;
    private final long delayMilli;

    /**
     * Create Retry with no delay
     *
     * @param r operation
     */
    public Retry(Runnable r) {
        Assert.notNull(r, "Runnable == null");
        this.r = r;
        this.delayMilli = -1; // no delay
    }

    /**
     * Create Retry
     *
     * @param r          operation
     * @param delayMilli delay between each attempt in milliseconds, it should be fairly small, like some ~100ms delays
     */
    public Retry(Runnable r, long delayMilli) {
        Assert.notNull(r, "Runnable == null");
        this.r = r;
        this.delayMilli = delayMilli;
    }

    /**
     * Retry on exception
     *
     * @param onException consumer of <b>last</b> exception
     * @param retryTimes  retry times
     */
    public void retryOnException(Consumer<Exception> onException, int retryTimes) {
        int currRetry = 0;
        Exception prevException = null;

        do {
            try {
                r.run();
                return; // success, no exception is thrown at all
            } catch (Exception t) {
                prevException = t;
                log.debug("Retry exception: ", t);
            }

            // try to delay each retry
            if (currRetry + 1 < retryTimes && delayMilli > 0)
                ThreadUtils.sleep(delayMilli);

        } while (++currRetry < retryTimes);

        // exceeded maximum retry times, this is the last exception that we caught
        if (onException != null) {
            onException.accept(prevException);
        }
    }

    /**
     * Retry once on exception, and simply ignore the exception caught
     */
    public void retryOnceOnException() {
        retryOnException(null, 1);
    }

    /**
     * Retry once on exception, and use the supplied consumer to handle the exception caught
     */
    public void retryOnceOnException(@Nullable Consumer<Exception> onException) {
        retryOnException(onException, 1);
    }

    /**
     * Create a Retry builder for the given runnable
     */
    public static Builder forRunnable(Runnable r) {
        return new Builder(r);
    }

    /**
     * Builder for Retry
     * <p>
     * Must call {@link #start()} to actually start the operation
     * </p>
     */
    public static class Builder {
        private final Retry retry;
        private int retryTimes = 1;
        private Consumer<Exception> onException;

        private Builder(Runnable r) {
            this.retry = new Retry(r);
        }

        /**
         * Maximum retry times
         */
        public Builder retryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        /**
         * Only retry once
         */
        public Builder retryOnce() {
            return retryTimes(1);
        }

        /**
         * Consumer for exception
         */
        public Builder onException(Consumer<Exception> onException) {
            this.onException = onException;
            return this;
        }

        /**
         * Start the internal {@code Retry}
         */
        public void start() {
            retry.retryOnException(onException, retryTimes);
        }

    }
}
