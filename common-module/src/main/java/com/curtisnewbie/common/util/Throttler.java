package com.curtisnewbie.common.util;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.util.*;

import java.util.concurrent.*;

/**
 * <p>
 * Throttler
 * </p>
 * <p>
 * Usage:
 * </p>
 *
 * <pre>
 * {@code
 *        final Throttler throttler = Throttler.builder()
 *                 .timeUnit(TimeUnit.SECONDS)
 *                 .timePerAttempt(12)  // 12 seconds for each attempt
 *                 .threshold(50) // only throttles when list size is greater than 50
 *                 .name("myThrottler") // name of the throttler
 *                 .build();
 *
 *        // throttler try to determine whether it should throttle based on the size of the list
 *        throttler.throttleIfNecessary(listSize);
 * }
 * </pre>
 *
 * @author yongj.zhuang
 */
@Slf4j
public final class Throttler {

    /** throttle time for each attempt */
    private final long timePerAttempt;

    /** throttle time unit */
    private final TimeUnit timeUnit;

    /** min threshold that triggers the throttling */
    private final long threshold;

    /** name of the throttler */
    private final String name;

    /**
     * @param timeUnit       time unit
     * @param timePerAttempt throttle time for each attempt, must be greater than or equal to 0, e.g., {@code Thread.sleep(timeUnit.toMillis(timePerAttempt) * amount); }
     * @param threshold      min threshold that triggers the throttling, must be greater than or equal to 0
     */
    @Builder
    public Throttler(TimeUnit timeUnit, long timePerAttempt, long threshold, String name) {
        Assert.notNull(timeUnit, "timeUnit == null");
        Assert.isTrue(timePerAttempt > 0, "timePerAttempt <= 0");
        Assert.isTrue(threshold >= 0, "imePerAttempt < 0");

        this.timeUnit = timeUnit;
        this.timePerAttempt = timePerAttempt;
        this.threshold = threshold;
        this.name = name != null ? name : "anonymous";

        log.info("Created Throttler '{}', time_unit: {}, time_per_attempt: {}, threshold: {}", name, timeUnit, timePerAttempt, threshold);
    }

    /**
     * Throttle if necessary
     *
     * @return is_interrupted whether the thread was interrupted
     */
    public boolean throttleIfNecessary(final long amount) {
        final long throttleInMilli = calculateThrottleTime(amount);

        if (throttleInMilli <= 0L) return false;

        try {
            Thread.sleep(throttleInMilli);

            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        }
    }

    /**
     * Calculate throttle time in milliseconds
     */
    public long calculateThrottleTime(final long amount) {
        if (amount < threshold || timePerAttempt <= 0 || amount <= 0) {
            return 0L;
        }

        return timeUnit.toMillis(timePerAttempt) * amount;
    }
}
