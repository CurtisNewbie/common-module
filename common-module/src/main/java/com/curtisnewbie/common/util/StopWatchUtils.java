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

    public static void logStopwatch(Runnable r, String op) {
        StopWatch sw = new StopWatch();
        sw.start();
        r.run();
        sw.stop();
        log.info("Operation '{}' took: {}ms", op, sw.getTotalTimeMillis());
    }
}
