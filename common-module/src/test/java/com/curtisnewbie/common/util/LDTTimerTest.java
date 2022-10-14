package com.curtisnewbie.common.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class LDTTimerTest {

    @Test
    public void should_time_op() {
        LDTTimer.timedAndLogged(() -> {
            Runner.tryRun(() -> Thread.sleep(TimeUnit.SECONDS.toMillis(1)));
        }, "sleepOp");
    }

}