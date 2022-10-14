package com.curtisnewbie.common.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.*;

import java.time.*;
import java.util.function.Consumer;

/**
 * Timer based on {@link LocalDateTime}
 * <p>
 * It's <b>not</b> thread-Safe
 * </p>
 *
 * @author yongj.zhuang
 */
@Slf4j
public class LDTTimer {

    private LocalDateTime startTime = null;
    private LocalDateTime endTime = null;

    /** Get a new timer and start it */
    public static LDTTimer startTimer() {
        return new LDTTimer().start();
    }

    /** start the timer, repeatable action, only the first time calling this method takes effect */
    public LDTTimer start() {
        if (startTime != null) return this;
        startTime = LocalDateTime.now();
        return this;
    }

    /** stop the timer, repeatable action, only the first time calling this method takes effect */
    public LDTTimer stop() {
        if (endTime != null) return this;
        endTime = LocalDateTime.now();
        return this;
    }

    /** Get duration */
    public Duration duration() {
        Assert.notNull(startTime, "Timer hasn't started yet");
        Assert.notNull(endTime, "Timer hasn't stopped yet");
        return Duration.between(startTime, endTime);
    }

    /** Print Duration */
    public String printDuration() {
        final Duration duration = duration();
        final String s = duration.toString().substring(2);
        return s.replace("H", " Hours ").replace("M", " Minutes ").replace("S", " Seconds ");
    }

    /**
     * Time operation and call onFinished callback in which the timer is stopped
     */
    public static void timed(Runnable r, Consumer<LDTTimer> onFinished) {
        LDTTimer timer = LDTTimer.startTimer();
        r.run();
        timer.stop();
        onFinished.accept(timer);
    }

    /**
     * Time and log the operation
     */
    public static void timedAndLogged(Runnable r, String opName) {
        timed(r, (timer) -> log.info("Operation '{}' took: {}", opName, timer.printDuration()));
    }

}
