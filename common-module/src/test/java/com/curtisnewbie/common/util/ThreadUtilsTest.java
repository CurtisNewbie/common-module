package com.curtisnewbie.common.util;

import org.junit.jupiter.api.Test;

public class ThreadUtilsTest {

    @Test
    public void should_loop_for_some_time() {
        StopWatchUtils.logStopwatch(() -> {
            ThreadUtils.loop(1000);
        }, "loop");
    }

}