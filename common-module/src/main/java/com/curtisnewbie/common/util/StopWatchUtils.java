package com.curtisnewbie.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * @author yongj.zhuang
 */
@Slf4j
public final class StopWatchUtils {

    private StopWatchUtils() {
    }

    /**
     * Use a StopWatch to check the time used for the Runnable
     *
     * @return milliseconds use
     */
    public static long stopwatch(Runnable r) {
        StopWatch sw = new StopWatch();
        sw.start();
        r.run();
        sw.stop();
        return sw.getTotalTimeMillis();
    }

    /**
     * Log the time used for the Runnable
     */
    public static void logStopwatch(Runnable r, String opName) {
        log.info("Operation '{}' took: {}ms", opName, stopwatch(r));
    }
}
